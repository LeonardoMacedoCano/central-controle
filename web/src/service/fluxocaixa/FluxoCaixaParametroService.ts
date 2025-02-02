import DefaultService from '../DefaultService';
import { useMessage } from '../../contexts';
import { FluxoCaixaParametro } from '../../types';

interface FluxoCaixaParametroApi {
  getParametros: (token: string) => Promise<FluxoCaixaParametro | undefined>;
  saveParametros: (token: string, data: FluxoCaixaParametro) => Promise<{ id: number } | undefined>;
}

const FluxoCaixaParametroService = (): FluxoCaixaParametroApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getParametros = async (token: string): Promise<FluxoCaixaParametro | undefined> => {
    try {
      return await request<FluxoCaixaParametro>('get', 'fluxo-caixa-parametro', token);
    } catch (error) {
      return undefined;
    }
  };

  const saveParametros = async (token: string, data: FluxoCaixaParametro): Promise<{ id: number } | undefined> => {
    try {
      return await request<{ id: number }>('post', 'fluxo-caixa-parametro', token, message, data);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getParametros,
    saveParametros
  };
};

export default FluxoCaixaParametroService;
