import { useEffect, useMemo, useState } from "react";
import { AuthContext } from "./authContext";

const storageKey = "marketplace-user";
const tokenStorageKey = "marketplace-token";

function getTokenExpiry(token) {
  try {
    const payload = token.split(".")[1];
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
    const decodedPayload = JSON.parse(atob(base64));
    return typeof decodedPayload.exp === "number"
      ? decodedPayload.exp * 1000
      : null;
  } catch {
    return null;
  }
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem(storageKey);
    return savedUser ? JSON.parse(savedUser) : null;
  });
  const [token, setToken] = useState(() =>
    localStorage.getItem(tokenStorageKey),
  );

  useEffect(() => {
    if (user) localStorage.setItem(storageKey, JSON.stringify(user));
    else localStorage.removeItem(storageKey);
  }, [user]);

  useEffect(() => {
    if (token) localStorage.setItem(tokenStorageKey, token);
    else localStorage.removeItem(tokenStorageKey);
  }, [token]);

  useEffect(() => {
    if (!token) return undefined;

    const expiry = getTokenExpiry(token);

    const delay = expiry
      ? Math.min(Math.max(expiry - Date.now(), 0), 2_147_483_647)
      : 0;

    const timeoutId = window.setTimeout(() => {
      setUser(null);
      setToken(null);
    }, delay);
    console.log(timeoutId);
    return () => window.clearTimeout(timeoutId);
  }, [token]);

  const value = useMemo(
    () => ({
      user,
      token,
      isAuthenticated: Boolean(user && token),
      login: (nextUser, nextToken) => {
        setUser(nextUser);
        setToken(nextToken);
      },
      updateUser: (changes) =>
        setUser((currentUser) => ({ ...currentUser, ...changes })),
      logout: () => {
        setUser(null);
        setToken(null);
      },
    }),
    [token, user],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
