import React from 'react';
import { 
  Button,
  Modal
} from '../';

interface ConfirmModalProps {
  isOpen: boolean;
  title: string;
  content: React.ReactNode;
  onClose: () => void;
  onConfirm: () => void;
  variant?: 'success' | 'info' | 'warning';
}

const ConfirmModal: React.FC<ConfirmModalProps> = ({ 
  isOpen, 
  title, 
  content, 
  onClose, 
  onConfirm, 
  variant = `warning` 
}) => {
  const confirmButton = (
    <Button 
      variant={variant}
      width="100px"
      height="30px"
      style={{
        borderRadius: '5px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
      description="ACEITAR"
      onClick={() => { onConfirm(); onClose(); }}
    />
  );

  const cancelButton = (
    <Button 
      width="100px"
      height="30px"
      style={{
        borderRadius: '5px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
      description="CANCELAR"
      onClick={onClose}
    />
  );

  return (
    <Modal
      isOpen={isOpen}
      variant={variant}
      title={title}
      content={content}
      modalWidth='400px'
      maxWidth='85%'
      onClose={onClose}
      showCloseButton={false}
      actions={
        <>
          {cancelButton}
          {confirmButton}
        </>
      }
    />
  );
};

export default ConfirmModal;
