import { Route } from 'react-router-dom';
import LancamentoListPage from '../pages/fluxocaixa/Lancamento/LancamentoListPage';
import LancamentoPage from '../pages/fluxocaixa/Lancamento/LancamentoPage';

const FluxoCaixaRoutes = () => (
  <>
    <Route path="/lancamentos" element={<LancamentoListPage />} />
    <Route path="/lancamento/:id?" element={<LancamentoPage />} />
  </>
);

export default FluxoCaixaRoutes;
