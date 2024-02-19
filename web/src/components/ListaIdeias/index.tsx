import React, { useState, useEffect, useContext } from 'react';
import * as C from './styles';
import useIdeiaApi from '../../hooks/useIdeiaApi';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { TableArea } from '../../components/TableArea';
import { InputArea } from '../../components/InputArea';
import { InfoArea } from '../../components/InfoArea';
import { Ideia } from '../../types/Ideia';
import { Categoria } from '../../types/Categoria';
import { FormFieldsIdeia, ValoresIniciaisIdeia } from '../../types/FormFields';
import { IdeiaColunasConfig } from '../../config/Ideias/IdeiaColunasConfig';
import { IdeiaColunasFormat } from '../../config/Ideias/IdeiaColunasFormat';
import { IdeiaCampos } from '../../config/Ideias/IdeiaCampos';
import { formatarDataParaString, getMesAnoAtual } from '../../utils/DateUtils';

const ListaIdeias: React.FC = () => {
  const [ideias, setIdeias] = useState<Ideia[]>([]);
  const [categoriaIdeias, setCategoriaIdeias] = useState<Categoria[]>([]);
  const [token, setToken] = useState<string | null>(null);
  const [carregando, setCarregando] = useState(true);
  const [idIdeiaSelecionada, setIdIdeiaSelecionada] = useState<number | null>(null);
  const [categoriaMap, setCategoriaMap] = useState<Record<number, string>>({});
  const [dataSelecionada, setDataSelecionada] = useState(() => getMesAnoAtual());
  const [formFields, setFormFields] = useState<FormFieldsIdeia>(ValoresIniciaisIdeia);
  
  const auth = useContext(AuthContext);
  const api = useIdeiaApi();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const buscarDados = async () => {
      if (carregando && token !== null && typeof token === 'string') {
        const [anoStr, mesStr] = dataSelecionada.split('-');
        const ano = parseInt(anoStr);
        const mes = parseInt(mesStr);

        const [ideiasResult, categoriasResult] = await Promise.all([
          api.listarIdeias(token, ano, mes),
          api.listarCategoriasIdeia(token),
        ]);

        if (ideiasResult && categoriasResult) {
          setIdeias(ideiasResult);
          setCategoriaIdeias(categoriasResult);
          setCarregando(false);
        }
      }
    };

    buscarDados();
  }, [token, carregando]);

  useEffect(() => {
    const categoriaMap = categoriaIdeias.reduce((map: Record<number, string>, categoria) => {
      map[categoria.id] = categoria.descricao;
      return map;
    }, {});
  
    setCategoriaMap(categoriaMap);
  }, [categoriaIdeias]);

  function inverterCategoriaMap(categoriaMap: Record<number, string>): Record<string, number> {
    const novoMapa: Record<string, number> = {};

    Object.keys(categoriaMap).forEach((key) => {
      const valor = categoriaMap[parseInt(key, 10)];
      novoMapa[valor] = parseInt(key, 10);
    });

    return novoMapa;
  }

  const convertToIdeia = (data: FormFieldsIdeia): Ideia => {
    const mapaInvertido = inverterCategoriaMap(categoriaMap);

    const ideia: Ideia = {
      id: idIdeiaSelecionada || 0,
      idCategoria: mapaInvertido[data.categoria] || 0,
      titulo: data.titulo,
      descricao: data.descricao,
      dataPrazo: new Date(data.dataPrazo),
      finalizado: data.finalizado,
    };

    return ideia;
  }; 

  const getInfoValor = (): string => {
    const ideiasNaoFinalizadas = ideias.filter((ideia) => !ideia.finalizado);
    const valorFormatado = ideiasNaoFinalizadas.length.toString().padStart(2, '0');
    return valorFormatado;
  };

  const handleEditClick = (idIdeia: number | null) => {
    setIdIdeiaSelecionada(idIdeia);
  
    const ideiaSelecionada = ideias.find((ideia) => ideia.id === idIdeia);

    setFormFields((prevFields) => ({
      ...prevFields,
      categoria: categoriaMap[ideiaSelecionada?.idCategoria || 0] || '', 
      titulo: ideiaSelecionada?.titulo || '',
      descricao: ideiaSelecionada?.descricao || '',
      dataPrazo: formatarDataParaString(ideiaSelecionada?.dataPrazo || undefined),
      finalizado: ideiaSelecionada?.finalizado?.valueOf() || false,
    }));
  }; 

  const handleAddIdeia = async (data: FormFieldsIdeia) => {
    const novaIdeia = convertToIdeia(data);
  
    if (token !== null && typeof token === 'string') {
      await api.addIdeia(token, novaIdeia);
      setCarregando(true);
    }
  };
  
  const handleEditIdeia = async (data: FormFieldsIdeia) => {
    const ideia = convertToIdeia(data);
  
    if (token !== null && typeof token === 'string') {
      await api.editarIdeia(token, ideia);
      setCarregando(true);
    }
    setIdIdeiaSelecionada(null);
  };
  
  const handleDeleteIdeia = async () => {
    if (idIdeiaSelecionada !== null && token !== null && typeof token === 'string') {
      await api.excluirIdeia(token, idIdeiaSelecionada);
      setCarregando(true);
    }

    setIdIdeiaSelecionada(null);
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
        titulo={'Ideias'}
        infoDescricao={'Ideias à finalizar'}
        infoValor={getInfoValor()}
      />

      <InputArea
        campos={IdeiaCampos}
        onAdd={(data) => handleAddIdeia(data as FormFieldsIdeia)}
        onEdit={(data) => handleEditIdeia(data as FormFieldsIdeia)}
        onDelete={handleDeleteIdeia}
        itemSelecionado={idIdeiaSelecionada}
        opcoesCategoria={categoriaIdeias}
        valoresIniciais={formFields}
      />

      {carregando ? (
        <p>Carregando...</p>
      ) : (
        <TableArea
          lista={ideias}
          colunasConfig={IdeiaColunasConfig}
          colunasFormat={IdeiaColunasFormat({ categoriaMap })}
          onEditClick={handleEditClick}
          itemIdSelecionado={idIdeiaSelecionada}
        />
      )}
    </C.Container>
  );
};

export default ListaIdeias;
