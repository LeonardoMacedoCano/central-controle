import { createContext } from 'react';
import { Usuario } from '../../types/Usuario';

export type AuthContextType = {
  usuario: Usuario | null;
  login: (username: string, senha: string) => Promise<boolean>;
  signout: () => void;
}

export const AuthContext = createContext<AuthContextType>(null!);