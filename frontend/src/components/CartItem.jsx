import { Group, Image, Paper, Stack, Text } from "@mantine/core";

function formatPrice(price) {
  const amount = Number(price);
  return Number.isFinite(amount) ? `$${amount.toFixed(2)}` : "$0.00";
}

export function CartItem({ cartItem }) {
  const item = cartItem.item ?? cartItem;
  const quantity = cartItem.quantity ?? 1;
  const price = item.price ?? cartItem.price ?? 0;
  const lineTotal = Number(price) * Number(quantity);

  return (
    <Paper withBorder radius="md" p="sm">
      <Group align="center" gap="md" wrap="nowrap">
        <Image
          src={item.image}
          alt={item.name ?? "Cart item"}
          width={76}
          height={76}
          radius="sm"
          fallbackSrc="https://placehold.co/160x160/e9ecef/495057?text=Item"
        />

        <Stack gap={4} flex={1}>
          <Text fw={700} lineClamp={2}>
            {item.itemName ?? "Marketplace item"}
          </Text>
          <Text size="sm" c="dimmed" lineClamp={1}>
            Qty: {quantity}
          </Text>
          <Group justify="space-between" gap="xs">
            <Text size="sm" c="dimmed">
              {formatPrice(price)} each
            </Text>
            <Text fw={700}>{formatPrice(lineTotal)}</Text>
          </Group>
        </Stack>
      </Group>
    </Paper>
  );
}
