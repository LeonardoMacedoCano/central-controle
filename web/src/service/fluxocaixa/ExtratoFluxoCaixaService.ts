import { useMessage } from "../../contexts";
import { formatDateToYMDString } from "../../utils";
import DefaultService from "../DefaultService";

interface ExtratoFluxoCaixaApi {
  importExtratoMensalCartao: (token: string, file: File, dataVencimento: Date) => Promise<void | undefined>;
  importExtratoContaCorrente: (token: string, file: File) => Promise<void | undefined>;
  importExtratoAtivosB3: (token: string, file: File) => Promise<void | undefined>;
}

const ExtratoFluxoCaixaService = (): ExtratoFluxoCaixaApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const importExtratoMensalCartao = async (token: string, file: File, dataVencimento: Date): Promise<void | undefined> => {
    try {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("dataVencimento", formatDateToYMDString(dataVencimento));

      await request<undefined>('post', 'extrato-fluxo-caixa/import-extrato-mensal-cartao', token, message, formData);
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

  const importExtratoAtivosB3 = async (token: string, file: File): Promise<void | undefined> => {
    try {
      const formData = new FormData();
      formData.append("file", file);

      await request<undefined>('post', 'extrato-fluxo-caixa/import-extrato-ativos-b3', token, message, formData);
    } catch (error) {
      return undefined;
    }
  };

  return {
    importExtratoMensalCartao,
    importExtratoContaCorrente,
    importExtratoAtivosB3
  };
};

export default ExtratoFluxoCaixaService;