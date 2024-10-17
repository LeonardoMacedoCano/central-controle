import { Servico } from "../../types";
import DefaultService from "../DefaultService";

interface ServicoApi {
  getAllServicos: (token: string) => Promise<Servico[] | undefined>;
}

const ServicoService = (): ServicoApi => {
  const { request } = DefaultService();

  const getAllServicos = async (token: string): Promise<Servico[] | undefined> => {
    try {
      return await request<Servico[]>('get', 'servico', token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getAllServicos
  };
};

export default ServicoService;
