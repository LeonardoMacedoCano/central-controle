import axios, { AxiosResponse } from 'axios';
import { Despesa } from '../types/Despesa';
import { Tarefa } from '../types/Tarefa';
import { usarMensagens } from '../contexts/Mensagens';

interface ApiResponse {
  success?: string;
  error?: string;
}

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

const useApi = () => {
  const mensagens = usarMensagens();

  const handleMensagemErro = (error: any, mensagemPadrao: string) => {
    const errorMessage = error.response?.data?.error || mensagemPadrao;
    mensagens.exibirErro(errorMessage);
  };

  const handleMensagemSucesso = (response: AxiosResponse<ApiResponse>) => {
    const successMessage = response?.data?.success;
    if (successMessage) {
      mensagens.exibirSucesso(successMessage);
    }
  };

  const request = async <T>(
    method: 'get' | 'post' | 'put' | 'delete',
    url: string,
    token?: string,
    data?: Record<string, any>
  ): Promise<T | undefined> => {
    try {
      const headers: Record<string, string> = token ? { Authorization: `Bearer ${token}` } : {};
      let response: AxiosResponse<ApiResponse>;

      switch (method) {
        case 'get':
          response = await api.get(url, { headers });
          break;
        case 'post':
          response = await api.post(url, data, { headers });
          break;
        case 'put':
          response = await api.put(url, data, { headers });
          break;
        case 'delete':
          response = await api.delete(url, { headers });
          break;
        default:
          throw `Método ${method} não configurado.`;
      }

      handleMensagemSucesso(response);
      return response.data as T;
    } catch (error: any) {
      handleMensagemErro(error, `Erro na requisição ${method.toUpperCase()} para ${url}`);
      return undefined;
    }
  };

  const despesaPayload = (data: Despesa) => ({
    idCategoria: data.idCategoria,
    descricao: data.descricao,
    valor: data.valor,
    data: data.data,
  });

  const tarefaPayload = (data: Tarefa) => ({
    idCategoria: data.idCategoria,
    titulo: data.titulo,
    descricao: data.descricao,
    dataPrazo: data.dataPrazo,
    finalizado: data.finalizado,
  });

  return {
    validateToken: async (token: string) => 
      request<boolean>('get', `auth/validateToken?token=${token}`),
    login: async (username: string, senha: string) => 
      request<boolean>('post', 'auth/login', '', { username, senha }),
    listarDespesas: async (token: string, ano: number, mes: number) =>
      request<Despesa[]>('get', `/despesa/listar?ano=${ano}&mes=${mes}`, token),
    addDespesa: async (token: string, data: Despesa) =>
      request<Despesa>('post', '/despesa/add', token, despesaPayload(data)),
    editarDespesa: async (token: string, data: Despesa) =>
      request<Despesa>('put', `/despesa/editar/${data.id}`, token, despesaPayload(data)),
    excluirDespesa: async (token: string, id: number) =>
      request<boolean>('delete', `/despesa/excluir/${id}`, token),
    listarTodasCategoriasDespesa: async (token: string) =>
      request<string[]>('get', '/categoriadespesa/getTodasCategoriasDespesa', token),
    listarTarefas: async (token: string, ano: number, mes: number) =>
      request<Tarefa[]>('get', `/tarefa/listar?ano=${ano}&mes=${mes}`, token),
    addTarefa: async (token: string, data: Tarefa) =>
      request<Tarefa>('post', '/tarefa/add', token, tarefaPayload(data)),
    editarTarefa: async (token: string, data: Tarefa) =>
      request<Tarefa>('put', `/tarefa/editar/${data.id}`, token, tarefaPayload(data)),
    excluirTarefa: async (token: string, id: number) =>
      request<boolean>('delete', `/tarefa/excluir/${id}`, token),
    listarTodasCategoriasTarefa: async (token: string) =>
      request<string[]>('get', '/categoriatarefa/getTodasCategoriasTarefa', token),
  };
};

export default useApi;
