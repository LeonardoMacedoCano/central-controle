import { useEffect, useState } from "react";
import  useApi  from "../../hooks/useApi";
import { AuthContext } from "./AuthContext";
import { Usuario } from "../../types/Usuario";

export const AuthProvider = ({ children }: { children: JSX.Element }) => {
    const [usuario, setUsuario] = useState<Usuario | null>(null);
    const api = useApi();

    useEffect(() => {
        const validateToken = async () => {
            const storageData = localStorage.getItem('authToken');
            if (storageData && !usuario) {
                const data = await api.validateToken(storageData);
                if (data?.usuario) {
                    setUsuario(data.usuario);
                } else {
                    setToken(''); 
                }
            } 
        }
        
        validateToken();
    }, [usuario]);

    const login = async (username: string, senha: string) => {
        const data = await api.login(username, senha);
        if (data.usuario) {
            setUsuario(data.usuario);
            setToken(data.usuario.token);
            return true;
        }
        return false;
    }

    const signout = async () => {
        setUsuario(null);
        setToken('');
    }

    const setToken = (token: string) => {
        localStorage.setItem('authToken', token);
    }

    return (
        <AuthContext.Provider value={{ usuario, login, signout }}>
            {children}
        </AuthContext.Provider>
    );
}