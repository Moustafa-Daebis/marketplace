import { useState } from "react";
import { useAuth } from "./hooks/useAuth";
import { LoginPage } from "./pages/LoginPage";
import { RegisterPage } from "./pages/RegisterPage";
import { MainPage } from "./pages/MainPage";

function App() {
  const { isAuthenticated, user, logout } = useAuth();
  const [authPage, setAuthPage] = useState("login");

  if (!isAuthenticated) {
    return authPage === "register" ? (
      <RegisterPage onLoginClick={() => setAuthPage("login")} />
    ) : (
      <LoginPage onRegisterClick={() => setAuthPage("register")} />
    );
  }

  return <MainPage user={user} onLogout={logout} />;
}

export default App;
