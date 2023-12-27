import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import { BrowserRouter } from 'react-router-dom'
import { AuthProvider } from './contexts/Auth/AuthProvider.tsx'
import { RequireAuth } from './contexts/Auth/RequireAuth';
import GlobalStyles from './styles/GlobalStyles';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AuthProvider>
      <BrowserRouter>
        <GlobalStyles />
        {<RequireAuth><App /></RequireAuth>}
      </BrowserRouter>
    </AuthProvider>
  </React.StrictMode>,
)
