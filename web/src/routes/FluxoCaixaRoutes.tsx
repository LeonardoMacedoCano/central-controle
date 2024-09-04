import { RouteObject } from 'react-router-dom';
import LancamentoListPage from '../pages/fluxocaixa/Lancamento/LancamentoListPage';
import LancamentoPage from '../pages/fluxocaixa/Lancamento/LancamentoPage';
import LancamentoFormPage from '../pages/fluxocaixa/Lancamento/LancamentoFormPage';

const FluxoCaixaRoutes: RouteObject[] = [
  { path: "/lancamentos", element: <LancamentoListPage /> },
  { path: "/lancamento/novo", element: <LancamentoFormPage /> },
  { path: "/lancamento/editar/:id", element: <LancamentoFormPage /> },
  { path: "/lancamento/:id", element: <LancamentoPage /> },
];

export default FluxoCaixaRoutes;
