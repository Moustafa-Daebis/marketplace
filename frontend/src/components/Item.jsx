import {
  Badge,
  Card,
  Group,
  Image,
  Stack,
  Text,
  UnstyledButton,
} from "@mantine/core";
import { useNavigate } from "react-router-dom";

export function Item({ item }) {
  const navigate = useNavigate();

  return (
    <UnstyledButton
      onClick={() => navigate(`/items/${item.id}`)}
      w="100%"
      h="100%"
    >
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
          </Group>
        </Stack>
      </Card>
    </UnstyledButton>
  );
}
