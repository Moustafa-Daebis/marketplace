import {
  Button,
  PasswordInput,
  SimpleGrid,
  Stack,
  TextInput,
} from "@mantine/core";
import axios from "axios";
import { useState } from "react";

const initialForm = {
  firstName: "",
  lastName: "",
  email: "",
  password: "",
  phoneNumber: "",
};

export function RegisterForm({ onSuccess }) {
  const [form, setForm] = useState(initialForm);
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  function updateField(field, value) {
    setForm((current) => ({ ...current, [field]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      setError("");
      setIsSubmitting(true);
      const baseUrl = import.meta.env.VITE_API_URL ?? "";
      await axios.post(`${baseUrl}/api/auth/register`, {
        ...form,
        firstName: form.firstName.trim(),
        lastName: form.lastName.trim(),
        email: form.email.trim(),
        phoneNumber: form.phoneNumber.trim(),
        role: "USER",
        isActive: true,
      });
      onSuccess();
    } catch (requestError) {
      setError(
        requestError.response?.data?.message ??
          "Unable to create your account. Please try again.",
      );
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
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
            disabled={isSubmitting}
          />
          <TextInput
            label="Last name"
            value={form.lastName}
            onChange={(event) =>
              updateField("lastName", event.currentTarget.value)
            }
            required
            disabled={isSubmitting}
          />
        </SimpleGrid>
        <TextInput
          label="Email"
          type="email"
          placeholder="you@example.com"
          value={form.email}
          onChange={(event) => updateField("email", event.currentTarget.value)}
          error={error}
          required
          disabled={isSubmitting}
        />
        <TextInput
          label="Phone number"
          type="tel"
          placeholder="+1 555 123 4567"
          value={form.phoneNumber}
          onChange={(event) =>
            updateField("phoneNumber", event.currentTarget.value)
          }
          required
          disabled={isSubmitting}
        />
        <PasswordInput
          label="Password"
          placeholder="Create a password"
          value={form.password}
          onChange={(event) =>
            updateField("password", event.currentTarget.value)
          }
          required
          disabled={isSubmitting}
        />
        <Button type="submit" fullWidth loading={isSubmitting}>
          Create account
        </Button>
      </Stack>
    </form>
  );
}
