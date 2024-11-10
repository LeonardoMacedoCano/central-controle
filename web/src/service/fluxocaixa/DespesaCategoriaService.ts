import DefaultService from '../DefaultService';
import { Categoria } from '../../types';

interface DespesaCategoriaApi {
  getTodasCategoriasDespesa: (token: string) => Promise<Categoria[] | undefined>;
}

const DespesaCategoriaService = (): DespesaCategoriaApi => {
  const { request } = DefaultService();

  const getTodasCategoriasDespesa = async (token: string): Promise<Categoria[] | undefined> => {
    try {
      return await request<Categoria[]>('get', 'despesa-categoria', token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getTodasCategoriasDespesa
  };
};

export default DespesaCategoriaService;
