import styled from 'styled-components';

export const Container = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100vh;
    width: 100vw;
    color: white;
    background-color: ${props => props.theme.colors.tertiary};
`;

export const CaixaCentral = styled.div`
  background-color: ${props => props.theme.colors.primary};
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  width: 500px;
  height: 500px;
`;

export const Titulo = styled.h1`
    color: ${props => props.theme.colors.white};
    font-family: 'Roboto Slab';
    font-size: 40px;
    margin-bottom: 80px;
`;

export const CampoUsername = styled.div`
    display: flex;
    align-items: center;
    color: gray;
    background-color: ${props => props.theme.colors.secondary};
    border-radius: 5px;
    padding: 3px;
    width: 98%;
    height: 50px;
`;

export const IconeUsername = {
    marginLeft: '10px',
    fontSize: '25px',
};

export const InputCampoUsername = styled.input`
    background: transparent;
    width: 100%;
    outline-width: 0;
    color: ${props => props.theme.colors.white};
    border: none;
    font-size: 17px;
    margin-left: 10px;
    margin-right: 10px;
`;

export const CampoSenha = styled.div`
    display: flex;
    align-items: center;
    color: gray;
    background-color: ${props => props.theme.colors.secondary};
    padding: 3px;
    margin: 5px;
    margin-top: 10px;
    width: 98%;
    height: 50px;
    border-radius: 5px;
`;

export const IconeSenha = {
    marginLeft: '10px',
    fontSize: '25px',
};

export const InputCampoSenha = styled.input`
    background: transparent;
    width: 100%;
    outline-width: 0;
    color: ${props => props.theme.colors.white};
    border: none;
    font-size: 17px;
    margin-left: 12px;
    margin-right: 10px;
`;

export const IconeOlho = styled.div`
    align-items: center;
    justify-content: center;
    font-size: 30;
    cursor: pointer;
    margin-right: 10px;
`;

export const BotaoEntrar = styled.button`
    width: 98%;
    background-color: ${props => props.theme.colors.secondary};
    color: ${props => props.theme.colors.white};
    font-weight: 800;
    height: 50px;
    border-radius: 5px;
    font-size: 18px;
    margin-top: 30px;
    border: none;
    outline-width: 0;
`;