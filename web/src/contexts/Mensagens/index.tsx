import React, { ReactNode, createContext, useContext, useState } from 'react';
import * as styles from './styles';

interface TipoContextoMensagens {
  exibirErro: (mensagem: string) => void;
  exibirSucesso: (mensagem: string) => void;
}

const ContextoMensagens = createContext<TipoContextoMensagens | undefined>(undefined);

interface PropriedadesProvedorMensagens {
  children: ReactNode;
}

const ExibicaoMensagens: React.FC<{ tipo: 'erro' | 'sucesso'; mensagem: string }> = ({ tipo, mensagem }) => {
  const ComponenteMensagem = tipo === 'erro' ? styles.MensagemErro : styles.MensagemSucesso;

  return mensagem ? <ComponenteMensagem>{mensagem}</ComponenteMensagem> : null;
};

export const ProvedorMensagens: React.FC<PropriedadesProvedorMensagens> = ({ children }) => {
  const [mensagemErro, setMensagemErro] = useState<string | null>(null);
  const [mensagemSucesso, setMensagemSucesso] = useState<string | null>(null);

  function limparMensagens() {
    setMensagemErro(null);
    setMensagemSucesso(null);
  }

  const exibirErro = (mensagem: string) => {
    limparMensagens();
    setMensagemErro(mensagem);
    setTimeout(() => setMensagemErro(null), 5000);
  };

  const exibirSucesso = (mensagem: string) => {
    limparMensagens();
    setMensagemSucesso(mensagem);
    setTimeout(() => setMensagemSucesso(null), 5000);
  };

  return (
    <ContextoMensagens.Provider value={{ exibirErro, exibirSucesso }}>
      {children}
      <ExibicaoMensagens tipo="erro" mensagem={mensagemErro || ''} />
      <ExibicaoMensagens tipo="sucesso" mensagem={mensagemSucesso || ''} />
    </ContextoMensagens.Provider>
  );
};

export const usarMensagens = () => {
  const contexto = useContext(ContextoMensagens);

  if (!contexto) {
    throw new Error('usarMensagens deve ser utilizado dentro de um ProvedorMensagens');
  }

  return contexto;
};
