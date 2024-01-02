import styled from "styled-components";

export const Container = styled.div`
    background-color: #FFF;
    box-shadow: 0px 0px 5px #CCC;
    border-radius: 10px;
    padding: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
`;

export const AreaMes = styled.div`
    width: 25%;
    display: flex;
    align-items: center;
    justify-content: center;
`;

export const SetaMes = styled.div`
    width: 30px;
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 22px;
    cursor: pointer;
    color: ${props => props.theme.colors.white};
    background-color: ${props => props.theme.colors.tertiary};
    border-radius: 5px;
`;

export const TituloMes = styled.div`
    text-align: center;
    padding: 0 10px 0 10px;
`;

export const AreaTitulo = styled.h1`
    width: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: ${props => props.theme.colors.black};
    font-size: 28px;
`;

export const AreaInfo = styled.div`
    flex: 3;
    width: 25%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
`;

export const InfoDescricao = styled.div`
    text-align: center;
    font-weight: bold;
    color: #888;
    margin-bottom: 5px;
`;

export const InfoValor = styled.div`
    text-align: center;
    font-weight: bold;
    color: #000;
`;
