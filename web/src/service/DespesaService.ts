import DefaultService from './DefaultService';
import { PagedResponse } from '../types/PagedResponse';
import { DespesaResumoMensal } from '../types/DespesaResumoMensal';

interface DespesaApi {
  listarDespesaResumoMensal: (token: string, page: number, size: number, ano: number, mes: number) => Promise< PagedResponse<DespesaResumoMensal> | undefined>;
}

const DespesaService = (): DespesaApi => {
  const { request } = DefaultService();

  const listarDespesaResumoMensal = async (token: string, page: number, size: number, ano: number, mes: number) => {
    try {
      return await request<PagedResponse<DespesaResumoMensal>>(`get`, `despesa?page=${page}&size=${size}&ano=${ano}&mes=${mes}`, token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    listarDespesaResumoMensal
  };
};

export default DespesaService;
