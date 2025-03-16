import { useEffect, useState, useContext } from "react";
import { AuthService } from "../../service";
import { AuthContext } from "../auth/AuthContext";
import { Usuario } from "../../types";
import { ThemeContext } from "../theme/ThemeControlProvider";

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [usuario, setUsuario] = useState<Usuario | null>(null);
  const authService = AuthService();
  const { loadUserTheme } = useContext(ThemeContext);

  useEffect(() => {
    const validateToken = async () => {
      const storageData = localStorage.getItem('authToken');
      if (storageData && !usuario) {
        const data = await authService.validateToken(storageData);
        if (data?.usuario) {
          setUsuario(data.usuario);
          
          if (data.usuario.idTema) {
            loadUserTheme(data.usuario.idTema, data.usuario.token);
          }
        } else {
          clearToken(); 
        }
      } 
    }
    validateToken();
  }, []);

  const login = async (username: string, senha: string) => {
    const data = await authService.login(username, senha);
    
    if (data?.usuario) {
      setUsuario(data.usuario);
      setToken(data.usuario.token);
      
      if (data.usuario.idTema) {
        loadUserTheme(data.usuario.idTema, data.usuario.token);
      }
      
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