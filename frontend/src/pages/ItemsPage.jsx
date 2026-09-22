import { SimpleGrid, Stack, Text, Title } from "@mantine/core";
import { Item } from "../components/Item";

const items = [
  {
    id: 1,
    title: "Minimal desk lamp",
    description: "A warm LED lamp for a focused workspace.",
    price: "$34.00",
    condition: "New",
    image: "https://placehold.co/600x400/e9ecef/495057?text=Desk+lamp",
  },
  {
    id: 2,
    title: "Wireless headphones",
    description: "Comfortable over-ear headphones with active noise cancellation.",
    price: "$89.00",
    condition: "Like new",
    image: "https://placehold.co/600x400/ddebf7/1c4966?text=Headphones",
  },
  {
    id: 3,
    title: "Everyday canvas backpack",
    description: "A durable backpack with room for work and weekend essentials.",
    price: "$52.00",
    condition: "Good",
    image: "https://placehold.co/600x400/f8e8d0/6b4f2a?text=Backpack",
  },
];

export function ItemsPage() {
  function handleAddToCart(item) {
    console.log("Added to cart:", item.title);
  }

  return (
    <Stack gap="xl">
      <div>
        <Title order={1}>Browse items</Title>
        <Text c="dimmed" mt={6}>
          Find something useful from the marketplace.
        </Text>
      </div>

      <SimpleGrid cols={{ base: 1, sm: 2, md: 3 }}>
        {items.map((item) => (
          <Item key={item.id} item={item} onAddToCart={handleAddToCart} />
        ))}
      </SimpleGrid>
    </Stack>
  );
}
