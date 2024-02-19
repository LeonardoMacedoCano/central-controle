import useApi from './useApi';
import { Despesa } from '../types/Despesa';
import { Categoria } from '../types/Categoria';
import { usarMensagens } from '../contexts/Mensagens';

interface DespesaApi {
  listarCategoriasDespesa: (token: string) => Promise<Categoria[] | undefined>;
  listarDespesas: (token: string, ano: number, mes: number) => Promise<Despesa[] | undefined>;
  addDespesa: (token: string, data: Despesa) => Promise<Despesa | undefined>;
  editarDespesa: (token: string, data: Despesa) => Promise<Despesa | undefined>;
  excluirDespesa: (token: string, id: number) => Promise<boolean | undefined>;
}

const useDespesaApi = (): DespesaApi => {
  const { request } = useApi();
  const mensagens = usarMensagens();

  const despesaPayload = (data: Despesa) => ({
    idCategoria: data.idCategoria,
    descricao: data.descricao,
    valor: data.valor,
    data: data.data,
  });

  const listarCategoriasDespesa = async (token: string) => {
    try {
      return await request<Categoria[]>('get', 'categoriadespesa', token);
    } catch (error) {
      return undefined;
    }
  };

  const listarDespesas = async (token: string, ano: number, mes: number) => {
    try {
      return await request<Despesa[]>(`get`, `despesa?ano=${ano}&mes=${mes}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const addDespesa = async (token: string, data: Despesa) => {
    try {
      return await request<Despesa>('post', 'despesa', token, despesaPayload(data), mensagens);
    } catch (error) {
      return undefined;
    }
  };

  const editarDespesa = async (token: string, data: Despesa) => {
    try {
      return await request<Despesa>('put', `despesa/${data.id}`, token, despesaPayload(data), mensagens);
    } catch (error) {
      return undefined;
    }
  };

  const excluirDespesa = async (token: string, id: number) => {
    try {
      return await request<boolean>('delete', `despesa/${id}`, token, undefined, mensagens);
    } catch (error) {
      return undefined;
    }
  };

  return {
    listarCategoriasDespesa,
    listarDespesas,
    addDespesa,
    editarDespesa,
    excluirDespesa,
  };
};

export default useDespesaApi;
