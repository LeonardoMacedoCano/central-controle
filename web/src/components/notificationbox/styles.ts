import styled, { css } from 'styled-components';

const defaultMessage = css`
  padding: 10px 30px 10px 10px;
  position: fixed;
  top: 10px;
  right: 10px;
  z-index: 1000;
  transition: opacity 5s ease-out;
  border-radius: 0 0 5px 5px;
  background-color: ${({ theme }) => theme.colors.primary};
  box-shadow: 0px 0px 2px ${({ theme }) => theme.colors.gray};
`;

export const ErrorMessage = styled.div`
  ${defaultMessage}
  border-top: 3px solid ${props => props.theme.colors.warning};
  color: ${props => props.theme.colors.warning};
`;

export const SuccessMessage = styled.div`
  ${defaultMessage}
  border-top: 3px solid ${props => props.theme.colors.success};
  color: ${props => props.theme.colors.success};
`;

export const InfoMessage = styled.div`
  ${defaultMessage}
  border-top: 3px solid ${props => props.theme.colors.info};
  color: ${props => props.theme.colors.info};
`;

export const ButtonContainer = styled.div`
  position: absolute;
  top: 5px;
  right: 5px;
`;