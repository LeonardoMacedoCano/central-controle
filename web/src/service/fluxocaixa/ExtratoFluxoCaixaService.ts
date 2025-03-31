import { useMessage } from "../../contexts";
import { formatDateToYMDString } from "../../utils";
import DefaultService from "../DefaultService";

interface ExtratoFluxoCaixaApi {
  importExtratoFaturaCartao: (token: string, file: File, dataVencimento: Date) => Promise<void | undefined>;
  importExtratoContaCorrente: (token: string, file: File) => Promise<void | undefined>;
  importExtratoMovimentacaoB3: (token: string, file: File) => Promise<void | undefined>;
}

const ExtratoFluxoCaixaService = (): ExtratoFluxoCaixaApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const importExtratoFaturaCartao = async (token: string, file: File, dataVencimento: Date): Promise<void | undefined> => {
    try {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("dataVencimento", formatDateToYMDString(dataVencimento));

      await request<undefined>('post', 'extrato-fluxo-caixa/import-extrato-fatura-cartao', token, message, formData);
    } catch (error) {
      return undefined;
    }
  };

  const importExtratoContaCorrente = async (token: string, file: File): Promise<void | undefined> => {
    try {
      const formData = new FormData();
      formData.append("file", file);

      await request<undefined>('post', 'extrato-fluxo-caixa/import-extrato-conta-corrente', token, message, formData);
    } catch (error) {
      return undefined;
    }
  };

  const importExtratoMovimentacaoB3 = async (token: string, file: File): Promise<void | undefined> => {
    try {
      const formData = new FormData();
      formData.append("file", file);

      await request<undefined>('post', 'extrato-fluxo-caixa/import-extrato-movimentacao-b3', token, message, formData);
    } catch (error) {
      return undefined;
    }
  };

  return {
    importExtratoFaturaCartao,
    importExtratoContaCorrente,
    importExtratoMovimentacaoB3
  };
};

export default ExtratoFluxoCaixaService;