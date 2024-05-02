import { useEffect, useState } from "react";
import AuthService from "../../service/AuthService";
import { AuthContext } from "./AuthContext";
import { Usuario } from "../../types/Usuario";

export const AuthProvider = ({ children }: { children: JSX.Element }) => {
  const [usuario, setUsuario] = useState<Usuario | null>(null);
  const authService = AuthService();

  useEffect(() => {
    const validateToken = async () => {
      const storageData = localStorage.getItem('authToken');
      if (storageData && !usuario) {
        const data = await authService.validateToken(storageData);
        if (data?.usuario) {
          setUsuario(data.usuario);
        } else {
          clearToken(); 
        }
      } 
    }
    validateToken();
  }, [usuario]);

  const login = async (username: string, senha: string) => {
    const data = await authService.login(username, senha);
    if (data?.usuario) {
      setUsuario(data.usuario);
      setToken(data.usuario.token);
      return true;
    }
    return false;
  }

  const signout = () => {
    setUsuario(null);
    clearToken();
  }

  const setToken = (token: string) => {
    localStorage.setItem('authToken', token);
  }

  const clearToken = () => {
    localStorage.removeItem('authToken');
  }

  return (
    <AuthContext.Provider value={{ usuario, login, signout }}>
      {children}
    </AuthContext.Provider>
  );
}
