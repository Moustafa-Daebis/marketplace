import { Button, Group, Text } from "@mantine/core";
import { IconShoppingCart } from "@tabler/icons-react";
import { useDisclosure } from "@mantine/hooks";
import { CartDrawer } from "./CartDrawer";

export function MainNavbar({ onLogout }) {
  const [cartOpened, { open: openCart, close: closeCart }] = useDisclosure(false);

  return (
    <>
      <CartDrawer opened={cartOpened} onClose={closeCart} />
      <Group justify="space-between" wrap="wrap" gap="md" h="100%" px="md">
        <Text fw={700} size="lg">Marketplace</Text>
        <Group gap="xs" wrap="wrap">
          <Button variant="subtle">Items</Button>
          <Button variant="subtle">Sell</Button>
          <Button variant="subtle" aria-label="Cart" onClick={openCart}>
            <IconShoppingCart size={20} stroke={1.8} />
          </Button>
          <Button variant="subtle">Profile</Button>
          <Button variant="light" color="gray" onClick={onLogout}>Sign out</Button>
        </Group>
      </Group>
    </>
  );
}
