import styled from 'styled-components';
import { getVariantColor } from '../../../utils/styledUtils';

interface ModalContainerProps {
  width: string;
  height: string;
}

export const ModalOverlay = styled.div`
  z-index: 1000;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const ModalContainer = styled.div<ModalContainerProps>`
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  background-color: ${({ theme }) => theme.colors.primary};
  border-radius: 8px;
  box-shadow: 0 0 5px 5px ${({ theme }) => theme.colors.secondary};
`;

interface ModalHeaderProps {
  variant: 'success' | 'info' | 'warning';
}
export const ModalHeader = styled.div<ModalHeaderProps>`
  color: ${({ theme }) => theme.colors.white};
  background-color: ${({ variant, theme }) => getVariantColor(variant, theme)};
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top-right-radius: 8px;
  border-top-left-radius: 8px; 
  margin-bottom: 10px;
  padding: 10px;
`;

export const ModalTitle = styled.div<ModalHeaderProps>`
  margin: 0;
  color: ${({ variant, theme }) => getVariantColor(variant, theme)};
  padding: 10px 20px;
`;

export const ModalContent = styled.div`
  padding: 10px 20px;
`;

export const ModalActions = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 20px;
`;
