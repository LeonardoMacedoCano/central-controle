import DefaultService from '../DefaultService';
import { FormaPagamento } from '../../types';

interface ParcelaApi {
  getValorTotalParcelasMensal: (token: string, ano: number, mes: number) => Promise<number | undefined>;
  getTodasFormaPagamento: (token: string) => Promise<FormaPagamento[] | undefined>;
}

const ParcelaService = (): ParcelaApi => {
  const { request } = DefaultService();

  const getValorTotalParcelasMensal = async (token: string, ano: number, mes: number): Promise<number | undefined> => {
    try {
      return await request<number>('get', `parcela/valor-total-mensal?ano=${ano}&mes=${mes}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getTodasFormaPagamento = async (token: string): Promise<FormaPagamento[] | undefined> => {
    try {
      return await request<FormaPagamento[]>('get', 'formapagamento', token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getValorTotalParcelasMensal,
    getTodasFormaPagamento
  };
};

export default ParcelaService;
