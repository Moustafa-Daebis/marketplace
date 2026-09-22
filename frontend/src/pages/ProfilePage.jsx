import {
  Button,
  Paper,
  SimpleGrid,
  Stack,
  Text,
  TextInput,
  Title,
} from "@mantine/core";
import axios from "axios";
import { useState } from "react";
import { useAuth } from "../hooks/useAuth";

export function ProfilePage() {
  const { user, token, updateUser } = useAuth();
  const [form, setForm] = useState({
    firstName: user.firstName ?? "",
    lastName: user.lastName ?? "",
    email: user.email ?? "",
    phoneNumber: user.phoneNumber ?? "",
  });
  const [saved, setSaved] = useState(false);
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  function updateField(field, value) {
    setSaved(false);
    setError("");
    setForm((current) => ({ ...current, [field]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    const changes = {
      firstName: form.firstName.trim(),
      lastName: form.lastName.trim(),
      phoneNumber: form.phoneNumber.trim(),
    };

    try {
      setError("");
      setIsSubmitting(true);
      const baseUrl = import.meta.env.VITE_API_URL ?? "";
      const { data } = await axios.patch(`${baseUrl}/api/users`, changes, {
        headers: { Authorization: `Bearer ${token}` },
      });

      updateUser(data.data ?? changes);
      setSaved(true);
    } catch (requestError) {
      setError(
        requestError.response?.data?.message ??
          "Unable to update your profile. Please try again.",
      );
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <Paper withBorder radius="md" p="xl" maw={640} mx="auto">
      <Stack gap="lg">
        <div>
          <Title order={1}>My profile</Title>
          <Text c="dimmed" mt={4}>
            View and update your contact details.
          </Text>
        </div>

        <form onSubmit={handleSubmit}>
          <Stack gap="md">
            <SimpleGrid cols={{ base: 1, sm: 2 }}>
              <TextInput
                label="First name"
                value={form.firstName}
                onChange={(event) =>
                  updateField("firstName", event.currentTarget.value)
                }
                required
              />
              <TextInput
                label="Last name"
                value={form.lastName}
                onChange={(event) =>
                  updateField("lastName", event.currentTarget.value)
                }
                required
              />
            </SimpleGrid>
            <TextInput label="Email" type="email" value={form.email} readOnly />
            <TextInput
              label="Phone number"
              type="tel"
              value={form.phoneNumber}
              onChange={(event) =>
                updateField("phoneNumber", event.currentTarget.value)
              }
            />
            <Button type="submit" mt="xs" loading={isSubmitting}>
              Save changes
            </Button>
            {saved && (
              <Text c="teal" size="sm">
                Your profile has been updated.
              </Text>
            )}
            {error && (
              <Text c="red" size="sm">
                {error}
              </Text>
            )}
          </Stack>
        </form>
      </Stack>
    </Paper>
  );
}
