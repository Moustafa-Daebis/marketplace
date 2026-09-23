import { useEffect, useMemo, useState } from "react";
import axios from "axios";
import {
  Alert,
  Badge,
  Button,
  Divider,
  Drawer,
  Group,
  Paper,
  Skeleton,
  Stack,
  Text,
  ThemeIcon,
  Title,
} from "@mantine/core";
import {
  IconArrowRight,
  IconShoppingBag,
  IconShoppingCart,
  IconTrash,
} from "@tabler/icons-react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";
import { CartItem } from "./CartItem";

function getCartItems(payload) {
  if (Array.isArray(payload)) return payload;
  if (Array.isArray(payload?.data)) return payload.data;
  if (Array.isArray(payload?.items)) return payload.items;
  if (Array.isArray(payload?.data?.items)) return payload.data.items;
  return [];
}

function getCartItemTotal(cartItem) {
  const item = cartItem.item ?? cartItem;
  const price = Number(item.price ?? cartItem.price ?? 0);
  const quantity = Number(cartItem.quantity ?? 1);

  return Number.isFinite(price * quantity) ? price * quantity : 0;
}

function formatPrice(price) {
  return `$${price.toFixed(2)}`;
}

export function CartDrawer({ opened, onClose }) {
  const { token } = useAuth();
  const navigate = useNavigate();
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const subtotal = useMemo(
    () =>
      cartItems.reduce(
        (total, cartItem) => total + getCartItemTotal(cartItem),
        0,
      ),
    [cartItems],
  );
  const hasItems = cartItems.length > 0;

  function handleCheckout() {
    onClose();
    navigate("/checkout");
  }

  useEffect(() => {
    if (!opened || !token) return undefined;

    const controller = new AbortController();

    async function fetchCartItems() {
      setLoading(true);
      setError("");

      try {
        const response = await axios.get(
          "http://localhost:8080/api/carts/items",
          {
            headers: { Authorization: `Bearer ${token}` },
            signal: controller.signal,
          },
        );

        setCartItems(getCartItems(response.data));
      } catch (fetchError) {
        if (axios.isCancel(fetchError)) return;
        setError("Unable to load your cart. Please try again later.");
      } finally {
        if (!controller.signal.aborted) setLoading(false);
      }
    }

    fetchCartItems();

    return () => controller.abort();
  }, [opened, token]);

  return (
    <Drawer
      opened={opened}
      onClose={onClose}
      position="right"
      size="md"
      padding="lg"
      title={
        <Group gap="sm">
          <ThemeIcon radius="md" size={36} variant="light" color="teal">
            <IconShoppingCart size={20} />
          </ThemeIcon>
          <div>
            <Title order={3}>Cart</Title>
            <Text size="sm" c="dimmed">
              Review your selected items
            </Text>
          </div>
        </Group>
      }
      styles={{
        header: {
          borderBottom: "1px solid var(--mantine-color-gray-2)",
          paddingBottom: "var(--mantine-spacing-md)",
        },
        body: {
          height: "calc(100% - 76px)",
        },
      }}
    >
      <Stack justify="space-between" h="100%" gap="xl">
        <Stack gap="md">
          <Group justify="space-between" align="center">
            <Text fw={700}>Items</Text>
            <Badge variant="light" color="gray">
              {cartItems.length} {cartItems.length === 1 ? "item" : "items"}
            </Badge>
          </Group>

          {loading && (
            <Stack gap="sm">
              {Array.from({ length: 3 }, (_, index) => (
                <Paper key={index} withBorder radius="md" p="sm">
                  <Group gap="md" wrap="nowrap">
                    <Skeleton height={76} width={76} radius="sm" />
                    <Stack gap="xs" flex={1}>
                      <Skeleton height={18} width="70%" />
                      <Skeleton height={14} width="28%" />
                      <Skeleton height={18} width="100%" />
                    </Stack>
                  </Group>
                </Paper>
              ))}
            </Stack>
          )}

          {error && <Alert color="red">{error}</Alert>}

          {!loading && !error && hasItems && (
            <Stack gap="sm">
              {cartItems.map((cartItem, index) => (
                <CartItem
                  key={
                    cartItem.id ?? cartItem.item?.id ?? cartItem.itemId ?? index
                  }
                  cartItem={cartItem}
                />
              ))}
            </Stack>
          )}

          {!loading && !error && !hasItems && (
            <Paper withBorder radius="md" p="xl">
              <Stack align="center" gap="sm" ta="center">
                <ThemeIcon size={64} radius="xl" variant="light" color="gray">
                  <IconShoppingBag size={34} stroke={1.6} />
                </ThemeIcon>
                <Title order={4}>Your cart is empty</Title>
                <Text size="sm" c="dimmed" maw={260}>
                  Items you add from the marketplace will appear here before
                  checkout.
                </Text>
                <Button variant="light" mt="xs" onClick={onClose}>
                  Continue shopping
                </Button>
              </Stack>
            </Paper>
          )}
        </Stack>

        <Stack gap="md">
          <Divider />

          <Stack gap="xs">
            <Group justify="space-between">
              <Text c="dimmed">Subtotal</Text>
              <Text fw={700}>{formatPrice(subtotal)}</Text>
            </Group>
            <Group justify="space-between">
              <Text c="dimmed">Estimated fees</Text>
              <Text fw={700}>$0.00</Text>
            </Group>
          </Stack>

          <Paper withBorder radius="md" p="md" bg="gray.0">
            <Group justify="space-between">
              <div>
                <Text fw={700}>Total</Text>
                <Text size="sm" c="dimmed">
                  Calculated at checkout
                </Text>
              </div>
              <Text fw={800} size="xl">
                {formatPrice(subtotal)}
              </Text>
            </Group>
          </Paper>

          <Group grow>
            <Button
              variant="light"
              color="red"
              disabled={!hasItems}
              leftSection={<IconTrash size={18} />}
            >
              Clear
            </Button>
            <Button
              disabled={!hasItems}
              rightSection={<IconArrowRight size={18} />}
              onClick={handleCheckout}
            >
              Checkout
            </Button>
          </Group>
        </Stack>
      </Stack>
    </Drawer>
  );
}
