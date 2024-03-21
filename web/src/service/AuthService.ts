import DefaultService from './DefaultService';
import { Usuario } from '../types/Usuario';

interface AuthApi {
  validateToken: (token: string) => Promise<{ usuario: Usuario } | undefined>;
  login: (username: string, senha: string) => Promise<{ usuario: Usuario } | undefined>;
}

const AuthService = (): AuthApi => {
  const { request } = DefaultService();

  const validateToken = async (token: string) => {
    try {
      return await request<{ usuario: Usuario }>('get', `auth/validateToken?token=${token}`);
    } catch (error) {
      return undefined;
    }
  };

  const login = async (username: string, senha: string) => {
    try {
      return await request<{ usuario: Usuario }>('post', 'auth/login', undefined, undefined, { username, senha });
    } catch (error) {
      return undefined;
    }
  };

  return {
    validateToken,
    login,
  };
};

export default AuthService;
