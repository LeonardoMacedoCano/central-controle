import React, { useState, useEffect, useContext } from 'react';
import * as C from './styles';
import { Despesa } from '../../types/Despesa';
import { CategoriaDespesa } from '../../types/CategoriaDespesa';
import { useApi } from '../../hooks/useApi';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { TableArea } from '../../components/TableArea';

const ListaDespesas: React.FC = () => {
  const [despesas, setDespesas] = useState<Despesa[]>([]);
  const [categoriaDespesas, setCategoriaDespesas] = useState<CategoriaDespesa[]>([]);
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [selectedItemId, setSelectedItemId] = useState<number | null>(null);
  const [categoriaMap, setCategoriaMap] = useState<Record<number, string>>({});

  const auth = useContext(AuthContext);
  const api = useApi();

  const customColumnNames = {
    id: { label: 'ID', width: 50 },
    idCategoria: { label: 'Categoria', width: 150 },
    descricao: { label: 'Descrição' },
    valor: { label: 'Valor', width: 100 },
    data: { label: 'Data', width: 100 },
  };

  type ColumnFormatters<T> = Partial<Record<keyof T, (value: T[keyof T]) => React.ReactNode>>;

  const customFormatters: ColumnFormatters<Despesa> = {
    id: (value: string | number | Date) => Number(value),
    descricao: (value: string | number | Date) => String(value),
    valor: (value: string | number | Date) => Number(value).toFixed(2),
    data: (value: string | number | Date) => new Date(value).toLocaleDateString(),
    idCategoria: (value: string | number | Date) => {
      const categoryId = typeof value === 'number' ? value : Number(value);
      return categoriaMap[categoryId] || 'Categoria Desconhecida';
    },
  };

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (token !== null && typeof token === 'string') {
          const [despesasResult, categoriasResult] = await Promise.all([
            api.listarDespesas(token),
            api.listarTodasCategoriasDespesas(token),
          ]);

          setDespesas(despesasResult);
          setCategoriaDespesas(categoriasResult);

          const categoriaMap = categoriaDespesas.reduce((map: Record<number, string>, categoria) => {
            map[categoria.id] = categoria.descricao;
            return map;
          }, {});

          setCategoriaMap(categoriaMap);
        }
      } catch (error: any) {
        console.error('Erro ao carregar as despesas ou categorias de despesas:', error.message);
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
        <C.HeaderText>Despesa {selectedItemId}</C.HeaderText>
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
