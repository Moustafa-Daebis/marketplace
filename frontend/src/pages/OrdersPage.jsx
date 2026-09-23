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
  Select,
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
import { useNavigate } from "react-router-dom";
const api = axios.create({
  baseURL: `${import.meta.env.VITE_API_URL}/api/orderitems`,
});

async function fetchOrderItems(endpoint, token) {
  const { data } = await api.get(`/${endpoint}`, {
    headers: token ? { Authorization: `Bearer ${token}` } : {},
  });
  return data;
}

async function updateOrderItemStatus({ orderItemId, status }, token) {
  const { data } = await api.patch(
    "",
    { orderItemId, status },
    { headers: token ? { Authorization: `Bearer ${token}` } : {} },
  );
  return data;
}

const statusColors = {
  PENDING: "yellow",
  PROCESSING: "blue",
  DELIVERED: "teal",
  COMPLETED: "teal",
  CANCELLED: "red",
  AWAITING_PICKUP: "orange",
  CONFIRMED: "blue",
  PREPARING: "orange",
  DELIVERING: "grape",
};

// Dropdown options for sales. `value` is what's sent in the PATCH body,
// matching the casing shown in the sample request ("Confirmed", not "CONFIRMED").
const SALE_STATUS_OPTIONS = [
  { value: "Confirmed", label: "Confirmed" },
  { value: "Preparing", label: "Preparing" },
  { value: "Delivering", label: "Delivering" },
  { value: "Delivered", label: "Delivered" },
];

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

// Maps whatever casing/format the backend returns (e.g. "DELIVERING" or
// "Delivering") to one of our option values, so the Select shows the
// right current selection.
function normalizeStatusToOption(status) {
  if (!status) return null;
  const match = SALE_STATUS_OPTIONS.find(
    (opt) => opt.value.toUpperCase() === status.toUpperCase(),
  );
  return match ? match.value : null;
}

function SaleStatusSelect({ item, token, onStatusUpdated }) {
  const [value, setValue] = useState(normalizeStatusToOption(item.status));
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setValue(normalizeStatusToOption(item.status));
  }, [item.status]);

  const handleChange = async (newStatus) => {
    if (!newStatus || newStatus === value) return;

    const previousValue = value;
    setValue(newStatus); // optimistic update
    setLoading(true);
    setError(null);

    try {
      await updateOrderItemStatus(
        { orderItemId: item.id, status: newStatus },
        token,
      );
      onStatusUpdated(item.id, newStatus);
    } catch (err) {
      setValue(previousValue); // revert on failure
      setError(err.response?.data?.message ?? err.message ?? "Update failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Stack gap={4} align="flex-end">
      <Select
        size="xs"
        w={160}
        data={SALE_STATUS_OPTIONS}
        value={value}
        onChange={handleChange}
        disabled={loading}
        rightSection={loading ? <Loader size={12} /> : undefined}
        placeholder="Set status"
      />
      {error && (
        <Text size="xs" c="red">
          {error}
        </Text>
      )}
    </Stack>
  );
}

function OrderCard({ item, type, token, onStatusUpdated }) {
  const navigate = useNavigate();
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
                color={statusColors[item.status?.toUpperCase()] ?? "gray"}
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

          {type === "sale" ? (
            <SaleStatusSelect
              item={item}
              token={token}
              onStatusUpdated={onStatusUpdated}
            />
          ) : (
            <Button
              variant="subtle"
              size="xs"
              rightSection={<IconArrowRight size={14} />}
              onClick={() => navigate(`/items/${item.itemId}`)}
            >
              Details
            </Button>
          )}
        </Stack>
      </Group>
    </Paper>
  );
}

function OrderList({
  items,
  loading,
  error,
  onRetry,
  emptyLabel,
  type,
  token,
  onStatusUpdated,
}) {
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
        <OrderCard
          key={item.id}
          item={item}
          type={type}
          token={token}
          onStatusUpdated={onStatusUpdated}
        />
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

  const handleSaleStatusUpdated = useCallback((itemId, newStatus) => {
    setSales((prev) =>
      prev.map((item) =>
        item.id === itemId ? { ...item, status: newStatus } : item,
      ),
    );
  }, []);

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
            type="purchase"
            token={token}
          />
        </Tabs.Panel>

        <Tabs.Panel value="sales" pt="md">
          <OrderList
            items={sales}
            loading={salesLoading}
            error={salesError}
            onRetry={loadSales}
            emptyLabel="No sales yet."
            type="sale"
            token={token}
            onStatusUpdated={handleSaleStatusUpdated}
          />
        </Tabs.Panel>
      </Tabs>
    </Stack>
  );
}
