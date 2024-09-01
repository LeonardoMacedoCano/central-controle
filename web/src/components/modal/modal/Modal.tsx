import React from 'react';
import styled from 'styled-components';
import { getVariantColor } from '../../../utils/styledUtils';
import { FaTimes } from 'react-icons/fa';
import Button from '../../button/button/Button';
import { FaCheckCircle, FaExclamationCircle, FaExclamationTriangle } from 'react-icons/fa';

interface ModalProps {
  isOpen: boolean;
  title: string;
  content: React.ReactNode;
  onClose: () => void;
  variant?: 'success' | 'info' | 'warning';
  actions?: React.ReactNode;
  showCloseButton?: boolean;
  closeButtonSize?: string;
  modalWidth?: string;
  modalHeight?: string;
}

const getIcon = (variant: ModalProps['variant']) => {
  let icon;

  switch (variant) {
    case 'success':
      icon = <FaCheckCircle />;
      break;
    case 'warning':
      icon = <FaExclamationTriangle />;
      break;
    default:
      icon = <FaExclamationCircle />;;
      break;
  }

  return { icon };
};

const Modal: React.FC<ModalProps> = ({
  isOpen,
  title,
  content,
  onClose,
  variant = 'warning',
  actions,
  showCloseButton = true,
  closeButtonSize = '20px',
  modalWidth = '500px',
  modalHeight = 'auto'
}) => {
  const { icon: computedIcon} = getIcon(variant);

  if (!isOpen) {
    return null;
  }

  return (
    <ModalOverlay onClick={onClose}>
      <ModalContainer 
        onClick={(e) => e.stopPropagation()}
        width={modalWidth}
        height={modalHeight}
      >
        <ModalHeader variant={variant}>
          {computedIcon && <span style={{ display: 'flex', alignItems: 'center' }}>{computedIcon}</span>}
          {showCloseButton && (
            <Button 
              width={closeButtonSize} 
              height={closeButtonSize} 
              style={{
                backgroundColor: 'transparent',
                borderRadius: '50%',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
              }}
              icon={<FaTimes/>}
              hint='Fechar'
              onClick={onClose}
            />
          )}
        </ModalHeader>
        <ModalTitle variant={variant}>{title}</ModalTitle>
        <ModalContent>{content}</ModalContent>
        {actions && <ModalActions>{actions}</ModalActions>}
      </ModalContainer>
    </ModalOverlay>
  );
};

export default Modal;

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
