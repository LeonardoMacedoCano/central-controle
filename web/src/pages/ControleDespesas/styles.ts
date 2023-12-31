import styled from 'styled-components';

export const Container = styled.div`
    background-color: ${props => props.theme.colors.tertiary};
    margin: 0;
    padding: 0;
`;

export const Header = styled.div`
    text-align: center;
`;

export const HeaderText = styled.h1`
    color: ${props => props.theme.colors.white};
    padding-top: 20px;
`;

export const Body = styled.div`
    margin: auto;
    max-width: 950px;
    margin-bottom: 50px;
`;