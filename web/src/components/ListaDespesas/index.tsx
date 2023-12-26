import React, { useState, useEffect, useContext } from 'react';
import { useApi } from '../../hooks/useApi';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { Despesa } from '../../types/Despesa';

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
    <div>
      <h2>Suas Despesas</h2>
      {loading ? (
        <p>Carregando...</p>
      ) : (
        <ul>
          {despesas.map((despesa: Despesa) => (
            <li key={despesa.id}>
              {despesa.descricao} - R$ {despesa.valor.toFixed(2)} - {despesa.idCategoria} - {new Date(despesa.data).toLocaleDateString()}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ListaDespesas;
