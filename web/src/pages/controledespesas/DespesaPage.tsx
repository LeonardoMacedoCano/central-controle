import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AuthContext } from '../../contexts/auth/AuthContext';
import DespesaService from '../../service/DespesaService';
import Panel from '../../components/panel/Panel';
import { Table, Column, TableToolbar } from '../../components/table/Table';
import DespesaForm from '../../components/form/despesa/DespesaForm';
import ParcelaForm from '../../components/form/despesa/ParcelaForm';
import FloatingButton from '../../components/button/Floatingbutton/FloatingButton';
import Container from '../../components/container/Container';
import { FaCheck } from 'react-icons/fa';
import { Despesa } from '../../types/Despesa';
import { Parcela } from '../../types/Parcela';
import { Categoria } from '../../types/Categoria';
import { formatarDataParaString, getDataAtual } from '../../utils/DateUtils';
import { formatarValorParaReal, formatarDescricaoSituacaoParcela } from '../../utils/ValorUtils';

const DespesaPage: React.FC = () => {
  const { idStr } = useParams<{ idStr?: string }>();
  const [token, setToken] = useState<string | null>(null);
  const [despesa, setDespesa] = useState<Despesa>({
    id: 0,
    categoria: {
      id: 0,
      descricao: ''
    },
    dataLancamento: getDataAtual(),
    descricao: '',
    valorTotal: 0,
    situacao: '',
    parcelas: []
  });
  const [categorias, setCategorias] = useState<Categoria[]>([]);
  const [numeroParcelaSelecionada, setNumeroParcelaSelecionada] = useState<number | null>(null);
  const [showParcelaForm, setShowParcelaForm] = useState<boolean>(false);
  
  const auth = useContext(AuthContext);
  const despesaService = DespesaService();

  const id = typeof idStr === 'string' ? parseInt(idStr, 10) : 0;

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    if (id > 0 ) carregarDespesa(id);
    carregarCategoriasDespesa();
  }, [token, id]);

  const carregarDespesa = async (idDespesa: number) => {
    if (!token) return;

    try {
      const result = await despesaService.getDespesaByIdWithParcelas(token, idDespesa);
      if (result) setDespesa(result);
    } catch (error) {
      console.error("Erro ao carregar a despesa:", error);
    }
  };

  const carregarCategoriasDespesa = async () => {
    if (!token) return;

    try {
      const result = await despesaService.getTodasCategoriasDespesa(token);
      setCategorias(result || []);
    } catch (error) {
      console.error("Erro ao carregar as categorias de despesa:", error);
    }
  };

  const atualizarDespesa = (newDespesa: Partial<Despesa>) => {
    setDespesa(prevDespesa => ({ ...prevDespesa, ...newDespesa }));
  };

  const salvarDespesa = async () => {
    if (!token) return;

    try {
      let idDespesa = 0;
      if (id > 0) {
        await despesaService.editarDespesa(token, id, despesa);
        idDespesa = id;
      } else {
        const response = await despesaService.gerarDespesa(token, despesa);
        if (response?.idDespesa) idDespesa = response?.idDespesa;
      }
      if (idDespesa > 0) carregarDespesa(idDespesa);
    } catch (error) {
      console.error("Erro ao salvar a despesa:", error);
    }
  }

  const adicionarNovaParcela = () => {
    const novoNumeroParcela = despesa.parcelas.length > 0 ? despesa.parcelas[despesa.parcelas.length - 1].numero + 1 : 1;
    const novaParcela: Parcela = {
      id: 0,
      numero: novoNumeroParcela,
      dataVencimento: getDataAtual(),
      valor: 0,
      pago: false
    };

    atualizarDespesa({ parcelas: [...despesa.parcelas, novaParcela] });
    setNumeroParcelaSelecionada(novoNumeroParcela);
    setShowParcelaForm(true);
  };

  const deletarParcela = () => {
    if (!numeroParcelaSelecionada || numeroParcelaSelecionada <= 0) return;

    const updatedParcelas = despesa.parcelas.filter(p => p.numero !== numeroParcelaSelecionada);
    atualizarDespesa({ parcelas: updatedParcelas });
    setNumeroParcelaSelecionada(null);
  };

  const alterarSituacaoParcela = () => {
    if (!numeroParcelaSelecionada || numeroParcelaSelecionada <= 0) return;

    const parcelaSelecionada = despesa.parcelas.find(p => p.numero === numeroParcelaSelecionada);
    if (parcelaSelecionada) {
      const updatedParcela: Parcela = {
        ...parcelaSelecionada,
        pago: !parcelaSelecionada.pago
      };

      const updatedParcelas = despesa.parcelas.map(p => p.numero === numeroParcelaSelecionada ? updatedParcela : p);
      atualizarDespesa({ parcelas: updatedParcelas });
    }
  };

  const atualizarParcela = (parcelaAtualizada: Parcela) => {
    const updatedParcelas = despesa.parcelas.map(p => p.numero === parcelaAtualizada.numero ? parcelaAtualizada : p);
    atualizarDespesa({ parcelas: updatedParcelas });
  };

  const exitParcela = () => {
    setShowParcelaForm(false);
    setNumeroParcelaSelecionada(null);
  }

  const isCamposObrigatoriosDespesaPreenchidos = (): boolean => {
    return (
      despesa.categoria.id > 0 &&
      despesa.descricao.trim() !== '' &&
      despesa.parcelas.length > 0
    );
  };

  const isCamposObrigatoriosParcelaPreenchidos = (): boolean => {
    const parcelaSelecionada = despesa.parcelas.find(parcela => parcela.numero === numeroParcelaSelecionada);
  
    return (
      !!parcelaSelecionada &&
      parcelaSelecionada.numero > 0 &&
      parcelaSelecionada.dataVencimento !== null &&
      parcelaSelecionada.valor > 0 &&
      !isNaN(parcelaSelecionada.valor)
    );
  };
  
  const isRowSelected = (item: Parcela) => numeroParcelaSelecionada === item.numero;

  const handleClickRow = (item: Parcela) => setNumeroParcelaSelecionada(prevId => prevId === item.numero ? null : item.numero);

  return (
    <Container>
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={showParcelaForm ? 'Salvar Parcela' : 'Salvar Despesa'}
        mainAction={showParcelaForm ? exitParcela : salvarDespesa}
        disabled={showParcelaForm ? !isCamposObrigatoriosParcelaPreenchidos() : !isCamposObrigatoriosDespesaPreenchidos() }
      />
      {showParcelaForm ? (
        <Panel
          maxWidth='1000px' 
          title='Parcela'
        >
          <ParcelaForm
            parcela={despesa.parcelas[despesa.parcelas.length - 1]}
            onUpdate={atualizarParcela}
          />
        </Panel>
      ) : (
        <>
          <Panel
            maxWidth='1000px' 
            title='Despesa'
          >
            <DespesaForm
              despesa={despesa}
              categorias={categorias}
              onUpdate={atualizarDespesa}
            />
          </Panel>
          <Panel
            maxWidth='1000px' 
            title='Parcelas'
            footer={despesa.parcelas.length > 0 && <></>}
          >
            <Table
              values={despesa ? despesa.parcelas : []}
              messageEmpty="Nenhuma parcela encontrada."
              keyExtractor={(item) => item.id.toString()}
              onClickRow={handleClickRow}
              rowSelected={isRowSelected}
              customHeader={
                <TableToolbar 
                  handleAdd={adicionarNovaParcela}
                  handleEdit={() => setShowParcelaForm(true)}
                  handleDelete={deletarParcela}
                  handleMoney={alterarSituacaoParcela}
                  isItemSelected={!!numeroParcelaSelecionada}
                />
              }
              columns={[
                <Column<Parcela> 
                  header="Número" 
                  value={(item) => item.numero} 
                />,
                <Column<Parcela> 
                  header="Data Vencimento" 
                  value={(item) => formatarDataParaString(item.dataVencimento)} 
                />,
                <Column<Parcela> 
                  header="Valor" 
                  value={(item) => formatarValorParaReal(item.valor)} 
                />,  
                <Column<Parcela> 
                  header="Situação" 
                  value={(item) => formatarDescricaoSituacaoParcela(item.pago)} 
                />
              ]}
            />
          </Panel>
        </>
      )}
    </Container>
  )
}

export default DespesaPage;
