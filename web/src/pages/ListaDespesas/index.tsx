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
  const [selectedItemId, setSelectedItemId] = useState<number | null>(null);
  const auth = useContext(AuthContext);
  const api = useApi();

  const customColumnNames = {
    id: { label: 'ID', width: 50 },
    idCategoria: { label: 'Categoria', width: 100 },
    descricao: { label: 'Descrição' },
    valor: { label: 'Valor', width: 150 },
    data: { label: 'Data', width: 100 },
  };

  const customFormatters = {
    valor: (value: string | number | Date) => Number(value).toFixed(2),
    data: (value: string | number | Date) => new Date(value).toLocaleDateString(),
  };

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

  const handleEditClick = (itemId: number | null) => {
    setSelectedItemId(itemId);
  };

  return (
    <C.Container>
      <C.Header>
        <C.HeaderText>Despesa</C.HeaderText>
      </C.Header>
      <C.Body>
        {loading ? (
          <p>Carregando...</p>
        ) : (
          <TableArea
            list={despesas}
            columnNames={customColumnNames}
            columnFormatters={customFormatters}
            onEditClick={handleEditClick}
            selectedItemId={selectedItemId}
          />
        )}
      </C.Body>
    </C.Container>
  );
};

export default ListaDespesas;
