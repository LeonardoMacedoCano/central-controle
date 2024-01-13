import React from 'react';
import { Route, Routes } from 'react-router-dom';
import { Home } from './pages/Home';
import ControleDespesas from './pages/ControleDespesas';
import ControleTarefas from './pages/ControleTarefas';
import MainHeader from './components/MainHeader';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from './styles/GlobalStyles';
import { BrowserRouter } from 'react-router-dom'
import { AuthProvider } from './contexts/Auth/AuthProvider.tsx'
import { RequireAuth } from './contexts/Auth/RequireAuth';

import dark from './styles/themes/dark';
import light from './styles/themes/light';

const App: React.FC = () => {
  return (
    <AuthProvider>
      <BrowserRouter>
        <ThemeProvider theme={dark}>
          <GlobalStyles />
          {<RequireAuth>
            <>
              <MainHeader />
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/controledespesas" element={<ControleDespesas />} />
                <Route path="/controletarefas" element={<ControleTarefas />} />
              </Routes>
            </>
          </RequireAuth>}
        </ThemeProvider>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
