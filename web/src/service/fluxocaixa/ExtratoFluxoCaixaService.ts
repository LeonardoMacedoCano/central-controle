import { useMessage } from "../../contexts";
import { ExtratoFaturaCartaoDTO } from "../../types/fluxocaixa/ExtratoFaturaCartao";
import { formatDateToYMDString } from "../../utils";
import DefaultService from "../DefaultService";

interface ExtratoFluxoCaixaApi {
  importExtratoFaturaCartao: (token: string, extratoFaturaCartaoDTO: ExtratoFaturaCartaoDTO) => Promise<void | undefined>;
  importExtratoConta: (token: string) => Promise<void | undefined>;
}

const ExtratoFluxoCaixaService = (): ExtratoFluxoCaixaApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const importExtratoFaturaCartao = async (token: string, extratoFaturaCartaoDTO: ExtratoFaturaCartaoDTO): Promise<void | undefined> => {
    try {
      const formData = new FormData();
      formData.append("file", extratoFaturaCartaoDTO.file!);
      formData.append("dataVencimento", formatDateToYMDString(extratoFaturaCartaoDTO.dataVencimento));

      await request<undefined>('post', 'extrato-fluxo-caixa/import-extrato-mensal-cartao', token, message, formData);
    } catch (error) {
      return undefined;
    }
  };

  const importExtratoConta = async (token: string): Promise<void | undefined> => {
    try {
      await request<undefined>('post', 'extrato-fluxo-caixa/import-extrato-conta', token, message);
    } catch (error) {
      return undefined;
    }
  };

  return {
    importExtratoFaturaCartao,
    importExtratoConta
  };
};

export default ExtratoFluxoCaixaService;