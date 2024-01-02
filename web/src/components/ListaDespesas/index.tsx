import React, { useState, useEffect, useContext } from 'react';
import * as C from './styles';
import { useApi } from '../../hooks/useApi';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { TableArea } from '../../components/TableArea';
import { InputArea } from '../../components/InputArea';
import { InfoArea } from '../../components/InfoArea';
import { Despesa } from '../../types/Despesa';
import { Categoria } from '../../types/Categoria';
import { FormFields } from '../../types/FormFields';
import { DespesaColunasConfig } from '../../config/Despesas/DespesaColunasConfig';
import { DespesaColunasFormat } from '../../config/Despesas/DespesaColunasFormat';
import { DespesaCampos } from '../../config/Despesas/DespesaCampos';
import { formatarDataParaString, getMesAnoAtual } from '../../utils/DateUtils';

const ListaDespesas: React.FC = () => {
  const [despesas, setDespesas] = useState<Despesa[]>([]);
  const [categoriaDespesas, setCategoriaDespesas] = useState<Categoria[]>([]);
  const [token, setToken] = useState<string | null>(null);
  const [carregando, setCarregando] = useState(true);
  const [idDespesaSelecionada, setIdDespesaSelecionada] = useState<number | null>(null);
  const [categoriaMap, setCategoriaMap] = useState<Record<number, string>>({});
  const [dataSelecionada, setDataSelecionada] = useState(() => getMesAnoAtual());
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
    const buscarDados = async () => {
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
        setCarregando(false);
      }
    };

    buscarDados();
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
      id: idDespesaSelecionada || 0,
      idCategoria: mapaInvertido[data.categoria] || 0,
      descricao: data.descricao,
      valor: data.valor,
      data: new Date(data.data),
    };

    return despesa;
  }; 

  const handleEditClick = (idDespesa: number | null) => {
    setIdDespesaSelecionada(idDespesa);
  
    const despesaSelecionada = despesas.find((despesa) => despesa.id === idDespesa);

    setFormFields((prevFields) => ({
      ...prevFields,
      data: formatarDataParaString(despesaSelecionada?.data || undefined),
      categoria: categoriaMap[despesaSelecionada?.idCategoria || 0] || '', 
      descricao: despesaSelecionada?.descricao || '',
      valor: despesaSelecionada?.valor || 0,
    }));
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
      setIdDespesaSelecionada(null);
    } catch (error) {
      console.error('Erro ao editar despesa');
    }
  };
  
  const handleDeleteDespesa = async () => {
    try {
      if (idDespesaSelecionada !== null && token !== null && typeof token === 'string') {
        await api.excluirDespesa(token, idDespesaSelecionada);
      }

      setIdDespesaSelecionada(null);
    } catch (error) {
      console.error('Erro ao excluir despesa');
    }
  };

  const handleMesChange = (newMonth: string) => {
    setDataSelecionada(newMonth);
  }
    
  return (
    <C.Container>
      <InfoArea
        dataSelecionada={dataSelecionada}
        onMesChange={handleMesChange}
        titulo={'Despesas'}
        infoDescricao={'Descricao'}
        infoValor={'Valor'}
      />

      <InputArea
        campos={DespesaCampos}
        onAdd={handleAddDespesa}
        onEdit={handleEditDespesa}
        onDelete={handleDeleteDespesa}
        itemSelecionado={idDespesaSelecionada}
        opcoesCategoria={categoriaDespesas}
        valoresIniciais={formFields}
      />

      {carregando ? (
        <p>Carregando...</p>
      ) : (
        <TableArea
          lista={despesas}
          colunasConfig={DespesaColunasConfig}
          colunasFormat={DespesaColunasFormat({ categoriaMap })}
          onEditClick={handleEditClick}
          itemIdSelecionado={idDespesaSelecionada}
        />
      )}
    </C.Container>
  );
};

export default ListaDespesas;
