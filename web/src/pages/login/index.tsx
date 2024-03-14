import { ChangeEvent, useContext, useState } from "react";
import { AuthContext } from "../../contexts/auth/AuthContext";
import { usarMensagens } from '../../contexts/mensagens';
import { MdAccountCircle, MdLock } from 'react-icons/md';
import { HiEye, HiEyeOff } from 'react-icons/hi';
import * as C from './styles';

export const Login = () => {
  const auth = useContext(AuthContext);
  const mensagens = usarMensagens();

  const [username, setUsername] = useState('');
  const [senha, setSenha] = useState('');
  const [showSenha, setShowSenha] = useState(false);

  const handleUsernameInput = (event: ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  }

  const handlePasswordInput = (event: ChangeEvent<HTMLInputElement>) => {
    setSenha(event.target.value);
  }

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleLogin();
    }
  };

  const handleClick = (e: React.MouseEvent) => {
    e.preventDefault();
    setShowSenha(!showSenha);
  };

  const handleLogin = async () => {
    if (username && senha) {
      const isLogged = await auth.login(username, senha);
      if (isLogged) {
        mensagens.exibirSucesso('Login bem-sucedido!')
      }
    }
  }
        
  return (
    <C.Container>
      <C.Body>
        <C.Titulo>
          Central de Controle
        </C.Titulo>
        <C.CampoUsername>
          <MdAccountCircle  style={C.IconeUsername} />
          <C.InputCampoUsername
            type="text"
            placeholder="Digite seu usuário"
            value={username}
            onChange={handleUsernameInput}
          />
        </C.CampoUsername>

        <C.CampoSenha>
          <MdLock style={C.IconeSenha} />
          <C.InputCampoSenha
            placeholder="Digite sua senha"
            type={showSenha ? 'text' : 'password'}
            value={senha}
            onChange={handlePasswordInput}
            onKeyDown={handleKeyDown}
          />
          <C.IconeOlho>
            {showSenha ? (
              <HiEye size={20} onClick={handleClick} />
            ) : (
              <HiEyeOff size={20} onClick={handleClick} />
            )}
          </C.IconeOlho>
        </C.CampoSenha>

        <C.BotaoEntrar onClick={handleLogin} type="submit">
          Entrar
        </C.BotaoEntrar>
      </C.Body>
    </C.Container>           
  )
}