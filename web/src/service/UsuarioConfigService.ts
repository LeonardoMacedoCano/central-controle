import { UsuarioConfig } from '../types/UsuarioConfig';
import DefaultService from './DefaultService';
import { useMessage } from '../contexts/message/ContextMessageProvider';

interface UsuarioConfigApi {
  getUsuarioConfigByUsuario: (token: string) => Promise<UsuarioConfig | undefined>;
  editarUsuarioConfig: (token: string, id: number, data: UsuarioConfig) => Promise<void | undefined>;
}

const UsuarioConfigService = (): UsuarioConfigApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const usuarioConfigPayload = (data: UsuarioConfig) => ({
    id: data.id,
    despesaNumeroItemPagina: data.despesaNumeroItemPagina,
    despesaValorMetaMensal: data.despesaValorMetaMensal,
    despesaDiaPadraoVencimento: data.despesaDiaPadraoVencimento,
    despesaFormaPagamentoPadrao: data.despesaFormaPagamentoPadrao
  });

  const getUsuarioConfigByUsuario = async (token: string): Promise<UsuarioConfig | undefined> => {
    try {
      return await request<UsuarioConfig>('get', 'usuarioconfig', token);
    } catch (error) {
      return undefined;
    }
  };

  const editarUsuarioConfig = async (token: string, id: number, data: UsuarioConfig): Promise<void | undefined> => {
    try {
      await request<undefined>('put', `usuarioconfig/${id}`, token, message, usuarioConfigPayload(data));
    } catch (error) {
      return undefined;
    }
  };

  return {
    getUsuarioConfigByUsuario,
    editarUsuarioConfig
  };
};

export default UsuarioConfigService;
