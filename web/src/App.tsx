import './App.css'
import { Route, Routes } from 'react-router-dom';
import { Home } from './pages/Home';
import { Private } from './pages/Private';
import ListaDespesas from './pages/ListaDespesas';

import Header from './components/Header';

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/ControleDespesas" element={<ListaDespesas />} />
        <Route path="/private" element={<Private />} />
      </Routes>
    </div>
  )
}

export default App
