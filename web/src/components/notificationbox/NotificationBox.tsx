import React from 'react';
import * as C from './styles';
import Button from '../button/button/Button';
import { FaTimes } from 'react-icons/fa';

interface NotificationBoxProps {
  type: 'error' | 'success';
  message: string;
  onClose: () => void;
}

const NotificationBox: React.FC<NotificationBoxProps> = ({ type, message, onClose }) => {
  const ComponenteMensagem = type === 'error' ? C.ErrorMessage : C.SuccessMessage;

  return (
    <ComponenteMensagem>
      {message}
      <C.ButtonContainer>
        <Button 
          variant={type === 'error' ? 'warning' : 'success'}
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
