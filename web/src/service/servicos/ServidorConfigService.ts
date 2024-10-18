import { ServidorConfig } from "../../types";
import DefaultService from "../DefaultService";

interface ServidorConfigApi {
  getServidorConfig: (token: string) => Promise<ServidorConfig | undefined>;
}

const ServidorConfigService = (): ServidorConfigApi => {
  const { request } = DefaultService();

  const getServidorConfig = async (token: string): Promise<ServidorConfig | undefined> => {
    try {
      return await request<ServidorConfig>('get', 'servidor-config', token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getServidorConfig
  };
};

export default ServidorConfigService;
