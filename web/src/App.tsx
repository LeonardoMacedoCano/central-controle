import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import darkOnyxAmber from './styles/themes/darkOnyxAmber';
import GlobalStyles from './styles/GlobalStyles';
import ContextMessageProvider from './contexts/message/ContextMessageProvider';
import { AuthProvider } from './contexts/auth/AuthProvider';
import { UsuarioConfigProvider } from './contexts/usuarioconfig/UsuarioConfigProvider';
import { RequireAuth } from './contexts/auth/RequireAuth';
import AppLayout from './components/applayout/AppLayout';
import { Home } from './pages/home';
import DespesaResumoMensalPage from './pages/controledespesas/DespesaResumoMensalPage';
import DespesaPage from './pages/controledespesas/DespesaPage';
import ConfiguracaoPage from './pages/configuracao/ConfiguracaoPage';

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
                    <Route path="/" element={<Home />} />
                    <Route path="/controledespesas" element={<DespesaResumoMensalPage />} />
                    <Route path="/despesa/:idStr?" element={<DespesaPage />} />
                    <Route path="/configuracao" element={<ConfiguracaoPage />} />
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
