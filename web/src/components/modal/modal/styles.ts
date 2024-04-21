import styled from 'styled-components';

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
  padding: 20px;
  background-color: ${({ theme }) => theme.colors.primary};
  border-radius: 8px;
  box-shadow: 0 0 5px 5px ${({ theme }) => theme.colors.secondary};
`;

export const ModalHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
`;

export const ModalTitle = styled.h2`
  margin: 0;
  color: ${({ theme }) => theme.colors.quaternary};
`;

export const ModalContent = styled.div`
  margin-bottom: 20px;
`;

export const ModalActions = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 10px;
`;
