import DefaultService from './DefaultService';
import { Usuario } from '../types/Usuario';
import { useMessage } from '../contexts/message/ContextMessageProvider';

interface AuthApi {
  validateToken: (token: string) => Promise<{ usuario: Usuario } | undefined>;
  login: (username: string, senha: string) => Promise<{ usuario: Usuario } | undefined>;
}

const AuthService = (): AuthApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const validateToken = async (token: string): Promise<{ usuario: Usuario } | undefined> => {
    try {
      return await request<{ usuario: Usuario }>('get', `auth/validateToken?token=${token}`);
    } catch (error) {
      return undefined;
    }
  };

  const login = async (username: string, senha: string): Promise<{ usuario: Usuario } | undefined> => {
    try {
      return await request<{ usuario: Usuario }>('post', 'auth/login', undefined, message, { username, senha });
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
