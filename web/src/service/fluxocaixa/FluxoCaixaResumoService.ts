import DefaultService from '../DefaultService';
import { ResumoFluxoCaixa } from '../../types';

interface FluxoCaixaResumoServiceApi {
  getResumoFluxoCaixa: (token: string) => Promise<ResumoFluxoCaixa | undefined>;
}

const FluxoCaixaResumoService = (): FluxoCaixaResumoServiceApi => {
  const { request } = DefaultService();

  const getResumoFluxoCaixa = async (token: string): Promise<ResumoFluxoCaixa | undefined> => {
    try {
      return await request<ResumoFluxoCaixa>('get', 'fluxo-caixa-resumo', token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getResumoFluxoCaixa
  };
};

export default FluxoCaixaResumoService;
