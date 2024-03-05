import useApi from './useApi';
import { Ideia } from '../types/Ideia';
import { Categoria } from '../types/Categoria';
import { usarMensagens } from '../contexts/mensagens';

interface IdeiaApi {
  listarCategoriasIdeia: (token: string) => Promise<Categoria[] | undefined>;
  listarIdeias: (token: string, ano: number, mes: number) => Promise<Ideia[] | undefined>;
  addIdeia: (token: string, data: Ideia) => Promise<Ideia | undefined>;
  editarIdeia: (token: string, data: Ideia) => Promise<Ideia | undefined>;
  excluirIdeia: (token: string, id: number) => Promise<boolean | undefined>;
}

const useIdeiaApi = (): IdeiaApi => {
  const { request } = useApi();
  const mensagens = usarMensagens();

  const ideiaPayload = (data: Ideia) => ({
    idCategoria: data.idCategoria,
    titulo: data.titulo,
    descricao: data.descricao,
    dataPrazo: data.dataPrazo,
    finalizado: data.finalizado,
  });

  const listarCategoriasIdeia = async (token: string) => {
    try {
      return await request<Categoria[]>('get', 'categoriaideia', token);
    } catch (error) {
      return undefined;
    }
  };

  const listarIdeias = async (token: string, ano: number, mes: number) => {
    try {
      return await request<Ideia[]>(`get`, `ideia?ano=${ano}&mes=${mes}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const addIdeia = async (token: string, data: Ideia) => {
    try {
      return await request<Ideia>('post', 'ideia', token, ideiaPayload(data), mensagens);
    } catch (error) {
      return undefined;
    }
  };

  const editarIdeia = async (token: string, data: Ideia) => {
    try {
      return await request<Ideia>('put', `ideia/${data.id}`, token, ideiaPayload(data), mensagens);
    } catch (error) {
      return undefined;
    }
  };

  const excluirIdeia = async (token: string, id: number) => {
    try {
      return await request<boolean>('delete', `ideia/${id}`, token, undefined, mensagens);
    } catch (error) {
      return undefined;
    }
  };

  return {
    listarCategoriasIdeia,
    listarIdeias,
    addIdeia,
    editarIdeia,
    excluirIdeia,
  };
};

export default useIdeiaApi;
