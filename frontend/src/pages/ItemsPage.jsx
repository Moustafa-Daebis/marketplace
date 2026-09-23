import { useEffect, useState } from "react";
import axios from "axios";
import {
  Alert,
  Card,
  Group,
  SimpleGrid,
  Skeleton,
  Stack,
  Text,
  Title,
} from "@mantine/core";
import { Item } from "../components/Item";
import { useAuth } from "../hooks/useAuth";

export function ItemsPage() {
  const { token } = useAuth();
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchItems() {
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_API_URL}/api/items`,
          {
            headers: { Authorization: `Bearer ${token}` },
          },
        );
        const apiItems = response.data?.data ?? [];

        setItems(
          apiItems.map((item) => ({
            ...item,
            title: item.name,
            price: `$${Number(item.price).toFixed(2)}`,
            condition: "Available",
            image:
              "https://placehold.co/600x400/e9ecef/495057?text=Marketplace+item",
          })),
        );
      } catch {
        setError("Unable to load items. Please try again later.");
      } finally {
        setLoading(false);
      }
    }

    fetchItems();
  }, [token]);

  function handleAddToCart(item) {
    console.log("Added to cart:", item.name);
  }

  return (
    <Stack gap="xl">
      <div>
        <Title order={1}>Browse items</Title>
        <Text c="dimmed" mt={6}>
          Find something useful from the marketplace.
        </Text>
      </div>

      {loading && (
        <SimpleGrid cols={{ base: 1, sm: 2, md: 3 }}>
          {Array.from({ length: 3 }, (_, index) => (
            <Card key={index} withBorder radius="md" padding="md">
              <Skeleton height={190} radius="sm" />
              <Stack gap="sm" mt="md">
                <Group justify="space-between">
                  <Skeleton height={18} width="55%" />
                  <Skeleton height={22} width={72} radius="xl" />
                </Group>
                <Skeleton height={14} width="90%" />
                <Skeleton height={14} width="70%" />
                <Group justify="space-between" mt="md">
                  <Skeleton height={24} width={70} />
                  <Skeleton height={36} width={120} radius="sm" />
                </Group>
              </Stack>
            </Card>
          ))}
        </SimpleGrid>
      )}

      {error && <Alert color="red">{error}</Alert>}

      {!loading && !error && items.length === 0 && (
        <Text c="dimmed">No items are available right now.</Text>
      )}

      {!loading && !error && items.length > 0 && (
        <SimpleGrid cols={{ base: 1, sm: 2, md: 3 }}>
          {items.map((item) => (
            <Item key={item.id} item={item} onAddToCart={handleAddToCart} />
          ))}
        </SimpleGrid>
      )}
    </Stack>
  );
}
