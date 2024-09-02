import DefaultService from '../DefaultService';
import { 
  PagedResponse,
  DespesaResumoMensal,
  Despesa,
  Categoria
} from '../../types';
import { useMessage } from '../../contexts';
import { formatDateToTimeZone } from '../../utils';

interface DespesaApi {
  gerarDespesa: (token: string, data: Despesa) => Promise<{ idDespesa: number } | undefined>;
  editarDespesa: (token: string, id: number, data: Despesa) => Promise<void | undefined>;
  excluirDespesa: (token: string, id: number) => Promise<void | undefined>;
  listarDespesaResumoMensal: (token: string, page: number, size: number, ano: number, mes: number) => Promise<PagedResponse<DespesaResumoMensal> | undefined>;
  getDespesaByIdWithParcelas: (token: string, id: number) => Promise<Despesa | undefined>;
  getTodasCategoriasDespesa: (token: string) => Promise<Categoria[] | undefined>;
}

const DespesaService = (): DespesaApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const despesaPayload = (data: Despesa) => ({
    categoria: data.categoria,
    descricao: data.descricao,
    parcelas: data.parcelas.map(parcela => ({
      ...parcela,
      dataVencimento: formatDateToTimeZone(new Date(parcela.dataVencimento), 'America/Sao_Paulo', { year: 'numeric', month: '2-digit', day: '2-digit' }).split('/').reverse().join('-')
    }))
  });

  const gerarDespesa = async (token: string, data: Despesa): Promise<{ idDespesa: number } | undefined> => {
    try {
      return await request<{ idDespesa: number }>('post', 'despesa', token, message, despesaPayload(data));
    } catch (error) {
      return undefined;
    }
  };

  const editarDespesa = async (token: string, id: number, data: Despesa): Promise<void | undefined> => {
    try {
      await request<undefined>('put', `despesa/${id}`, token, message, despesaPayload(data));
    } catch (error) {
      return undefined;
    }
  };

  const excluirDespesa = async (token: string, id: number): Promise<void | undefined> => {
    try {
      await request<undefined>('delete', `despesa/${id}`, token, message);
    } catch (error) {
      return undefined;
    }
  };

  const listarDespesaResumoMensal = async (token: string, page: number, size: number, ano: number, mes: number): Promise<PagedResponse<DespesaResumoMensal> | undefined> => {
    try {
      return await request<PagedResponse<DespesaResumoMensal>>('get', `despesa?page=${page}&size=${size}&ano=${ano}&mes=${mes}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getDespesaByIdWithParcelas = async (token: string, id: number): Promise<Despesa | undefined> => {
    try {
      return await request<Despesa>('get', `despesa/${id}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getTodasCategoriasDespesa = async (token: string): Promise<Categoria[] | undefined> => {
    try {
      return await request<Categoria[]>('get', 'categoriadespesa', token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    gerarDespesa,
    editarDespesa,
    excluirDespesa,
    listarDespesaResumoMensal,
    getDespesaByIdWithParcelas,
    getTodasCategoriasDespesa
  };
};

export default DespesaService;
