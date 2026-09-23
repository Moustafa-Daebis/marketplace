import { useEffect, useState, useCallback } from "react";
import axios from "axios";
import {
  Alert,
  Badge,
  Button,
  Center,
  Group,
  Loader,
  Paper,
  Stack,
  Tabs,
  Text,
  Title,
} from "@mantine/core";
import {
  IconAlertCircle,
  IconArrowRight,
  IconPackage,
  IconReceipt,
  IconShoppingBag,
} from "@tabler/icons-react";
import { useAuth } from "../hooks/useAuth";

const api = axios.create({
  baseURL: "http://localhost:8080/api/orderitems",
});

async function fetchOrderItems(endpoint, token) {
  const { data } = await api.get(`/${endpoint}`, {
    headers: token ? { Authorization: `Bearer ${token}` } : {},
  });
  return data;
}

const statusColors = {
  PENDING: "yellow",
  PROCESSING: "blue",
  DELIVERED: "teal",
  COMPLETED: "teal",
  CANCELLED: "red",
  AWAITING_PICKUP: "orange",
};

function formatStatus(status) {
  if (!status) return "";
  return status
    .toLowerCase()
    .split("_")
    .map((w) => w[0].toUpperCase() + w.slice(1))
    .join(" ");
}

function formatCurrency(value) {
  const num = Number(value ?? 0);
  return `$${num.toFixed(2)}`;
}

function OrderCard({ item }) {
  const total = item.price * item.quantity;

  return (
    <Paper withBorder radius="md" p="md">
      <Group justify="space-between" align="flex-start" gap="md">
        <Group align="flex-start" gap="md">
          <IconPackage size={24} stroke={1.8} />
          <Stack gap={4}>
            <Group gap="xs">
              <Text fw={700}>{item.itemName}</Text>
              <Badge
                variant="light"
                color={statusColors[item.status] ?? "gray"}
              >
                {formatStatus(item.status)}
              </Badge>
            </Group>
            <Text size="sm" c="dimmed">
              Order {item.orderId} · Qty {item.quantity}
            </Text>
            {item.itemDescription && (
              <Text size="sm" c="dimmed">
                {item.itemDescription}
              </Text>
            )}
          </Stack>
        </Group>

        <Stack align="flex-end" gap="xs">
          <Text fw={800}>{formatCurrency(total)}</Text>
          <Button
            variant="subtle"
            size="xs"
            rightSection={<IconArrowRight size={14} />}
          >
            Details
          </Button>
        </Stack>
      </Group>
    </Paper>
  );
}

function OrderList({ items, loading, error, onRetry, emptyLabel }) {
  if (loading) {
    return (
      <Center py="xl">
        <Loader size="sm" />
      </Center>
    );
  }

  if (error) {
    return (
      <Alert
        color="red"
        icon={<IconAlertCircle size={16} />}
        title="Couldn't load orders"
      >
        <Stack gap="sm">
          <Text size="sm">{error}</Text>
          <Button size="xs" variant="light" onClick={onRetry} w="fit-content">
            Retry
          </Button>
        </Stack>
      </Alert>
    );
  }

  if (!items.length) {
    return (
      <Center py="xl">
        <Text c="dimmed" size="sm">
          {emptyLabel}
        </Text>
      </Center>
    );
  }

  return (
    <Stack gap="sm">
      {items.map((item) => (
        <OrderCard key={item.id} item={item} />
      ))}
    </Stack>
  );
}

export function OrdersPage() {
  const { token } = useAuth();

  const [purchases, setPurchases] = useState([]);
  const [purchasesLoading, setPurchasesLoading] = useState(true);
  const [purchasesError, setPurchasesError] = useState(null);

  const [sales, setSales] = useState([]);
  const [salesLoading, setSalesLoading] = useState(true);
  const [salesError, setSalesError] = useState(null);

  const loadPurchases = useCallback(() => {
    setPurchasesLoading(true);
    setPurchasesError(null);
    fetchOrderItems("purchases", token)
      .then(setPurchases)
      .catch((err) =>
        setPurchasesError(
          err.response?.data?.message ?? err.message ?? "Something went wrong",
        ),
      )
      .finally(() => setPurchasesLoading(false));
  }, [token]);

  const loadSales = useCallback(() => {
    setSalesLoading(true);
    setSalesError(null);
    fetchOrderItems("sales", token)
      .then(setSales)
      .catch((err) =>
        setSalesError(
          err.response?.data?.message ?? err.message ?? "Something went wrong",
        ),
      )
      .finally(() => setSalesLoading(false));
  }, [token]);

  useEffect(() => {
    if (!token) return;
    loadPurchases();
    loadSales();
  }, [token, loadPurchases, loadSales]);

  return (
    <Stack gap="lg">
      <div>
        <Title order={1}>Orders</Title>
        <Text c="dimmed" mt={6}>
          Track purchases and sales from your marketplace activity.
        </Text>
      </div>

      <Tabs defaultValue="purchases">
        <Tabs.List>
          <Tabs.Tab value="purchases" leftSection={<IconReceipt size={16} />}>
            Purchases
          </Tabs.Tab>
          <Tabs.Tab value="sales" leftSection={<IconShoppingBag size={16} />}>
            Sales
          </Tabs.Tab>
        </Tabs.List>

        <Tabs.Panel value="purchases" pt="md">
          <OrderList
            items={purchases}
            loading={purchasesLoading}
            error={purchasesError}
            onRetry={loadPurchases}
            emptyLabel="No purchases yet."
          />
        </Tabs.Panel>

        <Tabs.Panel value="sales" pt="md">
          <OrderList
            items={sales}
            loading={salesLoading}
            error={salesError}
            onRetry={loadSales}
            emptyLabel="No sales yet."
          />
        </Tabs.Panel>
      </Tabs>
    </Stack>
  );
}
