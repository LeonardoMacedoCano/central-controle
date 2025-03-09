import DefaultService from "./DefaultService";
import { 
  Notificacao,
  PagedResponse,
} from "../types";
import { FilterDTO } from "../types/Filters";

interface NotificacaoApi {
  getNotificacao: (token: string, id: string | number) => Promise<Notificacao | undefined>;
  getNotificacoes: (token: string, page: number, size: number, filters?: FilterDTO[]) => Promise<PagedResponse<Notificacao> | undefined>;
  markAsRead: (token: string, id: string | number) => Promise<void | undefined>;
  getTotalNotificacoesNaoLidas: (token: string) => Promise<number | undefined>;
}

const NotificacaoService = (): NotificacaoApi => {
  const { request } = DefaultService();

  const getNotificacao = async (token: string, id: string | number): Promise<Notificacao | undefined> => {
    try {
      return await request<Notificacao>('get', `notificacao/${id}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getNotificacoes = async (token: string, page: number, size: number, filters?: FilterDTO[]): Promise<PagedResponse<Notificacao> | undefined> => {
    try {
      return await request<PagedResponse<Notificacao>>('post', `notificacao/search?page=${page}&size=${size}`, token, undefined, filters || []);
    } catch (error) {
      return undefined;
    }
  };

  const markAsRead = async (token: string, id: string | number): Promise<void | undefined> => {
    try {
      return await request<undefined>('post', `notificacao/${id}/read`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getTotalNotificacoesNaoLidas = async (token: string): Promise<number | undefined> => {
    try {
      return await request<number>('get', `notificacao/nao-lidas/total`, token);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getNotificacao,
    getNotificacoes,
    markAsRead,
    getTotalNotificacoesNaoLidas
  };
};

export default NotificacaoService;