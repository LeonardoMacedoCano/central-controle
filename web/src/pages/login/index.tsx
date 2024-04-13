import * as C from './styles';
import { ThemeContext } from "styled-components";
import { useContext, useState } from "react";
import { MdAccountCircle, MdLock } from 'react-icons/md';
import { AuthContext } from "../../contexts/auth/AuthContext";
import { usarMensagens } from '../../contexts/mensagens';
import Container from "../../components/container/Container";
import FlexBox from "../../components/flexbox/FlexBox";
import FieldValue from "../../components/fieldvalue/FieldValue";
import Button from "../../components/button/button/Button";

export const Login = () => {
  const auth = useContext(AuthContext);
  const theme = useContext(ThemeContext);
  const mensagens = usarMensagens();

  const [username, setUsername] = useState('');
  const [senha, setSenha] = useState('');

  const updateUsername = (value: any) => {
    setUsername(value);
  }

  const updatePassword = (value: any) => {
    setSenha(value);
  }

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleLogin();
    }
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
    <Container
      margin="0"
      style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100vh',
        width: '100vw',
      }}
    >
      <C.Body>
        <C.Titulo>
          Central de Controle
        </C.Titulo>
        <FlexBox
          style={C.Input(theme)}
        >
          <FlexBox.Item>
            <FieldValue
              type='string'
              value={username}
              icon={<MdAccountCircle style={C.Icon(theme)} />}
              editable={true}
              onUpdate={updateUsername}
              inline={true}
              padding="0"
              placeholder="Digite seu usuário"
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox
          style={C.Input(theme)}
        >
          <FlexBox.Item>
            <FieldValue
              type='string'
              value={senha}
              icon={<MdLock style={C.Icon(theme)} />}
              editable={true}
              onUpdate={updatePassword}
              inline={true}
              padding="0"
              placeholder="Digite sua senha"
              onKeyDown={handleKeyDown}
            />
          </FlexBox.Item>
        </FlexBox>
        <Button 
          variant="login" 
          description="Entrar"
          onClick={handleLogin}
        />
      </C.Body>
    </Container>           
  )
}
