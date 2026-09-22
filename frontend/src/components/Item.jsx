import { Badge, Button, Card, Group, Image, Stack, Text } from "@mantine/core";
import { IconShoppingCart } from "@tabler/icons-react";

export function Item({ item, onAddToCart }) {
  return (
    <Card withBorder radius="md" padding="md" h="100%">
      <Card.Section>
        <Image
          src={item.image}
          alt={item.title}
          height={190}
          fallbackSrc="https://placehold.co/600x400?text=Marketplace+item"
        />
      </Card.Section>

      <Stack gap="sm" mt="md" justify="space-between" h="calc(100% - 190px)">
        <div>
          <Group justify="space-between" align="flex-start" gap="xs">
            <Text fw={600} lineClamp={2}>
              {item.title}
            </Text>
            <Badge color="teal" variant="light">
              {item.condition}
            </Badge>
          </Group>
          <Text c="dimmed" size="sm" mt={6} lineClamp={2}>
            {item.description}
          </Text>
        </div>

        <Group justify="space-between" align="center">
          <Text fw={700} size="lg">
            {item.price}
          </Text>
          <Button
            size="sm"
            leftSection={<IconShoppingCart size={16} />}
            onClick={() => onAddToCart?.(item)}
          >
            Add to cart
          </Button>
        </Group>
      </Stack>
    </Card>
  );
}
