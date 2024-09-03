import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
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
import { routes } from './routes';

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
                  <Routes>
                    {routes.map((route, index) => (
                      <Route key={index} path={route.path} element={route.element} />
                    ))}
                  </Routes>
                </AppLayout>
              </RequireAuth>
            </BrowserRouter>
          </UsuarioConfigProvider>
        </AuthProvider>
      </ContextMessageProvider>
    </ThemeProvider>
  );
}

export default App;
