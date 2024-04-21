import React, { ReactNode, createContext, useContext, useState, useEffect } from 'react';
import NotificationBox from '../../components/notificationbox/NotificationBox';

export interface ContextMessageProps {
  showError: (message: string) => void;
  showSuccess: (message: string) => void;
}

const ContextMessage = createContext<ContextMessageProps | undefined>(undefined);

interface MessageProviderProps {
  children: ReactNode;
}

const ContextMessageProvider: React.FC<MessageProviderProps> = ({ children }) => {
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  useEffect(() => {
    const errorClearTimeout = errorMessage ? setTimeout(() => setErrorMessage(null), 5000) : undefined;
    const successClearTimeout = successMessage ? setTimeout(() => setSuccessMessage(null), 5000) : undefined;

    return () => {
      errorClearTimeout && clearTimeout(errorClearTimeout);
      successClearTimeout && clearTimeout(successClearTimeout);
    };
  }, [errorMessage, successMessage]);

  const closeError = () => {
    setErrorMessage(null);
  };

  const closeSuccess = () => {
    setSuccessMessage(null);
  };

  const clearMessages = () => {
    closeError();
    closeSuccess();
  };

  const showError = (message: string) => {
    clearMessages();
    setErrorMessage(message);
  };

  const showSuccess = (message: string) => {
    clearMessages();
    setSuccessMessage(message);
  };

  return (
    <ContextMessage.Provider value={{ showError, showSuccess }}>
      {errorMessage && <NotificationBox type='error' message={errorMessage} onClose={closeError} />}
      {successMessage && <NotificationBox type='success' message={successMessage} onClose={closeSuccess} />}
      {children}
    </ContextMessage.Provider>
  );
};

export const useMessage = () => {
  const contexto = useContext(ContextMessage);

  if (!contexto) {
    throw new Error('useMessage must be used within a ContextMessageProvider');
  }

  return contexto;
};

export default ContextMessageProvider;
