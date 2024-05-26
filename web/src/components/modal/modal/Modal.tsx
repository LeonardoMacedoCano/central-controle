import React from 'react';
import * as C from './styles';
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
    <C.ModalOverlay onClick={onClose}>
      <C.ModalContainer 
        onClick={(e) => e.stopPropagation()}
        width={modalWidth}
        height={modalHeight}
      >
        <C.ModalHeader variant={variant}>
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
        </C.ModalHeader>
        <C.ModalTitle variant={variant}>{title}</C.ModalTitle>
        <C.ModalContent>{content}</C.ModalContent>
        {actions && <C.ModalActions>{actions}</C.ModalActions>}
      </C.ModalContainer>
    </C.ModalOverlay>
  );
};

export default Modal;
