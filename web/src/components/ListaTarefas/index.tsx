import React, { useState, useEffect, useContext } from 'react';
import * as C from './styles';
import useTarefaApi from '../../hooks/useTarefaApi';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { TableArea } from '../../components/TableArea';
import { InputArea } from '../../components/InputArea';
import { InfoArea } from '../../components/InfoArea';
import { Tarefa } from '../../types/Tarefa';
import { Categoria } from '../../types/Categoria';
import { FormFieldsTarefa, ValoresIniciaisTarefa } from '../../types/FormFields';
import { TarefaColunasConfig } from '../../config/Tarefas/TarefaColunasConfig';
import { TarefaColunasFormat } from '../../config/Tarefas/TarefaColunasFormat';
import { TarefaCampos } from '../../config/Tarefas/TarefaCampos';
import { formatarDataParaString, getMesAnoAtual } from '../../utils/DateUtils';

const ListaTarefas: React.FC = () => {
  const [tarefas, setTarefas] = useState<Tarefa[]>([]);
  const [categoriaTarefas, setCategoriaTarefas] = useState<Categoria[]>([]);
  const [token, setToken] = useState<string | null>(null);
  const [carregando, setCarregando] = useState(true);
  const [idTarefaSelecionada, setIdTarefaSelecionada] = useState<number | null>(null);
  const [categoriaMap, setCategoriaMap] = useState<Record<number, string>>({});
  const [dataSelecionada, setDataSelecionada] = useState(() => getMesAnoAtual());
  const [formFields, setFormFields] = useState<FormFieldsTarefa>(ValoresIniciaisTarefa);
  
  const auth = useContext(AuthContext);
  const api = useTarefaApi();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const buscarDados = async () => {
      if (carregando && token !== null && typeof token === 'string') {
        const [anoStr, mesStr] = dataSelecionada.split('-');
        const ano = parseInt(anoStr);
        const mes = parseInt(mesStr);

        const [tarefasResult, categoriasResult] = await Promise.all([
          api.listarTarefas(token, ano, mes),
          api.listarCategoriasTarefa(token),
        ]);

        if (tarefasResult && categoriasResult) {
          setTarefas(tarefasResult);
          setCategoriaTarefas(categoriasResult);
          setCarregando(false);
        }
      }
    };

    buscarDados();
  }, [token, carregando]);

  useEffect(() => {
    const categoriaMap = categoriaTarefas.reduce((map: Record<number, string>, categoria) => {
      map[categoria.id] = categoria.descricao;
      return map;
    }, {});
  
    setCategoriaMap(categoriaMap);
  }, [categoriaTarefas]);

  function inverterCategoriaMap(categoriaMap: Record<number, string>): Record<string, number> {
    const novoMapa: Record<string, number> = {};

    Object.keys(categoriaMap).forEach((key) => {
      const valor = categoriaMap[parseInt(key, 10)];
      novoMapa[valor] = parseInt(key, 10);
    });

    return novoMapa;
  }

  const convertToTarefa = (data: FormFieldsTarefa): Tarefa => {
    const mapaInvertido = inverterCategoriaMap(categoriaMap);

    const tarefa: Tarefa = {
      id: idTarefaSelecionada || 0,
      idCategoria: mapaInvertido[data.categoria] || 0,
      titulo: data.titulo,
      descricao: data.descricao,
      dataPrazo: new Date(data.dataPrazo),
      finalizado: data.finalizado,
    };

    return tarefa;
  }; 

  const getInfoValor = (): string => {
    const tarefasNaoFinalizadas = tarefas.filter((tarefa) => !tarefa.finalizado);
    const valorFormatado = tarefasNaoFinalizadas.length.toString().padStart(2, '0');
    return valorFormatado;
  };

  const handleEditClick = (idTarefa: number | null) => {
    setIdTarefaSelecionada(idTarefa);
  
    const tarefaSelecionada = tarefas.find((tarefa) => tarefa.id === idTarefa);

    setFormFields((prevFields) => ({
      ...prevFields,
      categoria: categoriaMap[tarefaSelecionada?.idCategoria || 0] || '', 
      titulo: tarefaSelecionada?.titulo || '',
      descricao: tarefaSelecionada?.descricao || '',
      dataPrazo: formatarDataParaString(tarefaSelecionada?.dataPrazo || undefined),
      finalizado: tarefaSelecionada?.finalizado?.valueOf() || false,
    }));
  }; 

  const handleAddTarefa = async (data: FormFieldsTarefa) => {
    const novaTarefa = convertToTarefa(data);
  
    if (token !== null && typeof token === 'string') {
      await api.addTarefa(token, novaTarefa);
      setCarregando(true);
    }
  };
  
  const handleEditTarefa = async (data: FormFieldsTarefa) => {
    const tarefa = convertToTarefa(data);
  
    if (token !== null && typeof token === 'string') {
      await api.editarTarefa(token, tarefa);
      setCarregando(true);
    }
    setIdTarefaSelecionada(null);
  };
  
  const handleDeleteTarefa = async () => {
    if (idTarefaSelecionada !== null && token !== null && typeof token === 'string') {
      await api.excluirTarefa(token, idTarefaSelecionada);
      setCarregando(true);
    }

    setIdTarefaSelecionada(null);
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
        titulo={'Tarefas'}
        infoDescricao={'Tarefas à finalizar'}
        infoValor={getInfoValor()}
      />

      <InputArea
        campos={TarefaCampos}
        onAdd={(data) => handleAddTarefa(data as FormFieldsTarefa)}
        onEdit={(data) => handleEditTarefa(data as FormFieldsTarefa)}
        onDelete={handleDeleteTarefa}
        itemSelecionado={idTarefaSelecionada}
        opcoesCategoria={categoriaTarefas}
        valoresIniciais={formFields}
      />

      {carregando ? (
        <p>Carregando...</p>
      ) : (
        <TableArea
          lista={tarefas}
          colunasConfig={TarefaColunasConfig}
          colunasFormat={TarefaColunasFormat({ categoriaMap })}
          onEditClick={handleEditClick}
          itemIdSelecionado={idTarefaSelecionada}
        />
      )}
    </C.Container>
  );
};

export default ListaTarefas;
