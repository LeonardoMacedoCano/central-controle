import { createContext } from 'react';
import { UsuarioConfig, initialUsuarioConfigState } from '../../types';

type UsuarioConfigContextType = {
  usuarioConfig: UsuarioConfig;
};

export const UsuarioConfigContext = createContext<UsuarioConfigContextType>({
  usuarioConfig: initialUsuarioConfigState,
});
