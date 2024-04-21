import React from 'react';
import Modal from '../modal/Modal';
import Button from '../../button/button/Button';
import { FaCheck, FaTimes } from "react-icons/fa";

interface ConfirmModalProps {
  isOpen: boolean;
  title: string;
  content: React.ReactNode;
  onClose: () => void;
  onConfirm: () => void;
}

const ConfirmModal: React.FC<ConfirmModalProps> = ({ isOpen, title, content, onClose, onConfirm }) => {
  const confirmButton = (
    <Button 
      variant="success"
      width="50px"
      height="25px"
      style={{
        borderRadius: '5px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
      icon={<FaCheck/>}
      hint="Sim"
      onClick={() => { onConfirm(); onClose(); }}
    />
  );

  const cancelButton = (
    <Button 
      variant="warning"
      width="50px"
      height="25px"
      style={{
        borderRadius: '5px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
      icon={<FaTimes/>}
      hint="Não"
      onClick={onClose}
    />
  );

  return (
    <Modal
      isOpen={isOpen}
      title={title}
      content={content}
      modalWidth='350px'
      onClose={onClose}
      showCloseButton={false}
      actions={
        <>
          {confirmButton}
          {cancelButton}
        </>
      }
    />
  );
};

export default ConfirmModal;
