import useApi from './useApi';
import { Tarefa } from '../types/Tarefa';
import { Categoria } from '../types/Categoria';
import { usarMensagens } from '../contexts/mensagens';

interface TarefaApi {
  listarCategoriasTarefa: (token: string) => Promise<Categoria[] | undefined>;
  listarTarefas: (token: string, ano: number, mes: number) => Promise<Tarefa[] | undefined>;
  addTarefa: (token: string, data: Tarefa) => Promise<Tarefa | undefined>;
  editarTarefa: (token: string, data: Tarefa) => Promise<Tarefa | undefined>;
  excluirTarefa: (token: string, id: number) => Promise<boolean | undefined>;
}

const useTarefaApi = (): TarefaApi => {
  const { request } = useApi();
  const mensagens = usarMensagens();

  const tarefaPayload = (data: Tarefa) => ({
    idCategoria: data.idCategoria,
    titulo: data.titulo,
    descricao: data.descricao,
    dataPrazo: data.dataPrazo,
    finalizado: data.finalizado,
  });

  const listarCategoriasTarefa = async (token: string) => {
    try {
      return await request<Categoria[]>('get', 'categoriatarefa', token);
    } catch (error) {
      return undefined;
    }
  };

  const listarTarefas = async (token: string, ano: number, mes: number) => {
    try {
      return await request<Tarefa[]>(`get`, `tarefa?ano=${ano}&mes=${mes}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const addTarefa = async (token: string, data: Tarefa) => {
    try {
      return await request<Tarefa>('post', 'tarefa', token, tarefaPayload(data), mensagens);
    } catch (error) {
      return undefined;
    }
  };

  const editarTarefa = async (token: string, data: Tarefa) => {
    try {
      return await request<Tarefa>('put', `tarefa/${data.id}`, token, tarefaPayload(data), mensagens);
    } catch (error) {
      return undefined;
    }
  };

  const excluirTarefa = async (token: string, id: number) => {
    try {
      return await request<boolean>('delete', `tarefa/${id}`, token, undefined, mensagens);
    } catch (error) {
      return undefined;
    }
  };

  return {
    listarCategoriasTarefa,
    listarTarefas,
    addTarefa,
    editarTarefa,
    excluirTarefa,
  };
};

export default useTarefaApi;
