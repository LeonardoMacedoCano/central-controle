import React from 'react';
import { Route, Routes } from 'react-router-dom';
import { Home } from './pages/home/index.tsx';
import ConsultaDespesaResumoMensal from './pages/controledespesas/ConsultaDespesaResumoMensal.tsx';
import ControleTarefas from './pages/controletarefas/index.tsx';
import ControleIdeias from './pages/controleideias/index.tsx';
import MainHeader from './components/mainheader/index.tsx';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from './styles/GlobalStyles';
import { BrowserRouter } from 'react-router-dom'
import { AuthProvider } from './contexts/auth/AuthProvider.tsx'
import { RequireAuth } from './contexts/auth/RequireAuth.tsx';
import { ProvedorMensagens } from './contexts/mensagens/index.tsx';

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
                <MainHeader />
                <Routes>
                  <Route path="/" element={<Home />} />
                  <Route path="/controledespesas" element={<ConsultaDespesaResumoMensal />} />
                  <Route path="/controletarefas" element={<ControleTarefas />} />
                  <Route path="/controleideias" element={<ControleIdeias />} />
                </Routes>
              </>
            </RequireAuth>}
          </BrowserRouter>
        </AuthProvider>
      </ProvedorMensagens>
    </ThemeProvider>
  )
}

export default App
