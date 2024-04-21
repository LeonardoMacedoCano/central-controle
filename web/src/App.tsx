import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import darkOnyxAmber from './styles/themes/darkOnyxAmber';
import GlobalStyles from './styles/GlobalStyles';
import ContextMessageProvider from './contexts/message/ContextMessageProvider';
import { AuthProvider } from './contexts/auth/AuthProvider';
import { RequireAuth } from './contexts/auth/RequireAuth';
import AppHeader from './components/menu/AppHeader';
import { Home } from './pages/home';
import DespesaResumoMensalPage from './pages/controledespesas/DespesaResumoMensalPage';
import ControleTarefas from './pages/controletarefas';
import ControleIdeias from './pages/controleideias';
import DespesaPage from './pages/controledespesas/DespesaPage';

const App: React.FC = () => {
  return (
    <ThemeProvider theme={darkOnyxAmber}>
      <ContextMessageProvider>
        <AuthProvider>
          <BrowserRouter>
            <GlobalStyles />
            <RequireAuth>
              <>
                <AppHeader />
                <Routes>
                  <Route path="/" element={<Home />} />
                  <Route path="/controledespesas" element={<DespesaResumoMensalPage />} />
                  <Route path="/despesa/:idStr?" element={<DespesaPage />} />
                  <Route path="/controletarefas" element={<ControleTarefas />} />
                  <Route path="/controleideias" element={<ControleIdeias />} />
                </Routes>
              </>
            </RequireAuth>
          </BrowserRouter>
        </AuthProvider>
      </ContextMessageProvider>
    </ThemeProvider>
  );
}

export default App;
