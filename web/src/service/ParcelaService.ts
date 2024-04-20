import DefaultService from './DefaultService';

interface ParcelaApi {
  getValorTotalParcelasMensal: (token: string, ano: number, mes: number) => Promise<number | undefined>;
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

  return {
    getValorTotalParcelasMensal
  };
};

export default ParcelaService;
