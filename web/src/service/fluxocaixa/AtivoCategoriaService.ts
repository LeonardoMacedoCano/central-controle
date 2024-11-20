import DefaultService from '../DefaultService';
import { Categoria } from '../../types';

interface AtivoCategoriaApi {
  getTodasCategoriasAtivo: (token: string) => Promise<Categoria[] | undefined>;
}

const AtivoCategoriaService = (): AtivoCategoriaApi => {
  const { request } = DefaultService();

  const getTodasCategoriasAtivo = async (token: string): Promise<Categoria[] | undefined> => {
    try {
      return await request<Categoria[]>('get', 'ativo-categoria', token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getTodasCategoriasAtivo
  };
};

export default AtivoCategoriaService;
