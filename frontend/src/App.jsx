import { useAuth } from "./hooks/useAuth";
import { LoginPage } from "./pages/LoginPage";
import { RegisterPage } from "./pages/RegisterPage";
import { HomePage } from "./pages/HomePage";
import { MainPage } from "./pages/MainPage";
import { ProfilePage } from "./pages/ProfilePage";
import { ItemsPage } from "./pages/ItemsPage";
import { ItemPage } from "./pages/ItemPage";
import { CreateItemPage } from "./pages/CreateItemPage";
import { Navigate, Route, Routes, useLocation } from "react-router-dom";

function PublicRoute({ children }) {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <Navigate to="/" replace /> : children;
}

function RootRoute() {
  const { isAuthenticated } = useAuth();
  const location = useLocation();

  if (isAuthenticated) {
    return <MainPage />;
  }

  return location.pathname === "/" ? <HomePage /> : <Navigate to="/" replace />;
}

function App() {
  return (
    <Routes>
      <Route path="/" element={<RootRoute />}>
        <Route path="profile" element={<ProfilePage />} />
        <Route path="items" element={<ItemsPage />} />
        <Route path="items/:itemId" element={<ItemPage />} />
        <Route path="items/create" element={<CreateItemPage />} />
      </Route>
      <Route
        path="/login"
        element={
          <PublicRoute>
            <LoginPage />
          </PublicRoute>
        }
      />
      <Route
        path="/register"
        element={
          <PublicRoute>
            <RegisterPage />
          </PublicRoute>
        }
      />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
