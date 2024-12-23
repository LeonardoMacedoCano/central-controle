import DefaultService from '../DefaultService';
import { FluxoCaixaConfig } from '../../types';
import { useMessage } from '../../contexts';

interface FluxoCaixaConfigApi {
  getConfig: (token: string) => Promise<FluxoCaixaConfig | undefined>;
  saveConfig: (token: string, data: FluxoCaixaConfig) => Promise<{ id: number } | undefined>;
}

const FluxoCaixaConfigService = (): FluxoCaixaConfigApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getConfig = async (token: string): Promise<FluxoCaixaConfig | undefined> => {
    try {
      return await request<FluxoCaixaConfig>('get', 'fluxo-caixa-config', token);
    } catch (error) {
      return undefined;
    }
  };

  const saveConfig = async (token: string, data: FluxoCaixaConfig): Promise<{ id: number } | undefined> => {
    try {
      return await request<{ id: number }>('post', 'fluxo-caixa-config', token, message, data);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getConfig,
    saveConfig
  };
};

export default FluxoCaixaConfigService;
