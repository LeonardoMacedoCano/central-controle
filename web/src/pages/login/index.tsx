import { ChangeEvent, useContext, useState } from "react";
import { AuthContext } from "../../contexts/auth/AuthContext";
import { usarMensagens } from '../../contexts/mensagens';
import { MdAccountCircle, MdLock } from 'react-icons/md';
import { HiEye, HiEyeOff } from 'react-icons/hi';
import * as styles from './styles';

export const Login = () => {
  const auth = useContext(AuthContext);
  const mensagens = usarMensagens();

  const [username, setUsername] = useState('');
  const [senha, setSenha] = useState('');
  const [show, setShow] = useState(false);

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
    setShow(!show);
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
    <styles.Container>
      <styles.CaixaCentral>
        <styles.Titulo>
          Central de Controle
        </styles.Titulo>
        <styles.CampoUsername>
          <MdAccountCircle  style={styles.IconeUsername} />
          <styles.InputCampoUsername
            type="text"
            placeholder="Digite seu usuário"
            value={username}
            onChange={handleUsernameInput}
          />
        </styles.CampoUsername>

        <styles.CampoSenha>
          <MdLock style={styles.IconeSenha} />
          <styles.InputCampoSenha
            placeholder="Digite sua senha"
            type={show ? 'text' : 'password'}
            value={senha}
            onChange={handlePasswordInput}
            onKeyDown={handleKeyDown}
          />
          <styles.IconeOlho>
            {show ? (
              <HiEye size={20} onClick={handleClick} />
            ) : (
              <HiEyeOff size={20} onClick={handleClick} />
            )}
          </styles.IconeOlho>
        </styles.CampoSenha>

        <styles.BotaoEntrar onClick={handleLogin} type="submit">
          Entrar
        </styles.BotaoEntrar>
      </styles.CaixaCentral>
    </styles.Container>           
  )
}