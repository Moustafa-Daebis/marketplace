import {
  Alert,
  Button,
  NumberInput,
  Stack,
  TextInput,
  Textarea,
} from "@mantine/core";
import axios from "axios";
import { useState } from "react";

const initialForm = {
  name: "",
  description: "",
  price: "",
};

export function CreateItemForm({ token, onSuccess }) {
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
      const { data } = await axios.post(
        `${import.meta.env.VITE_API_URL}/api/items`,
        {
          name: form.name.trim(),
          description: form.description.trim(),
          price: Number(form.price),
        },
        { headers: { Authorization: `Bearer ${token}` } },
      );

      onSuccess(data.data);
    } catch (requestError) {
      setError(
        requestError.response?.data?.message ??
          "Unable to create your listing. Please try again.",
      );
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <Stack gap="md">
        <TextInput
          label="Item name"
          placeholder="e.g. iPhone 16 Pro"
          value={form.name}
          onChange={(event) => updateField("name", event.currentTarget.value)}
          required
          disabled={isSubmitting}
        />
        <Textarea
          label="Description"
          placeholder="Describe the item's condition and details"
          value={form.description}
          onChange={(event) =>
            updateField("description", event.currentTarget.value)
          }
          minRows={4}
          required
          disabled={isSubmitting}
        />
        <NumberInput
          label="Price"
          placeholder="0.00"
          value={form.price}
          onChange={(value) => updateField("price", value)}
          min={0}
          decimalScale={2}
          fixedDecimalScale
          prefix="$"
          required
          disabled={isSubmitting}
        />
        <Button type="submit" loading={isSubmitting}>
          Create listing
        </Button>
        {error && <Alert color="red">{error}</Alert>}
      </Stack>
    </form>
  );
}
