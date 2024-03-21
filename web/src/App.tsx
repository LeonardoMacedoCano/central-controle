import React from 'react';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { ProvedorMensagens } from './contexts/mensagens/index.tsx';
import { AuthProvider } from './contexts/auth/AuthProvider.tsx';
import GlobalStyles from './styles/GlobalStyles';
import { RequireAuth } from './contexts/auth/RequireAuth.tsx';
import AppHeader from './components/menu/AppHeader.tsx';
import { Home } from './pages/home/index.tsx';
import DespesaResumoMensalPage from './pages/controledespesas/DespesaResumoMensalPage.tsx';
import ControleTarefas from './pages/controletarefas/index.tsx';
import ControleIdeias from './pages/controleideias/index.tsx';
import dark from './styles/themes/dark';
//import light from './styles/themes/light';

const App: React.FC = () => {
  return (
    <ThemeProvider theme={dark}>
      <ProvedorMensagens>
        <AuthProvider>
          <BrowserRouter>
            <GlobalStyles />   
            {<RequireAuth>
              <>
                <AppHeader/>
                <Routes>
                  <Route path="/" element={<Home />} />
                  <Route path="/controledespesas" element={<DespesaResumoMensalPage />} />
                  <Route path="/controletarefas" element={<ControleTarefas />} />
                  <Route path="/controleideias" element={<ControleIdeias />} />
                </Routes>
              </>
            </RequireAuth>}
          </BrowserRouter>
        </AuthProvider>
      </ProvedorMensagens>
    </ThemeProvider>
  );
}

export default App
