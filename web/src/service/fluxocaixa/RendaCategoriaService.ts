import DefaultService from '../DefaultService';
import { Categoria, PagedResponse } from '../../types';
import { useMessage } from '../../contexts';

interface RendaCategoriaApi {
  getAllCategorias: (token: string) => Promise<Categoria[] | undefined>;
  getAllCategoriasPaged: (token: string, page: number, size: number) => Promise<PagedResponse<Categoria> | undefined>;
  saveCategoria: (token: string, data: Categoria) => Promise<{ id: number } | undefined>;
  deleteCategoria: (token: string, id: string | number) => Promise<void | undefined>;
}

const RendaCategoriaService = (): RendaCategoriaApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getAllCategorias = async (token: string): Promise<Categoria[] | undefined> => {
      try {
        return await request<Categoria[]>('get', 'renda-categoria', token);
      } catch (error) {
        return undefined;
      }
    };
  
    const getAllCategoriasPaged = async (token: string, page: number, size: number): Promise<PagedResponse<Categoria> | undefined> => {
      try {
        return await request<PagedResponse<Categoria>>('get', `renda-categoria/search?page=${page}&size=${size}`, token);
      } catch (error) {
        return undefined;
      }
    };
  
    const saveCategoria = async (token: string, data: Categoria): Promise<{ id: number } | undefined> => {
      try {
        return await request<{ id: number }>('post', 'renda-categoria', token, message, data);
      } catch (error) {
        return undefined;
      }
    };
  
    const deleteCategoria = async (token: string, id: string | number): Promise<void | undefined> => {
      try {
        await request<undefined>('delete', `renda-categoria/${id}`, token, message);
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

export default RendaCategoriaService;
