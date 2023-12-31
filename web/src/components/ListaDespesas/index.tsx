import React, { useState, useEffect, useContext } from 'react';
import * as C from './styles';
import { useApi } from '../../hooks/useApi';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { TableArea } from '../../components/TableArea';
import { InputArea } from '../../components/InputArea';
import { Despesa } from '../../types/Despesa';
import { Categoria } from '../../types/Categoria';
import { FormFields } from '../../types/FormFields';
import { DespesaColumnNames } from '../../config/Despesas/DespesaColumnNames';
import { DespesaColumnFormatters } from '../../config/Despesas/DespesaColumnFormatters';
import { DespesaInputFields } from '../../config/Despesas/DespesaInputFields';
import { formatarDataParaString } from '../../utils/DateUtils';

const ListaDespesas: React.FC = () => {
  const [despesas, setDespesas] = useState<Despesa[]>([]);
  const [categoriaDespesas, setCategoriaDespesas] = useState<Categoria[]>([]);
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [selectedItemId, setSelectedItemId] = useState<number | null>(null);
  const [categoriaMap, setCategoriaMap] = useState<Record<number, string>>({});
  const [formFields, setFormFields] = useState<FormFields>({
    data: '',
    categoria: '',
    descricao: '',
    valor: 0,
  });

  const auth = useContext(AuthContext);
  const api = useApi();

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
  }, [api, token, categoriaDespesas]);


  function inverterCategoriaMap(categoriaMap: Record<number, string>): Record<string, number> {
    const novoMapa: Record<string, number> = {};

    Object.keys(categoriaMap).forEach((key) => {
      const valor = categoriaMap[parseInt(key, 10)];
      novoMapa[valor] = parseInt(key, 10);
    });

    return novoMapa;
  }

  const convertToDespesa = (data: FormFields): Despesa => {
    const mapaInvertido = inverterCategoriaMap(categoriaMap);

    const despesa: Despesa = {
      id: selectedItemId || 0,
      idCategoria: mapaInvertido[data.categoria] || 0,
      descricao: data.descricao,
      valor: data.valor,
      data: new Date(data.data),
    };

    return despesa;
  }; 

  const handleEditClick = (itemId: number | null) => {
    setSelectedItemId(itemId);
  
    if (itemId !== null) {
      const despesaSelecionada = despesas.find((despesa) => despesa.id === itemId);

      setFormFields((prevFields) => ({
        ...prevFields,
        data: formatarDataParaString(despesaSelecionada?.data || undefined),
        categoria: categoriaMap[despesaSelecionada?.idCategoria || 0] || '', 
        descricao: despesaSelecionada?.descricao || '',
        valor: despesaSelecionada?.valor || 0,
      }));

    }
  }; 

  const handleAddDespesa = async (data: FormFields) => {
    try {
      const novaDespesa = convertToDespesa(data);
    
      if (token !== null && typeof token === 'string') {
        await api.addDespesa(token, novaDespesa);
      }
    } catch (error) {
      console.error('Erro ao adicionar despesa');
    }
  };
  
  const handleEditDespesa = async (data: FormFields) => {
    try {
      const despesa = convertToDespesa(data);
    
      if (token !== null && typeof token === 'string') {
        await api.editarDespesa(token, despesa);
      }
      setSelectedItemId(null);
    } catch (error) {
      console.error('Erro ao editar despesa');
    }
  };
  
  const handleDeleteDespesa = async () => {
    try {
      if (selectedItemId !== null && token !== null && typeof token === 'string') {
        await api.excluirDespesa(token, selectedItemId);
      }

      setSelectedItemId(null);
    } catch (error) {
      console.error('Erro ao excluir despesa');
    }
  };
    

  return (
    <C.Container>
      <InputArea
        inputFields={DespesaInputFields}
        onAdd={handleAddDespesa}
        onEdit={handleEditDespesa}
        onDelete={handleDeleteDespesa}
        selectedItem={selectedItemId}
        categoriaOptions={categoriaDespesas}
        initialValues={formFields}
      />

      {loading ? (
        <p>Carregando...</p>
      ) : (
        <TableArea
          list={despesas}
          columnNames={DespesaColumnNames}
          columnFormatters={DespesaColumnFormatters({ categoriaMap })}
          onEditClick={handleEditClick}
          selectedItemId={selectedItemId}
        />
      )}
    </C.Container>
  );
};

export default ListaDespesas;
