import useApi from './useApi';

interface ParcelaApi {
  getValorTotalParcelasMensal: (token: string, ano: number, mes: number) => Promise<Number | undefined>;
}

const useParcelaApi = (): ParcelaApi => {
  const { request } = useApi();

  const getValorTotalParcelasMensal = async (token: string, ano: number, mes: number) => {
    try {
      return await request<Number>(`get`, `parcela/valor-total-mensal?ano=${ano}&mes=${mes}`, token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getValorTotalParcelasMensal  
  };
};

export default useParcelaApi;
