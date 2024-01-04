import styled from "styled-components";

export const Container = styled.div`
    background-color: ${props => props.theme.colors.white};
    box-shadow: 0px 0px 5px ${props => props.theme.colors.gray};
    border-radius: 10px;
    padding: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
`;

export const AreaData = styled.div`
    width: 25%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
`;

export const DataDescricao = styled.div`
    text-align: center;
    font-weight: bold;
    color: ${props => props.theme.colors.gray};
    margin-bottom: 5px;
`;

export const DataValor = styled.div`
    text-align: center;
    font-weight: bold;
    color: ${props => props.theme.colors.black};
    width: 100%;
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
`;

export const DataSeta = styled.div`
    width: 30px;
    height: 30px;
    font-size: 30px;
    cursor: pointer;
    color: ${props => props.theme.colors.tertiary};
    background-color: ${props => props.theme.colors.white};
    border-radius: 5px;
`;

export const TituloMes = styled.div`
    text-align: center;
    padding: 0 10px 0 10px;
`;

export const AreaTitulo = styled.h1`
    width: 50%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: ${props => props.theme.colors.tertiary};
    font-size: 28px;
`;

export const AreaInfo = styled.div`
    width: 25%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
`;

export const InfoDescricao = styled.div`
    text-align: center;
    font-weight: bold;
    color: ${props => props.theme.colors.gray};
    margin-bottom: 5px;
`;

export const InfoValor = styled.div`
    text-align: center;
    font-weight: bold;
    color: ${props => props.theme.colors.black};
    width: 100%;
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
`;
