import axios from 'axios';
import { Despesa } from '../types/Despesa';
import { Tarefa } from '../types/Tarefa';
import { usarMensagens } from '../contexts/Mensagens';

const api = axios.create({
    baseURL: 'http://localhost:8080',
});

const useApi = () => {
    const mensagens = usarMensagens();

    const handleMensagemErro = (error: any, mensagemPadrao: string) => {
        if (error.response && error.response.data && error.response.data.error) {
            mensagens.exibirErro(error.response.data.error);
        } else {
            mensagens.exibirErro(mensagemPadrao);
        }
    };

    const handleMensagemSucesso = (response: any) => {
      if (response && response.data && response.data.success) {
          mensagens.exibirSucesso(response.data.success);
      }
    };

    const request = async (
        method: 'get' | 'post' | 'put' | 'delete',
        url: string,
        token?: string,
        data?: Record<string, any>
      ) => {
        try {
          let headers: Record<string, string> = {};
      
          if (token && token !== '') {
            headers = {
              Authorization: `Bearer ${token}`,
            };
          }
      
          if (method === 'get') {
            const response = await api.get(url, { headers });
            handleMensagemSucesso(response);
            return response.data;
          } else if (method === 'post') {
            const response = await api.post(url, data, { headers });
            handleMensagemSucesso(response);
            return response.data;
          } else if (method === 'put') {
            const response = await api.put(url, data, { headers });
            handleMensagemSucesso(response);
            return response.data;
          } else if (method === 'delete') {
            const response = await api.delete(url, { headers });
            handleMensagemSucesso(response);
            return response.data;
          } else {
            throw `Método ${method} não configurado.`;
          }
        } catch (error: any) {
          handleMensagemErro(error, `Erro na requisição ${method.toUpperCase()} para ${url}`);
          return undefined;
        }
      };
      

  return {
    validateToken: async (token: string) => 
        request('get', `auth/validateToken?token=${token}`),
    login: async (username: string, senha: string) =>
        request('post', 'auth/login', '', { username, senha }),
    listarDespesas: async (token: string, ano: number, mes: number) =>
        request('get', `/despesa/listar?ano=${ano}&mes=${mes}`, token),
    addDespesa: async (token: string, data: Despesa) =>
        request('post', '/despesa/add', token, {
            idCategoria: data.idCategoria,
            descricao: data.descricao,
            valor: data.valor,
            data: data.data,
        }),
    editarDespesa: async (token: string, data: Despesa) =>
        request('put', `/despesa/editar/${data.id}`, token, {
            idCategoria: data.idCategoria,
            descricao: data.descricao,
            valor: data.valor,
            data: data.data,
        }),
    excluirDespesa: async (token: string, id: number) =>
        request('delete', `/despesa/excluir/${id}`, token),
    listarTodasCategoriasDespesa: async (token: string) =>
        request('get', '/categoriadespesa/getTodasCategoriasDespesa', token),
    listarTarefas: async (token: string, ano: number, mes: number) =>
        request('get', `/tarefa/listar?ano=${ano}&mes=${mes}`, token),
    addTarefa: async (token: string, data: Tarefa) =>
        request('post', '/tarefa/add', token, {
            idCategoria: data.idCategoria,
            titulo: data.titulo,
            descricao: data.descricao,
            dataPrazo: data.dataPrazo,
            finalizado: data.finalizado,
        }),
    editarTarefa: async (token: string, data: Tarefa) =>
        request('put', `/tarefa/editar/${data.id}`, token, {
            idCategoria: data.idCategoria,
            titulo: data.titulo,
            descricao: data.descricao,
            dataPrazo: data.dataPrazo,
            finalizado: data.finalizado,
        }),
    excluirTarefa: async (token: string, id: number) =>
        request('delete', `/tarefa/excluir/${id}`, token),
    listarTodasCategoriasTarefa: async (token: string) =>
        request('get', '/categoriatarefa/getTodasCategoriasTarefa', token),
  };
};

export default useApi;
