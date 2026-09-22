import { Button, PasswordInput, Stack, TextInput } from "@mantine/core";
import axios from "axios";
import { useState } from "react";

export function LoginForm({ onSubmit }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    if (!email.trim() || !password) {
      setError("Enter your email address and password.");
      return;
    }
    try {
      setError("");
      setIsSubmitting(true);
      const baseUrl = import.meta.env.VITE_API_URL ?? "";
      const { data } = await axios.post(`${baseUrl}/api/auth/login`, {
        email: email.trim(),
        password,
      });

      onSubmit(data.user ?? { email: email.trim() }, data.token);
    } catch (requestError) {
      setError(
        requestError.response?.data?.message ?? "Incorrect email or password",
      );
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <Stack gap="md">
        <TextInput
          label="Email"
          placeholder="you@example.com"
          type="email"
          value={email}
          onChange={(event) => setEmail(event.currentTarget.value)}
          error={error}
          required
          disabled={isSubmitting}
        />
        <PasswordInput
          label="Password"
          placeholder="Your password"
          value={password}
          onChange={(event) => setPassword(event.currentTarget.value)}
          error={error}
          required
          disabled={isSubmitting}
        />
        <Button type="submit" fullWidth mt="xs" loading={isSubmitting}>
          Sign in
        </Button>
      </Stack>
    </form>
  );
}
