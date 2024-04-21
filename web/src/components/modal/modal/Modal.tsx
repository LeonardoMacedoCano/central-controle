import React from 'react';
import * as C from './styles';
import { FaTimes } from 'react-icons/fa';
import Button from '../../button/button/Button';

interface ModalProps {
  isOpen: boolean;
  title: string;
  content: React.ReactNode;
  onClose: () => void;
  actions?: React.ReactNode;
  showCloseButton?: boolean;
  closeButtonSize?: string;
  modalWidth?: string;
  modalHeight?: string;
}

const Modal: React.FC<ModalProps> = ({
  isOpen,
  title,
  content,
  onClose,
  actions,
  showCloseButton = true,
  closeButtonSize = '20px',
  modalWidth = '500px',
  modalHeight = 'auto'
}) => {
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
        <C.ModalHeader>
          <C.ModalTitle>{title}</C.ModalTitle>
          {showCloseButton && (
            <Button 
              variant="warning"
              width={closeButtonSize} 
              height={closeButtonSize} 
              style={{
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
        <C.ModalContent>{content}</C.ModalContent>
        {actions && <C.ModalActions>{actions}</C.ModalActions>}
      </C.ModalContainer>
    </C.ModalOverlay>
  );
};

export default Modal;
