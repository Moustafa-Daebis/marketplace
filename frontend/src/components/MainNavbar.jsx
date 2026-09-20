import { Button, Group, Text } from "@mantine/core";

export function MainNavbar({ onLogout }) {
  return (
    <Group justify="space-between" wrap="wrap" gap="md" h="100%" px="md">
      <Text fw={700} size="lg">Marketplace</Text>
      <Group gap="xs" wrap="wrap">
        <Button variant="subtle">Items</Button>
        <Button variant="subtle">Sell</Button>
        <Button variant="subtle">Cart</Button>
        <Button variant="subtle">Profile</Button>
        <Button variant="light" color="gray" onClick={onLogout}>Sign out</Button>
      </Group>
    </Group>
  );
}
