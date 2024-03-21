import axios, { AxiosResponse } from 'axios';
import { TipoContextoMensagens } from '../contexts/mensagens';

interface ApiResponse {
  success?: string;
  error?: string;
}

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

const handleErrorMessage = (contextoMensagem: TipoContextoMensagens, error: any, defaultMessage: string) => {
  const errorMessage = error.response?.data?.error || defaultMessage;
  contextoMensagem.exibirErro(errorMessage);
};

const handleSuccessMessage = (contextoMensagem: TipoContextoMensagens, response: AxiosResponse<ApiResponse>) => {
  const successMessage = response?.data?.success;
  if (successMessage) {
    contextoMensagem.exibirSucesso(successMessage);
  }
};

export const RequestApi = async <T>(
  method: 'get' | 'post' | 'put' | 'delete',
  url: string,
  token?: string,
  contextoMensagem?: TipoContextoMensagens,
  data?: Record<string, any>
): Promise<T | undefined> => {
  try {
    const headers: Record<string, string> = token ? { Authorization: `Bearer ${token}` } : {};
    const config = { headers, data };

    const response = await api.request({
      method,
      url,
      ...config,
    });

    if (contextoMensagem) { 
      handleSuccessMessage(contextoMensagem, response);
    }
    return response.data as T;
  } catch (error: any) {
    if (contextoMensagem) {
      handleErrorMessage(contextoMensagem, error, `Erro na requisição ${method.toUpperCase()} para ${url}`);
    }
    return undefined;
  }
};

const DefaultService = () => {
  return {
    request: RequestApi
  };
};

export default DefaultService;
