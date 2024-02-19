import axios, { AxiosResponse } from 'axios';

interface ApiResponse {
  success?: string;
  error?: string;
}

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

const handleMensagemErro = (mensagens: any, error: any, mensagemPadrao: string) => {
  const errorMessage = error.response?.data?.error || mensagemPadrao;
  mensagens.exibirErro(errorMessage);
};

const handleMensagemSucesso = (mensagens: any, response: AxiosResponse<ApiResponse>) => {
  const successMessage = response?.data?.success;
  if (successMessage) {
    mensagens.exibirSucesso(successMessage);
  }
};

export const request = async <T>(
  method: 'get' | 'post' | 'put' | 'delete',
  url: string,
  token?: string,
  data?: Record<string, any>,
  usarMensagens?: any
): Promise<T | undefined> => {
  try {
    const headers: Record<string, string> = token ? { Authorization: `Bearer ${token}` } : {};
    const config = { headers, data };

    const response = await api.request({
      method,
      url,
      ...config,
    });

    handleMensagemSucesso(usarMensagens, response);
    return response.data as T;
  } catch (error: any) {
    handleMensagemErro(usarMensagens, error, `Erro na requisição ${method.toUpperCase()} para ${url}`);
    return undefined;
  }
};

const useApi = () => {
  return {
    request: request
  };
};

export default useApi;
