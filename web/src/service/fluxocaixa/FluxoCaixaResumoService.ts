import DefaultService from '../DefaultService';
import { ResumoFluxoCaixa } from '../../types';
import { useMessage } from '../../contexts';

interface FluxoCaixaResumoServiceApi {
  getResumoFluxoCaixa: (token: string) => Promise<ResumoFluxoCaixa | undefined>;
}

const FluxoCaixaResumoService = (): FluxoCaixaResumoServiceApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getResumoFluxoCaixa = async (token: string): Promise<ResumoFluxoCaixa | undefined> => {
    try {
      return await request<ResumoFluxoCaixa>('get', 'fluxo-caixa-resumo', token, message);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getResumoFluxoCaixa
  };
};

export default FluxoCaixaResumoService;
