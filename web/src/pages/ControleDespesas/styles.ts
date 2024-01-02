import styled from 'styled-components';

export const Container = styled.div`
    background-color: ${props => props.theme.colors.tertiary};
    margin: 0;
    padding: 0;
`;

export const Body = styled.div`
    margin: auto;
    max-width: 950px;
    padding-top: 50px;
    padding: 25px 0 10px 0;
`;