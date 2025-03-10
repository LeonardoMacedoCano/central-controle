import DefaultService from "./DefaultService";
import { useMessage } from "../contexts";
import { 
  Lancamento,
  PagedResponse,
} from "../types";
import { FilterDTO } from "../types/Filters";

interface LancamentoApi {
  saveLancamento: (token: string, data: Lancamento) => Promise<{ id: number } | undefined>;
  deleteLancamento: (token: string, id: string | number) => Promise<void | undefined>;
  getLancamentos: (token: string, page: number, size: number, filters?: FilterDTO[]) => Promise<PagedResponse<Lancamento> | undefined>;
  getLancamento: (token: string, id: string | number) => Promise<Lancamento | undefined>;
}

const LancamentoService = (): LancamentoApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const saveLancamento = async (token: string, data: Lancamento): Promise<{ id: number } | undefined> => {
    try {
      return await request<{ id: number }>('post', 'lancamento', token, message, data);
    } catch (error) {
      return undefined;
    }
  };

  const deleteLancamento = async (token: string, id: string | number): Promise<void | undefined> => {
    try {
      await request<undefined>('delete', `lancamento/${id}`, token, message);
    } catch (error) {
      return undefined;
    }
  };

  const getLancamentos = async (token: string, page: number, size: number, filters?: FilterDTO[]): Promise<PagedResponse<Lancamento> | undefined> => {
    try {
      return await request<PagedResponse<Lancamento>>('post', `lancamento/search?page=${page}&size=${size}`, token, undefined, filters || []);
    } catch (error) {
      return undefined;
    }
  };

  const getLancamento = async (token: string, id: string | number): Promise<Lancamento | undefined> => {
    try {
      return await request<Lancamento>('get', `lancamento/${id}`, token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    saveLancamento,
    deleteLancamento,
    getLancamentos,
    getLancamento
  };
};

export default LancamentoService;