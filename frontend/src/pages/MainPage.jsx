import { AppShell, Container, Paper, Stack, Text, Title } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { CartDrawer } from "../components/CartDrawer";
import { MainNavbar } from "../components/MainNavbar";
import { useAuth } from "../hooks/useAuth";

export function MainPage() {
  const { user, logout } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();
  const [cartOpened, { open: openCart, close: closeCart }] =
    useDisclosure(false);

  function handleLogout() {
    logout();
    navigate("/", { replace: true });
  }

  return (
    <AppShell header={{ height: 64 }} padding="md">
      <CartDrawer opened={cartOpened} onClose={closeCart} />
      <AppShell.Header>
        <MainNavbar
          onLogout={handleLogout}
          onHomeClick={() => navigate("/")}
          onItemsClick={() => navigate("/items")}
          onCreateItemClick={() => navigate("/items/create")}
          onProfileClick={() => navigate("/profile")}
          onCartClick={openCart}
        />
      </AppShell.Header>
      <AppShell.Main>
        <Container size="lg" py="xl">
          {location.pathname === "/" ? (
            <Paper withBorder radius="md" p="xl">
              <Stack gap="xs">
                <Title order={1}>Welcome, {user.firstName}</Title>
                <Text c="dimmed">
                  Browse listings, add items to your cart, or start selling.
                </Text>
              </Stack>
            </Paper>
          ) : (
            <Outlet context={{ openCart }} />
          )}
        </Container>
      </AppShell.Main>
    </AppShell>
  );
}
