import { Button, Center, Stack, Text, Title } from "@mantine/core";
import { useState } from "react";
import { useAuth } from "./hooks/useAuth";
import { LoginPage } from "./pages/LoginPage";
import { RegisterPage } from "./pages/RegisterPage";

function App() {
  const { isAuthenticated, user, logout } = useAuth();
  const [authPage, setAuthPage] = useState("login");

  if (!isAuthenticated) {
    return authPage === "register" ? (
      <RegisterPage onLoginClick={() => setAuthPage("login")} />
    ) : (
      <LoginPage onRegisterClick={() => setAuthPage("register")} />
    );
  }

  return (
    <Center mih="100vh" p="md">
      <Stack align="center">
        <Title order={1}>Welcome back</Title>
        <Text c="dimmed">You are signed in as {user.email}.</Text>
        <Button variant="light" color="gray" onClick={logout}>
          Sign out
        </Button>
      </Stack>
    </Center>
  );
}

export default App;
