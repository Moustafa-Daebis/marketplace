import {
  Badge,
  Button,
  Group,
  Paper,
  Stack,
  Tabs,
  Text,
  Title,
} from "@mantine/core";
import {
  IconArrowRight,
  IconPackage,
  IconReceipt,
  IconShoppingBag,
} from "@tabler/icons-react";

const purchaseOrders = [
  {
    id: "ORD-1042",
    item: "Wireless keyboard",
    seller: "Mona Ahmed",
    status: "Processing",
    total: "$42.00",
    date: "Sep 22, 2026",
  },
  {
    id: "ORD-1038",
    item: "Desk lamp",
    seller: "Youssef Ali",
    status: "Delivered",
    total: "$18.50",
    date: "Sep 18, 2026",
  },
];

const salesOrders = [
  {
    id: "SALE-218",
    item: "Vintage headphones",
    buyer: "Laila Hassan",
    status: "Awaiting pickup",
    total: "$65.00",
    date: "Sep 21, 2026",
  },
  {
    id: "SALE-204",
    item: "Mechanical mouse",
    buyer: "Omar Saleh",
    status: "Completed",
    total: "$29.00",
    date: "Sep 15, 2026",
  },
];

function OrderCard({ order, type }) {
  const personLabel = type === "purchase" ? "Seller" : "Buyer";
  const personName = type === "purchase" ? order.seller : order.buyer;

  return (
    <Paper withBorder radius="md" p="md">
      <Group justify="space-between" align="flex-start" gap="md">
        <Group align="flex-start" gap="md">
          <IconPackage size={24} stroke={1.8} />
          <Stack gap={4}>
            <Group gap="xs">
              <Text fw={700}>{order.item}</Text>
              <Badge variant="light" color="teal">
                {order.status}
              </Badge>
            </Group>
            <Text size="sm" c="dimmed">
              {order.id} · {order.date}
            </Text>
            <Text size="sm" c="dimmed">
              {personLabel}: {personName}
            </Text>
          </Stack>
        </Group>

        <Stack align="flex-end" gap="xs">
          <Text fw={800}>{order.total}</Text>
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

export function OrdersPage() {
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
          <Stack gap="sm">
            {purchaseOrders.map((order) => (
              <OrderCard key={order.id} order={order} type="purchase" />
            ))}
          </Stack>
        </Tabs.Panel>

        <Tabs.Panel value="sales" pt="md">
          <Stack gap="sm">
            {salesOrders.map((order) => (
              <OrderCard key={order.id} order={order} type="sale" />
            ))}
          </Stack>
        </Tabs.Panel>
      </Tabs>
    </Stack>
  );
}
