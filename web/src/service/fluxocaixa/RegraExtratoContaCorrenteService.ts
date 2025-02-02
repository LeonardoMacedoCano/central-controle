import DefaultService from '../DefaultService';
import { PagedResponse, RegraExtratoContaCorrente } from '../../types';
import { useMessage } from '../../contexts';

interface RegraExtratoContaCorrenteServiceApi {
  getRegra: (token: string, id: string | number) => Promise<RegraExtratoContaCorrente | undefined>;
  getAllRegras: (token: string) => Promise<RegraExtratoContaCorrente[] | undefined>;
  getAllRegrasPaged: (token: string, page: number, size: number) => Promise<PagedResponse<RegraExtratoContaCorrente> | undefined>;
  saveRegra: (token: string, data: RegraExtratoContaCorrente) => Promise<{ id: number } | undefined>;
  deleteRegra: (token: string, id: string | number) => Promise<void | undefined>;
}

const RegraExtratoContaCorrenteService = (): RegraExtratoContaCorrenteServiceApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getRegra = async (token: string, id: string | number): Promise<RegraExtratoContaCorrente | undefined> => {
      try {
        return await request<RegraExtratoContaCorrente>('get', `regra-extrato-conta-corrente/${id}`, token);
      } catch (error) {
        return undefined;
      }
    };

  const getAllRegras = async (token: string): Promise<RegraExtratoContaCorrente[] | undefined> => {
    try {
      return await request<RegraExtratoContaCorrente[]>('get', 'regra-extrato-conta-corrente', token);
    } catch (error) {
      return undefined;
    }
  };

  const getAllRegrasPaged = async (token: string, page: number, size: number): Promise<PagedResponse<RegraExtratoContaCorrente> | undefined> => {
    try {
      return await request<PagedResponse<RegraExtratoContaCorrente>>('get', `regra-extrato-conta-corrente/search?page=${page}&size=${size}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const saveRegra = async (token: string, data: RegraExtratoContaCorrente): Promise<{ id: number } | undefined> => {
    try {
      return await request<{ id: number }>('post', 'regra-extrato-conta-corrente', token, message, data);
    } catch (error) {
      return undefined;
    }
  };

  const deleteRegra = async (token: string, id: string | number): Promise<void | undefined> => {
    try {
      await request<undefined>('delete', `regra-extrato-conta-corrente/${id}`, token, message);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getRegra,
    getAllRegras,
    getAllRegrasPaged,
    saveRegra,
    deleteRegra
  };
};

export default RegraExtratoContaCorrenteService;
