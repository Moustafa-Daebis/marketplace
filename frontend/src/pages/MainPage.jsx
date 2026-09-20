import { AppShell, Container, Paper, Stack, Text, Title } from "@mantine/core";
import { MainNavbar } from "../components/MainNavbar";

export function MainPage({ user, onLogout }) {
  console.log(user);
  return (
    <AppShell header={{ height: 64 }} padding="md">
      <AppShell.Header>
        <MainNavbar onLogout={onLogout} />
      </AppShell.Header>
      <AppShell.Main>
        <Container size="lg" py="xl">
          <Paper withBorder radius="md" p="xl">
            <Stack gap="xs">
              <Title order={1}>Welcome, {user.firstName}</Title>
              <Text c="dimmed">
                Browse listings, add items to your cart, or start selling.
              </Text>
            </Stack>
          </Paper>
        </Container>
      </AppShell.Main>
    </AppShell>
  );
}
