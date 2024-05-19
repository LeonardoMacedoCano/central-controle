import { useContext, useEffect, useState, ReactNode } from "react";
import UsuarioConfigService from "../../service/UsuarioConfigService";
import { AuthContext } from "../auth/AuthContext";
import { useMessage } from "../message/ContextMessageProvider";
import { UsuarioConfig, initialUsuarioConfigState } from "../../types/UsuarioConfig";
import { UsuarioConfigContext } from "./UsuarioConfigContext";

interface UsuarioConfigProviderProps {
  children: ReactNode;
}

export const UsuarioConfigProvider = ({ children }: UsuarioConfigProviderProps) => {
  const [usuarioConfig, setUsuarioConfig] = useState<UsuarioConfig>(initialUsuarioConfigState);
  const usuarioConfigService = UsuarioConfigService();
  const { usuario } = useContext(AuthContext);
  const { showErrorWithLog } = useMessage();

  useEffect(() => {
    const carregarConfiguracao = async () => {
      if (!usuario?.token) return;
  
      try {
        const result = await usuarioConfigService.getUsuarioConfigByUsuario(usuario.token);
        setUsuarioConfig(result || initialUsuarioConfigState);
      } catch (error) {
        showErrorWithLog('Erro ao carregar as configurações do usuário.', error);
      }
    };
    
    carregarConfiguracao();
  }, [usuario?.token]);

  return (
    <UsuarioConfigContext.Provider value={{ usuarioConfig }}>
      {children}
    </UsuarioConfigContext.Provider>
  );
};
