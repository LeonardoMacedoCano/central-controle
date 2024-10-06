import DefaultService from "./DefaultService";
import { useMessage } from "../contexts";
import { 
  Lancamento,
  PagedResponse,
} from "../types";
import { ExtratoFaturaCartaoDTO } from "../types/fluxocaixa/ExtratoFaturaCartao";
import { formatDateToYMDString } from "../utils";
import { FilterDTO } from "../types/Filters";

interface LancamentoApi {
  createLancamento: (token: string, data: Lancamento) => Promise<{ id: number } | undefined>;
  updateLancamento: (token: string, id: string | number, data: Lancamento) => Promise<void | undefined>;
  deleteLancamento: (token: string, id: string | number) => Promise<void | undefined>;
  getLancamentos: (token: string, page: number, size: number, filters?: FilterDTO[]) => Promise<PagedResponse<Lancamento> | undefined>;
  getLancamento: (token: string, id: string | number) => Promise<Lancamento | undefined>;
  importExtratoFaturaCartao: (token: string, extratoFaturaCartaoDTO: ExtratoFaturaCartaoDTO) => Promise<void | undefined>;
}

const LancamentoService = (): LancamentoApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const lancamentoPayload = (data: Lancamento) => ({
    descricao: data.descricao,
    tipo: data.tipo,
    itemDTO: data.itemDTO
  });

  const createLancamento = async (token: string, data: Lancamento): Promise<{ id: number } | undefined> => {
    try {
      return await request<{ id: number }>('post', 'lancamento', token, message, lancamentoPayload(data));
    } catch (error) {
      return undefined;
    }
  };

  const updateLancamento = async (token: string, id: string | number, data: Lancamento): Promise<void | undefined> => {
    try {
      await request<undefined>('put', `lancamento/${id}`, token, message, lancamentoPayload(data));
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

  const importExtratoFaturaCartao = async (token: string, extratoFaturaCartaoDTO: ExtratoFaturaCartaoDTO): Promise<void | undefined> => {
    try {
      const formData = new FormData();
      formData.append("file", extratoFaturaCartaoDTO.file!);
      formData.append("dataVencimento", formatDateToYMDString(extratoFaturaCartaoDTO.dataVencimento));

      await request<undefined>('post', 'lancamento/import-extrato-fatura-cartao', token, message, formData);
    } catch (error) {
      return undefined;
    }
  };

  return {
    createLancamento,
    updateLancamento,
    deleteLancamento,
    getLancamentos,
    getLancamento,
    importExtratoFaturaCartao
  };
};

export default LancamentoService;