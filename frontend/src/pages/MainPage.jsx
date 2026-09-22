import { AppShell, Container, Paper, Stack, Text, Title } from "@mantine/core";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { MainNavbar } from "../components/MainNavbar";
import { useAuth } from "../hooks/useAuth";

export function MainPage() {
  const { user, logout } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate("/login", { replace: true });
  }

  return (
    <AppShell header={{ height: 64 }} padding="md">
      <AppShell.Header>
        <MainNavbar
          onLogout={handleLogout}
          onHomeClick={() => navigate("/")}
          onItemsClick={() => navigate("/items")}
          onProfileClick={() => navigate("/profile")}
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
            <Outlet />
          )}
        </Container>
      </AppShell.Main>
    </AppShell>
  );
}
