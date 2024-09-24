import DefaultService from "./DefaultService";
import { useMessage } from "../contexts";
import { 
  Lancamento,
  PagedResponse,
  TipoLancamentoEnum
} from "../types";
import { ExtratoFaturaCartaoDTO } from "../types/fluxocaixa/ExtratoFaturaCartao";
import { formatDateToYMDString } from "../utils";

interface LancamentoApi {
  createLancamento: (token: string, data: Lancamento) => Promise<{ id: number } | undefined>;
  updateLancamento: (token: string, id: number, data: Lancamento) => Promise<void | undefined>;
  deleteLancamento: (token: string, id: number) => Promise<void | undefined>;
  getLancamentos: (token: string, page: number, size: number, descricao?: string, tipo?: TipoLancamentoEnum, dataInicio?: string, dataFim?: string) => Promise<PagedResponse<Lancamento> | undefined>;
  getLancamento: (token: string, id: number) => Promise<Lancamento | undefined>;
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

  const updateLancamento = async (token: string, id: number, data: Lancamento): Promise<void | undefined> => {
    try {
      await request<undefined>('put', `lancamento/${id}`, token, message, lancamentoPayload(data));
    } catch (error) {
      return undefined;
    }
  };

  const deleteLancamento = async (token: string, id: number): Promise<void | undefined> => {
    try {
      await request<undefined>('delete', `lancamento/${id}`, token, message);
    } catch (error) {
      return undefined;
    }
  };

  const getLancamentos = async (token: string, page: number, size: number, descricao?: string, tipo?: TipoLancamentoEnum, dataInicio?: string, dataFim?: string): Promise<PagedResponse<Lancamento> | undefined> => {
    try {
      const params = new URLSearchParams();
      params.append('page', page.toString());
      params.append('size', size.toString());
      if (descricao !== undefined) params.append('descricao', descricao);
      if (tipo !== undefined) params.append('tipo', tipo);
      if (dataInicio !== undefined) params.append('dataInicio', dataInicio);
      if (dataFim !== undefined) params.append('dataFim', dataFim);

      return await request<PagedResponse<Lancamento>>('get', `lancamento?${params.toString()}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getLancamento = async (token: string, id: number): Promise<Lancamento | undefined> => {
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