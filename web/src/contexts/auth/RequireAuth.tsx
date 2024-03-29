import { useContext } from "react";
import { AuthContext } from "./AuthContext";
import { Login } from "../../pages/login";

export const RequireAuth = ({ children }: { children: JSX.Element }) => {
  const auth = useContext(AuthContext);

  if (!auth.usuario) {
    return <Login />;
  }

  return children;
}