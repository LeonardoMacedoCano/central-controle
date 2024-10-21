import { useMessage } from "../../contexts";
import { Servico } from "../../types";
import DefaultService from "../DefaultService";

interface ServicoApi {
  getAllServicos: (token: string) => Promise<Servico[] | undefined>;
  changeContainerStatusByName: (token: string, name: string, action: string) => Promise<void | undefined>;
}

const ServicoService = (): ServicoApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getAllServicos = async (token: string): Promise<Servico[] | undefined> => {
    try {
      return await request<Servico[]>('get', 'servico', token);
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

  return {
    getAllServicos,
    changeContainerStatusByName
  };
};

export default ServicoService;
