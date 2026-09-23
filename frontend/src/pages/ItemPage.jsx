import { useEffect, useState } from "react";
import axios from "axios";
import {
  Alert,
  Badge,
  Button,
  Card,
  Group,
  Image,
  Paper,
  Skeleton,
  Stack,
  Text,
  Title,
} from "@mantine/core";
import { IconArrowLeft, IconShoppingCartPlus } from "@tabler/icons-react";
import { useNavigate, useOutletContext, useParams } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

function formatPrice(price) {
  const amount = Number(price);
  return Number.isFinite(amount) ? `$${amount.toFixed(2)}` : price;
}

export function ItemPage() {
  const { itemId } = useParams();
  const { openCart } = useOutletContext();
  const { token } = useAuth();
  const navigate = useNavigate();
  const [item, setItem] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [cartStatus, setCartStatus] = useState({ type: "", message: "" });
  const [addingToCart, setAddingToCart] = useState(false);

  useEffect(() => {
    async function fetchItem() {
      setLoading(true);
      setError("");

      try {
        const response = await axios.get(
          `${import.meta.env.VITE_API_URL}/api/items/${itemId}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          },
        );

        setItem(response.data?.data ?? response.data);
      } catch {
        setError("Unable to load this item. Please try again later.");
      } finally {
        setLoading(false);
      }
    }

    fetchItem();
  }, [itemId, token]);

  if (loading) {
    return (
      <Stack gap="lg">
        <Skeleton height={36} width={130} radius="sm" />

        <Paper withBorder radius="md" p={{ base: "md", sm: "xl" }}>
          <Group align="flex-start" gap="xl" wrap="wrap">
            <Skeleton height={320} maw={460} radius="md" flex="1 1 320px" />

            <Stack gap="md" flex="2 1 360px">
              <Group justify="space-between" align="flex-start" gap="sm">
                <Stack gap="xs" flex="1">
                  <Skeleton height={34} width="70%" />
                  <Skeleton height={34} width="45%" />
                </Stack>
                <Skeleton height={28} width={96} radius="xl" />
              </Group>

              <Skeleton height={28} width={110} />
              <Stack gap="xs">
                <Skeleton height={16} width="95%" />
                <Skeleton height={16} width="88%" />
                <Skeleton height={16} width="72%" />
              </Stack>
            </Stack>
          </Group>
        </Paper>
      </Stack>
    );
  }

  if (error) {
    return <Alert color="red">{error}</Alert>;
  }

  if (!item) {
    return <Text c="dimmed">This item could not be found.</Text>;
  }

  async function handleAddToCart() {
    setAddingToCart(true);
    setCartStatus({ type: "", message: "" });

    try {
      await axios.post(
        `${import.meta.env.VITE_API_URL}/api/cart-items`,
        {
          itemId: itemId,
          quantity: 1,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );

      setCartStatus({
        type: "success",
        message: "Item added to your cart.",
      });
      openCart();
    } catch {
      setCartStatus({
        type: "error",
        message: "Unable to add this item to your cart. Please try again.",
      });
    } finally {
      setAddingToCart(false);
    }
  }

  return (
    <Stack gap="lg">
      <Button
        variant="subtle"
        leftSection={<IconArrowLeft size={18} />}
        onClick={() => navigate("/items")}
        w="fit-content"
      >
        Back to items
      </Button>

      <Paper withBorder radius="md" p={{ base: "md", sm: "xl" }}>
        <Stack gap="xl">
          <Group align="flex-start" gap="xl" wrap="wrap">
            <Card withBorder radius="md" padding={0} maw={460} flex="1 1 320px">
              <Image
                src={item.image}
                alt={item.name}
                height={320}
                fallbackSrc="https://placehold.co/600x400/e9ecef/495057?text=Marketplace+item"
              />
            </Card>

            <Stack gap="md" flex="2 1 360px">
              <Group justify="space-between" align="flex-start" gap="sm">
                <Title order={1}>{item.name}</Title>
                <Badge color="teal" variant="light" size="lg">
                  Available
                </Badge>
              </Group>

              <Text fw={700} size="xl">
                {formatPrice(item.price)}
              </Text>

              <Text c="dimmed" style={{ whiteSpace: "pre-wrap" }}>
                {item.description || "No description provided."}
              </Text>

              <Button
                leftSection={<IconShoppingCartPlus size={18} />}
                onClick={handleAddToCart}
                loading={addingToCart}
                w={{ base: "100%", sm: "fit-content" }}
                mt="sm"
              >
                Add to cart
              </Button>

              {cartStatus.message && (
                <Alert color={cartStatus.type === "success" ? "teal" : "red"}>
                  {cartStatus.message}
                </Alert>
              )}
            </Stack>
          </Group>
        </Stack>
      </Paper>
    </Stack>
  );
}
