import { Anchor, Center, Paper, Stack, Text, Title } from "@mantine/core";
import { RegisterForm } from "../components/RegisterForm";

export function RegisterPage({ onLoginClick }) {
  return (
    <Center mih="100vh" p="md">
      <Paper withBorder shadow="sm" radius="md" p="xl" w="100%" maw={520}>
        <Stack gap="lg">
          <div>
            <Title order={1} ta="center">Create your account</Title>
            <Text c="dimmed" size="sm" ta="center" mt={6}>Join the marketplace in just a few steps.</Text>
          </div>
          <RegisterForm onSuccess={onLoginClick} />
          <Text c="dimmed" size="sm" ta="center">
            Already have an account?{" "}
            <Anchor component="button" type="button" onClick={onLoginClick}>Sign in</Anchor>
          </Text>
        </Stack>
      </Paper>
    </Center>
  );
}
