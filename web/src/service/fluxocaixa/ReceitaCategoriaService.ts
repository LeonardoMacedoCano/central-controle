import DefaultService from '../DefaultService';
import { Categoria, PagedResponse } from '../../types';
import { useMessage } from '../../contexts';

interface ReceitaCategoriaApi {
  getAllCategorias: (token: string) => Promise<Categoria[] | undefined>;
    getAllCategoriasPaged: (token: string, page: number, size: number) => Promise<PagedResponse<Categoria> | undefined>;
    saveCategoria: (token: string, data: Categoria) => Promise<{ id: number } | undefined>;
    deleteCategoria: (token: string, id: string | number) => Promise<void | undefined>;
}

const ReceitaCategoriaService = (): ReceitaCategoriaApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getAllCategorias = async (token: string): Promise<Categoria[] | undefined> => {
      try {
        return await request<Categoria[]>('get', 'receita-categoria', token);
      } catch (error) {
        return undefined;
      }
    };
  
    const getAllCategoriasPaged = async (token: string, page: number, size: number): Promise<PagedResponse<Categoria> | undefined> => {
      try {
        return await request<PagedResponse<Categoria>>('get', `receita-categoria/search?page=${page}&size=${size}`, token);
      } catch (error) {
        return undefined;
      }
    };
  
    const saveCategoria = async (token: string, data: Categoria): Promise<{ id: number } | undefined> => {
      try {
        return await request<{ id: number }>('post', 'receita-categoria', token, message, data);
      } catch (error) {
        return undefined;
      }
    };
  
    const deleteCategoria = async (token: string, id: string | number): Promise<void | undefined> => {
      try {
        await request<undefined>('delete', `receita-categoria/${id}`, token, message);
      } catch (error) {
        return undefined;
      }
    };

  return {
    getAllCategorias,
    getAllCategoriasPaged,
    saveCategoria,
    deleteCategoria
  };
};

export default ReceitaCategoriaService;
