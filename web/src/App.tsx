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
import { AppLayout } from './components/';
import { Home } from './pages/home/Home';
import ConfiguracaoPage from './pages/configuracao/ConfiguracaoPage';
import LancamentoListPage from './pages/fluxocaixa/Lancamento/LancamentoListPage';
import LancamentoPage from './pages/fluxocaixa/Lancamento/LancamentoPage';

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
                    <Route path="/lancamentos" element={<LancamentoListPage />} />
                    <Route path="/lancamento/:id?" element={<LancamentoPage />} />
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
