import { Paper, Stack, Text, Title } from "@mantine/core";
import { useNavigate } from "react-router-dom";
import { CreateItemForm } from "../components/CreateItemForm";
import { useAuth } from "../hooks/useAuth";

export function CreateItemPage() {
  const { token } = useAuth();
  const navigate = useNavigate();

  return (
    <Paper withBorder radius="md" p="xl" maw={640} mx="auto">
      <Stack gap="lg">
        <div>
          <Title order={1}>Create a listing</Title>
          <Text c="dimmed" mt={4}>
            Add an item for other marketplace members to discover.
          </Text>
        </div>
        <CreateItemForm token={token} onSuccess={() => navigate("/items")} />
      </Stack>
    </Paper>
  );
}
