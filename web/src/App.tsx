import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from './GlobalStyles';
import { darkOnyxAmber } from './themes';
import { 
  AuthProvider,
  ContextMessageProvider,
  UsuarioConfigProvider,
  RequireAuth
} from './contexts';
import AppLayout from './menus/AppLayout';
import AppRoutes from './routes';
import { DynamicFavicon } from './components';

const App: React.FC = () => {
  return (
    <ThemeProvider theme={darkOnyxAmber}>
      <ContextMessageProvider>
        <AuthProvider>
          <UsuarioConfigProvider>
            <BrowserRouter>
              <GlobalStyles />
              <RequireAuth>
                <AppLayout>
                  <AppRoutes />
                </AppLayout>
              </RequireAuth>
              <DynamicFavicon />
            </BrowserRouter>
          </UsuarioConfigProvider>
        </AuthProvider>
      </ContextMessageProvider>
    </ThemeProvider>
  );
}

export default App;
