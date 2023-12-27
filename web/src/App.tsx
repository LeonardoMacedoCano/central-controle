import React from 'react';
import { Route, Routes } from 'react-router-dom';
import { Home } from './pages/Home';
import { Private } from './pages/Private';
import ListaDespesas from './pages/ListaDespesas';
import MainHeader from './components/MainHeader';
import { ThemeProvider } from 'styled-components';

import dark from './styles/themes/dark';

const App: React.FC = () => {
  return (
    <ThemeProvider theme={dark}>
      <MainHeader />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/controledespesas" element={<ListaDespesas />} />
        <Route path="/private" element={<Private />} />
      </Routes>
    </ThemeProvider>
  )
}

export default App
