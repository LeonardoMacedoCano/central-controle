import React from 'react';
import * as C from './styles';
import Button from '../button/button/Button';
import { FaTimes } from 'react-icons/fa';

type MessageType = 'error' | 'success' | 'info';

interface NotificationBoxProps {
  type: MessageType;
  message: string;
  onClose: () => void;
}

const NotificationBox: React.FC<NotificationBoxProps> = ({ type, message, onClose }) => {
  const ComponenteMensagem = {
    error: C.ErrorMessage,
    success: C.SuccessMessage,
    info: C.InfoMessage
  }[type];

  return (
    <ComponenteMensagem>
      {message}
      <C.ButtonContainer>
        <Button 
          variant={type === 'error' ? 'warning' : type === 'success' ? 'success' : 'info'}
          width={'15px'} 
          height={'15px'} 
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
      </C.ButtonContainer>
    </ComponenteMensagem>
  );
};

export default NotificationBox;
