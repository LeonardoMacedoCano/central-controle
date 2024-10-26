import { useMessage } from "../../contexts";
import { FilterDTO, PagedResponse, Servico, ServicoCategoria } from "../../types";
import DefaultService from "../DefaultService";

interface ServicoApi {
  getServicos: (token: string, page: number, size: number, filters?: FilterDTO[]) => Promise<PagedResponse<Servico> | undefined>;
  changeContainerStatusByName: (token: string, name: string, action: string) => Promise<void | undefined>;
  getAllServicoCategoria: (token: string) => Promise<ServicoCategoria[] | undefined>;
}

const ServicoService = (): ServicoApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getServicos = async (token: string, page: number, size: number, filters?: FilterDTO[]): Promise<PagedResponse<Servico> | undefined> => {
    try {
      const sort = 'id,asc';
      return await request<PagedResponse<Servico>>('post', `servico/search?page=${page}&size=${size}&sort=${sort}`, token, undefined, filters || []);
    } catch (error) {
      return undefined;
    }
  };

  const changeContainerStatusByName = async (token: string, name: string, action: string): Promise<void | undefined> => {
    try {
      return await request<undefined>('post', `servico/status/${name}/${action}`, token, message);
    } catch (error) {
      console.error(`Error changing status for ${name}:`, error);
      return undefined;
    }
  };

  const getAllServicoCategoria = async (token: string): Promise<ServicoCategoria[] | undefined> => {
    try {
      return await request<ServicoCategoria[]>('get', `servico/categorias`, token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getServicos,
    changeContainerStatusByName,
    getAllServicoCategoria
  };
};

export default ServicoService;
