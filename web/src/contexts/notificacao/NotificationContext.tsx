import { createContext, useState, useEffect, ReactNode, useContext } from "react";
import { NotificacaoService } from "../../service";
import { AuthContext } from "../auth/AuthContext";

type NotificationContextType = {
  unreadCount: number;
  refreshNotifications: () => void;
};

export const NotificationContext = createContext<NotificationContextType>(null!);

type NotificationProviderProps = {
  children: ReactNode;
};

export const NotificationProvider = ({ children }: NotificationProviderProps) => {
  const [unreadCount, setUnreadCount] = useState(0);
  const { usuario } = useContext(AuthContext);
  const notificacaoService = NotificacaoService();

  const fetchUnreadCount = async () => {
    try {
      const count = await notificacaoService.getTotalNotificacoesNaoLidas(usuario?.token!);
      setUnreadCount(count || 0);
    } catch (error) {
      console.error("Erro ao buscar notificações não lidas:", error);
    }
  };

  useEffect(() => {
    fetchUnreadCount();
    const interval = setInterval(fetchUnreadCount, 15000);
    return () => clearInterval(interval);
  }, []);

  return (
    <NotificationContext.Provider value={{ unreadCount, refreshNotifications: fetchUnreadCount }}>
      {children}
    </NotificationContext.Provider>
  );
};
