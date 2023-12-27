import { ChangeEvent, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/Auth/AuthContext";
import { MdAccountCircle, MdLock } from 'react-icons/md';
import { HiEye, HiEyeOff } from 'react-icons/hi';
import * as styles from './styles';

export const Login = () => {
    const auth = useContext(AuthContext);
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [senha, setSenha] = useState('');
    const [show, setShow] = useState(false);
    const [isHovered, setIsHovered] = useState(false);

    const handleUsernameInput = (event: ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value);
    }

    const handlePasswordInput = (event: ChangeEvent<HTMLInputElement>) => {
        setSenha(event.target.value);
    }

    const handleClick = (e: React.MouseEvent) => {
        e.preventDefault();
        setShow(!show);
     };

    const handleLogin = async () => {
        if (username && senha) {
            const isLogged = await auth.login(username, senha);
            if (isLogged) {
                navigate('/');
            } else {
                alert("Não deu certo.");
            }
        }
    }
    
    return (
        <>
            <div style={styles.Container}>
                <div style={styles.CaixaCentral}>
                    <h1 style={styles.Titulo}>Central de Controle</h1>
                    <div style={styles.CampoUsername}>
                        <MdAccountCircle  style={styles.IconeUsername} />
                        <input
                            type="text"
                            placeholder="Digite seu usuário"
                            value={username}
                            onChange={handleUsernameInput}
                            style={styles.InputCampoUsername}
                        />
                    </div>

                    <div style={styles.CampoSenha}>
                        <MdLock style={styles.IconeSenha} />
                        <input
                            placeholder="Digite sua senha"
                            type={show ? 'text' : 'password'}
                            value={senha}
                            onChange={handlePasswordInput}
                            style={styles.InputCampoSenha}
                        />
                        <div style={styles.IconeOlho}>
                            {show ? (
                                <HiEye size={20} onClick={handleClick} />
                            ) : (
                                <HiEyeOff size={20} onClick={handleClick} />
                            )}
                        </div>
                    </div>

                    <button onClick={handleLogin} 
                        type="submit"
                        style={isHovered ? { ...styles.BotaoEntrar, ...styles.BotaoEntrarHover } : styles.BotaoEntrar}
                        onMouseEnter={() => setIsHovered(true)}
                        onMouseLeave={() => setIsHovered(false)}
                    >
                        Entrar
                    </button>
                </div>
            </div>      
        </>      
    )
}