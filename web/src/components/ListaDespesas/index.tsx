import React, { useState, useEffect, useContext } from 'react';
import * as C from './styles';
import useDespesaApi from '../../hooks/useDespesaApi';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { TableArea } from '../../components/TableArea';
import { InputArea } from '../../components/InputArea';
import { InfoArea } from '../../components/InfoArea';
import { Despesa } from '../../types/Despesa';
import { Categoria } from '../../types/Categoria';
import { FormFieldsDespesa, ValoresIniciaisDespesa } from '../../types/FormFields';
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
  const somaDespesas = despesas.reduce((total, despesa) => total + despesa.valor, 0);
  const [formFields, setFormFields] = useState<FormFieldsDespesa>(ValoresIniciaisDespesa);

  const auth = useContext(AuthContext);
  const api = useDespesaApi();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const buscarDados = async () => {
      if (carregando && token !== null && typeof token === 'string') {
        const [anoStr, mesStr] = dataSelecionada.split('-');
        const ano = parseInt(anoStr);
        const mes = parseInt(mesStr);

        const [despesasResult, categoriasResult] = await Promise.all([
          api.listarDespesas(token, ano, mes),
          api.listarCategoriasDespesa(token),
        ]);

        if (despesasResult && categoriasResult) {
          setDespesas(despesasResult);
          setCategoriaDespesas(categoriasResult);
          setCarregando(false);
        }
      }
    };
  
    buscarDados();
  }, [token, carregando]);
  
  useEffect(() => {
    const categoriaMap = categoriaDespesas.reduce((map: Record<number, string>, categoria) => {
      map[categoria.id] = categoria.descricao;
      return map;
    }, {});
  
    setCategoriaMap(categoriaMap);
  }, [categoriaDespesas]);

  function inverterCategoriaMap(categoriaMap: Record<number, string>): Record<string, number> {
    const novoMapa: Record<string, number> = {};

    Object.keys(categoriaMap).forEach((key) => {
      const valor = categoriaMap[parseInt(key, 10)];
      novoMapa[valor] = parseInt(key, 10);
    });

    return novoMapa;
  }

  const convertToDespesa = (data: FormFieldsDespesa): Despesa => {
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

  const handleAddDespesa = async (data: FormFieldsDespesa) => {
    const novaDespesa = convertToDespesa(data);
  
    if (token !== null && typeof token === 'string') {
      await api.addDespesa(token, novaDespesa);
      setCarregando(true);
    }
  };
  
  const handleEditDespesa = async (data: FormFieldsDespesa) => {
    const despesa = convertToDespesa(data);
  
    if (token !== null && typeof token === 'string') {
      await api.editarDespesa(token, despesa);
      setCarregando(true);
    }
    setIdDespesaSelecionada(null);
  };
  
  const handleDeleteDespesa = async () => {
    if (idDespesaSelecionada !== null && token !== null && typeof token === 'string') {
      await api.excluirDespesa(token, idDespesaSelecionada);
      setCarregando(true);
    }
    setIdDespesaSelecionada(null);
  };

  const handleDataChange = (data: string) => {
    setDataSelecionada(data);
    setCarregando(true);
  }
    
  return (
    <C.Container>
      <InfoArea
        dataSelecionada={dataSelecionada}
        onDataChange={handleDataChange}
        dataDescricao={'Data'}
        titulo={'Despesas'}
        infoDescricao={'Total Despesas'}
        infoValor={`${somaDespesas.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}`}
      />

      <InputArea
        campos={DespesaCampos}
        onAdd={(data) => handleAddDespesa(data as FormFieldsDespesa)}
        onEdit={(data) => handleEditDespesa(data as FormFieldsDespesa)}
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
