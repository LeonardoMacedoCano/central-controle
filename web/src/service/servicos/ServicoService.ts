import { DockerStatusEnum, Servico } from "../../types";
import DefaultService from "../DefaultService";

interface ServicoApi {
  getAllServicos: (token: string) => Promise<Servico[] | undefined>;
  changeContainerStatusByName: (token: string, name: string, action: string) => Promise<DockerStatusEnum | undefined>;
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

  const changeContainerStatusByName = async (token: string, name: string, action: string): Promise<DockerStatusEnum | undefined> => {
    try {
      return await request<DockerStatusEnum>('post', `servico/status/${name}/${action}`, token);
    } catch (error) {
      console.error(`Error changing status for ${name}:`, error);
      return undefined;
    }
  };

  return {
    getAllServicos,
    changeContainerStatusByName
  };
};

export default ServicoService;
