import { Drawer } from "@mantine/core";

export function CartDrawer({ opened, onClose }) {
  return (
    <Drawer opened={opened} onClose={onClose} title="Cart" position="right">
      {/* Cart items will be added here. */}
    </Drawer>
  );
}
