import React from 'react';
import { Route, Routes } from 'react-router-dom';
import { Home } from './pages/Home';
import ConsultaDespesaResumoMensal from './pages/ControleDespesas/ConsultaDespesaResumoMensal.tsx';
import ControleTarefas from './pages/ControleTarefas';
import ControleIdeias from './pages/ControleIdeias';
import MainHeader from './components/MainHeader';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from './styles/GlobalStyles';
import { BrowserRouter } from 'react-router-dom'
import { AuthProvider } from './contexts/Auth/AuthProvider.tsx'
import { RequireAuth } from './contexts/Auth/RequireAuth';
import { ProvedorMensagens } from './contexts/Mensagens/index.tsx';

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
