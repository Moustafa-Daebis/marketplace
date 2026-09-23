import { useEffect, useMemo, useState } from "react";
import axios from "axios";
import {
  Alert,
  Button,
  Divider,
  Group,
  Paper,
  Skeleton,
  Stack,
  Text,
  Title,
} from "@mantine/core";
import { IconArrowLeft, IconCreditCard } from "@tabler/icons-react";
import { useNavigate } from "react-router-dom";
import { CartItem } from "../components/CartItem";
import { useAuth } from "../hooks/useAuth";

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

export function CheckoutPage() {
  const { token } = useAuth();
  const navigate = useNavigate();
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [placingOrder, setPlacingOrder] = useState(false);
  const [orderStatus, setOrderStatus] = useState({ type: "", message: "" });
  const subtotal = useMemo(
    () =>
      cartItems.reduce(
        (total, cartItem) => total + getCartItemTotal(cartItem),
        0,
      ),
    [cartItems],
  );

  useEffect(() => {
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
        setError("Unable to load checkout. Please try again later.");
      } finally {
        if (!controller.signal.aborted) setLoading(false);
      }
    }

    fetchCartItems();

    return () => controller.abort();
  }, [token]);

  async function handlePlaceOrder() {
    setPlacingOrder(true);
    setOrderStatus({ type: "", message: "" });

    try {
      await axios.post(
        "http://localhost:8080/api/orders",
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );

      setOrderStatus({
        type: "success",
        message: "Your order has been placed.",
      });
      setCartItems([]);
    } catch {
      setOrderStatus({
        type: "error",
        message: "Unable to place your order. Please try again.",
      });
    } finally {
      setPlacingOrder(false);
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

      <Group justify="space-between" align="flex-start" gap="md">
        <div>
          <Title order={1}>Checkout</Title>
          <Text c="dimmed" mt={6}>
            Review your cart before placing your order.
          </Text>
        </div>
      </Group>

      {error && <Alert color="red">{error}</Alert>}

      <Group align="flex-start" gap="lg">
        <Stack gap="sm" flex="2 1 420px">
          <Title order={3}>Order items</Title>

          {loading &&
            Array.from({ length: 3 }, (_, index) => (
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

          {!loading &&
            !error &&
            cartItems.map((cartItem, index) => (
              <CartItem
                key={
                  cartItem.id ?? cartItem.item?.id ?? cartItem.itemId ?? index
                }
                cartItem={cartItem}
              />
            ))}

          {!loading && !error && cartItems.length === 0 && (
            <Paper withBorder radius="md" p="xl">
              <Text c="dimmed">Your cart is empty.</Text>
            </Paper>
          )}
        </Stack>

        <Paper withBorder radius="md" p="lg" flex="1 1 280px">
          <Stack gap="md">
            <Title order={3}>Summary</Title>
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
            <Divider />
            <Group justify="space-between">
              <Text fw={700}>Total</Text>
              <Text fw={800} size="xl">
                {formatPrice(subtotal)}
              </Text>
            </Group>
            <Button
              leftSection={<IconCreditCard size={18} />}
              disabled={loading || cartItems.length === 0}
              loading={placingOrder}
              onClick={handlePlaceOrder}
            >
              Place order
            </Button>
            {orderStatus.message && (
              <Alert color={orderStatus.type === "success" ? "teal" : "red"}>
                {orderStatus.message}
              </Alert>
            )}
          </Stack>
        </Paper>
      </Group>
    </Stack>
  );
}
