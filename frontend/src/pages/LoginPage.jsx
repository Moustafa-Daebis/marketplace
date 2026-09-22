import { Anchor, Center, Paper, Stack, Text, Title } from "@mantine/core";
import { LoginForm } from "../components/LoginForm";
import { useAuth } from "../hooks/useAuth";
import { Link, useNavigate } from "react-router-dom";

export function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();

  function handleLogin(user, token) {
    login(user, token);
    navigate("/", { replace: true });
  }
  return (
    <Center mih="100vh" p="md">
      <Paper withBorder shadow="sm" radius="md" p="xl" w="100%" maw={420}>
        <Stack gap="lg">
          <div>
            <Title order={1} ta="center">
              Welcome back
            </Title>
            <Text c="dimmed" size="sm" ta="center" mt={6}>
              Sign in to continue to your marketplace.
            </Text>
          </div>
          <LoginForm onSubmit={handleLogin} />
          <Text c="dimmed" size="sm" ta="center">
            New here?{" "}
            <Anchor component={Link} to="/register">
              Create an account
            </Anchor>
          </Text>
        </Stack>
      </Paper>
    </Center>
  );
}
