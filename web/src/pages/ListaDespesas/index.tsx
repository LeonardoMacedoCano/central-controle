import React, { useState, useEffect, useContext } from 'react';
import * as C from './styles';
import { Despesa } from '../../types/Despesa';
import { useApi } from '../../hooks/useApi';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { TableArea } from '../../components/TableArea';

const ListaDespesas: React.FC = () => {
  const [despesas, setDespesas] = useState<Despesa[]>([]);
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const auth = useContext(AuthContext);
  const api = useApi();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (token !== null && typeof token === 'string') {
          const result: Despesa[] = await api.listarDespesas(token);
          setDespesas(result);
        }
      } catch (error: any) {
        console.error('Erro ao carregar as despesas:', error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [api, token]);

  return (
    <C.Container>
      <C.Header>
        <C.HeaderText>Despesa</C.HeaderText>
      </C.Header>
      <C.Body>

        {loading ? (
          <p>Carregando...</p>
        ) : (
          <TableArea list={despesas} />
        )}  

      </C.Body>
    </C.Container>
  );
};

export default ListaDespesas;
