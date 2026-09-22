import {
  Button,
  Container,
  Group,
  Image,
  Paper,
  Stack,
  Text,
  ThemeIcon,
  Title,
} from "@mantine/core";
import { IconArrowRight, IconLogin } from "@tabler/icons-react";
import { Link } from "react-router-dom";

export function HomePage() {
  return (
    <Container size="lg" py={{ base: 48, sm: 80 }}>
      <Stack gap="xl">
        <Group justify="space-between" align="center">
          <Group gap="sm">
            <ThemeIcon size={38} radius="md" color="white" variant="filled">
              <Image src="/favicon.svg" alt="Marketplace" w={24} h={24} />
            </ThemeIcon>
            <Text fw={700} size="xl">
              Marketplace
            </Text>
          </Group>
          <Button
            component={Link}
            to="/login"
            variant="subtle"
            leftSection={<IconLogin size={18} />}
          >
            Sign in
          </Button>
        </Group>

        <Paper withBorder radius="md" p={{ base: "xl", sm: 48 }}>
          <Stack gap="lg" maw={680}>
            <Title order={1}>Buy and sell with your local marketplace</Title>
            <Text c="dimmed" size="lg">
              Discover listed items, create your own listings, and manage your
              marketplace account in one place.
            </Text>
            <Group gap="sm">
              <Button
                component={Link}
                to="/register"
                size="md"
                rightSection={<IconArrowRight size={18} />}
              >
                Create account
              </Button>
              <Button component={Link} to="/login" size="md" variant="light">
                Sign in
              </Button>
            </Group>
          </Stack>
        </Paper>
      </Stack>
    </Container>
  );
}
