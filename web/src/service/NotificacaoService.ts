import DefaultService from "./DefaultService";
import { 
  Notificacao,
  PagedResponse,
} from "../types";
import { FilterDTO } from "../types/Filters";
import { useMessage } from "../contexts";

interface NotificacaoApi {
  getNotificacao: (token: string, id: string | number) => Promise<Notificacao | undefined>;
  getNotificacoes: (token: string, page: number, size: number, filters?: FilterDTO[]) => Promise<PagedResponse<Notificacao> | undefined>;
  markAsRead: (token: string, id: string | number, lido: boolean) => Promise<void | undefined>;
  getTotalNotificacoesNaoLidas: (token: string) => Promise<number | undefined>;
  deleteNotificacao: (token: string, id: string | number) => Promise<void | undefined>;
}

const NotificacaoService = (): NotificacaoApi => {
  const { request } = DefaultService();
  const message = useMessage();

  const getNotificacao = async (token: string, id: string | number): Promise<Notificacao | undefined> => {
    try {
      return await request<Notificacao>('get', `notificacao/${id}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getNotificacoes = async (token: string, page: number, size: number, filters?: FilterDTO[]): Promise<PagedResponse<Notificacao> | undefined> => {
    try {
      return await request<PagedResponse<Notificacao>>('post', `notificacao/search?page=${page}&size=${size}&sort=dataHora,desc`, token, undefined, filters || []);
    } catch (error) {
      return undefined;
    }
  };

  const markAsRead = async (token: string, id: string | number, lido: boolean): Promise<void | undefined> => {
    try {
      return await request<undefined>('post', `notificacao/${id}/alterar-status/${lido}`, token);
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

  const deleteNotificacao = async (token: string, id: string | number): Promise<void | undefined> => {
    try {
      await request<undefined>('delete', `notificacao/${id}`, token, message);
    } catch (error) {
      return undefined;
    }
  };

  return {
    getNotificacao,
    getNotificacoes,
    markAsRead,
    getTotalNotificacoesNaoLidas,
    deleteNotificacao
  };
};

export default NotificacaoService;