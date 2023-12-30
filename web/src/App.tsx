import React from 'react';
import { Route, Routes } from 'react-router-dom';
import { Home } from './pages/Home';
import ControleDespesas from './pages/ControleDespesas';
import MainHeader from './components/MainHeader';
import { ThemeProvider } from 'styled-components';

import dark from './styles/themes/dark';

const App: React.FC = () => {
  return (
    <ThemeProvider theme={dark}>
      <MainHeader />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/controledespesas" element={<ControleDespesas />} />
      </Routes>
    </ThemeProvider>
  )
}

export default App
