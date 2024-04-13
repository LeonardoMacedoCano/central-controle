import styled, { css } from 'styled-components';

const mensagemComum = css`
  padding: 10px;
  position: fixed;
  top: 10px;
  right: 10px;
  z-index: 1000;
  transition: opacity 5s ease-out;
  border-radius: 5px 0 5px 5px;
  color: ${props => props.theme.colors.white};
`;

export const MensagemErro = styled.div`
  ${mensagemComum}
  background-color: ${props => props.theme.colors.warning};
`;

export const MensagemSucesso = styled.div`
  ${mensagemComum}
  background-color: ${props => props.theme.colors.success};
`;
