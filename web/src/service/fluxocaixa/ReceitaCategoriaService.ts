import DefaultService from '../DefaultService';
import { Categoria } from '../../types';

interface ReceitaCategoriaApi {
  getTodasCategoriasReceita: (token: string) => Promise<Categoria[] | undefined>;
}

const ReceitaCategoriaService = (): ReceitaCategoriaApi => {
  const { request } = DefaultService();

  const getTodasCategoriasReceita = async (token: string): Promise<Categoria[] | undefined> => {
    try {
      return await request<Categoria[]>('get', 'receita-categoria', token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getTodasCategoriasReceita
  };
};

export default ReceitaCategoriaService;
