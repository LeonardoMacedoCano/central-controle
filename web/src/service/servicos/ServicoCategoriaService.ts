import { ServicoCategoria } from "../../types";
import DefaultService from "../DefaultService";

interface ServicoApi {
  getCategoriasByServicoId: (token: string, id: string | number) => Promise<ServicoCategoria[] | undefined>;
}

const ServicoCategoriaService = (): ServicoApi => {
  const { request } = DefaultService();

  const getCategoriasByServicoId = async (token: string, id: string | number): Promise<ServicoCategoria[] | undefined> => {
    try {
      return await request<ServicoCategoria[]>('get', `servico-categoria/searchByServicoId/${id}`, token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getCategoriasByServicoId
  };
};

export default ServicoCategoriaService;
