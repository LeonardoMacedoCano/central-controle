import DefaultService from '../DefaultService';
import { Categoria, PagedResponse } from '../../types';
import { useMessage } from '../../contexts';

interface DespesaCategoriaApi {
  getTodasCategoriasDespesa: (token: string) => Promise<Categoria[] | undefined>;
  getTodasCategoriasDespesaPaged: (token: string, page: number, size: number) => Promise<PagedResponse<Categoria> | undefined>;
  saveCategoria: (token: string, data: Categoria) => Promise<{ id: number } | undefined>;
  deleteCategoria: (token: string, id: string | number) => Promise<void | undefined>;
}

const DespesaCategoriaService = (): DespesaCategoriaApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getTodasCategoriasDespesa = async (token: string): Promise<Categoria[] | undefined> => {
    try {
      return await request<Categoria[]>('get', 'despesa-categoria', token);
    } catch (error) {
      return undefined;
    }
  };

  const getTodasCategoriasDespesaPaged = async (token: string, page: number, size: number): Promise<PagedResponse<Categoria> | undefined> => {
    try {
      return await request<PagedResponse<Categoria>>('get', `despesa-categoria/search?page=${page}&size=${size}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const saveCategoria = async (token: string, data: Categoria): Promise<{ id: number } | undefined> => {
    try {
      return await request<{ id: number }>('post', 'despesa-categoria', token, message, data);
    } catch (error) {
      return undefined;
    }
  };

  const deleteCategoria = async (token: string, id: string | number): Promise<void | undefined> => {
    try {
      await request<undefined>('delete', `despesa-categoria/${id}`, token, message);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getTodasCategoriasDespesa,
    getTodasCategoriasDespesaPaged,
    saveCategoria,
    deleteCategoria
  };
};

export default DespesaCategoriaService;
