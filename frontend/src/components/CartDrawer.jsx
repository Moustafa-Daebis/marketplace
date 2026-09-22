import {
  Badge,
  Button,
  Divider,
  Drawer,
  Group,
  Paper,
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

export function CartDrawer({ opened, onClose }) {
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
              0 items
            </Badge>
          </Group>

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
        </Stack>

        <Stack gap="md">
          <Divider />

          <Stack gap="xs">
            <Group justify="space-between">
              <Text c="dimmed">Subtotal</Text>
              <Text fw={700}>$0.00</Text>
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
                $0.00
              </Text>
            </Group>
          </Paper>

          <Group grow>
            <Button
              variant="light"
              color="red"
              disabled
              leftSection={<IconTrash size={18} />}
            >
              Clear
            </Button>
            <Button disabled rightSection={<IconArrowRight size={18} />}>
              Checkout
            </Button>
          </Group>
        </Stack>
      </Stack>
    </Drawer>
  );
}
