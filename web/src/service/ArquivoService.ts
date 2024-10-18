import DefaultService from "./DefaultService";

interface ArquivoApi {
  getArquivoById: (token: string, id: string | number) => Promise<Blob | undefined>;
}

const ArquivoService = (): ArquivoApi => {
  const { request } = DefaultService();

  const getArquivoById = async (token: string, id: string | number): Promise<Blob | undefined> => {
    try {
      return await request<Blob>('get', `arquivo/${id}`, token, undefined, undefined, 'blob');
    } catch (error) {
      return undefined;
    }
  };

  return {
    getArquivoById
  };
};

export default ArquivoService;
