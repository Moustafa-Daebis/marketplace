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
import { useNavigate, useParams } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

function formatPrice(price) {
  const amount = Number(price);
  return Number.isFinite(amount) ? `$${amount.toFixed(2)}` : price;
}

export function ItemPage() {
  const { itemId } = useParams();
  const { token } = useAuth();
  const navigate = useNavigate();
  const [item, setItem] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchItem() {
      setLoading(true);
      setError("");

      try {
        const response = await axios.get(
          `http://localhost:8080/api/items/${itemId}`,
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

  function handleAddToCart() {
    console.log("Added to cart:", item.name);
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
                w={{ base: "100%", sm: "fit-content" }}
                mt="sm"
              >
                Add to cart
              </Button>
            </Stack>
          </Group>
        </Stack>
      </Paper>
    </Stack>
  );
}
