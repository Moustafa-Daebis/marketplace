import { Button, Group, Menu, Text, UnstyledButton } from "@mantine/core";
import {
  IconHeart,
  IconPackage,
  IconSettings,
  IconShoppingCart,
  IconUser,
} from "@tabler/icons-react";
import { useDisclosure } from "@mantine/hooks";
import { CartDrawer } from "./CartDrawer";

export function MainNavbar({ onLogout, onHomeClick, onItemsClick, onProfileClick }) {
  const [cartOpened, { open: openCart, close: closeCart }] =
    useDisclosure(false);

  return (
    <>
      <CartDrawer opened={cartOpened} onClose={closeCart} />
      <Group justify="space-between" wrap="wrap" gap="md" h="100%" px="md">
        <UnstyledButton onClick={onHomeClick} aria-label="Go to home page">
          <Text fw={700} size="lg">
            Marketplace
          </Text>
        </UnstyledButton>
        <Group gap="xs" wrap="wrap">
          <Button variant="subtle" onClick={onItemsClick}>
            Items
          </Button>
          <Button variant="subtle">Sell</Button>
          <Button variant="subtle" aria-label="Cart" onClick={openCart}>
            <IconShoppingCart size={20} stroke={1.8} />
          </Button>
          <Menu shadow="md" width={180} position="bottom-end">
            <Menu.Target>
              <Button variant="subtle" rightSection={<IconUser size={16} />}>
                Profile
              </Button>
            </Menu.Target>
            <Menu.Dropdown>
              <Menu.Item
                leftSection={<IconUser size={16} />}
                onClick={onProfileClick}
              >
                My profile
              </Menu.Item>
              <Menu.Item leftSection={<IconSettings size={16} />}>
                Settings
              </Menu.Item>
              <Menu.Item leftSection={<IconPackage size={16} />}>
                Orders
              </Menu.Item>
              <Menu.Item leftSection={<IconHeart size={16} />}>
                Favourites
              </Menu.Item>
            </Menu.Dropdown>
          </Menu>
          <Button variant="light" color="gray" onClick={onLogout}>
            Sign out
          </Button>
        </Group>
      </Group>
    </>
  );
}
