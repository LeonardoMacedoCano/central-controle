import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from './GlobalStyles';
import { darkOnyxAmber } from './themes';
import { 
  AuthProvider,
  ContextMessageProvider,
  RequireAuth
} from './contexts';
import AppLayout from './menus/AppLayout';
import AppRoutes from './routes';
import { DynamicFavicon } from './components';
import { NotificationProvider } from './contexts/notificacao/NotificationContext';

const App: React.FC = () => {
  return (
    <ThemeProvider theme={darkOnyxAmber}>
      <ContextMessageProvider>
        <AuthProvider>
          <BrowserRouter>
            <GlobalStyles />
            <RequireAuth>
              <NotificationProvider>
                <AppLayout>
                  <AppRoutes />
                </AppLayout>
              </NotificationProvider>
            </RequireAuth>
            <DynamicFavicon />
          </BrowserRouter>
        </AuthProvider>
      </ContextMessageProvider>
    </ThemeProvider>
  );
}

export default App;
