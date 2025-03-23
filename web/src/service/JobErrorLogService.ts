import DefaultService from "./DefaultService";
import { 
  JobErrorLog,
} from "../types";

interface JobErrorLogServiceApi {
  getJobErrorLog: (token: string, id: string | number) => Promise<JobErrorLog | undefined>;
}

const JobErrorLogService = (): JobErrorLogServiceApi => {
  const { request } = DefaultService();

  const getJobErrorLog = async (token: string, id: string | number): Promise<JobErrorLog | undefined> => {
    try {
      return await request<JobErrorLog>('get', `job-error-log/${id}`, token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getJobErrorLog
  };
};

export default JobErrorLogService;