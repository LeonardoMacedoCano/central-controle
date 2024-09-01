import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AuthContext } from '../../contexts/auth/AuthContext';
import { UsuarioConfigContext } from '../../contexts/usuarioconfig/UsuarioConfigContext';
import { useMessage } from '../../contexts/message/ContextMessageProvider';
import DespesaService from '../../service/fluxocaixa/DespesaService';
import useConfirmModal from '../../hooks/useConfirmModal';
import Panel from '../../components/panel/Panel';
import { Table, Column } from '../../components/table/Table';
import DespesaForm from '../../components/form/despesa/DespesaForm';
import ParcelaForm from '../../components/form/despesa/ParcelaForm';
import Container from '../../components/container/Container';
import FloatingButton from '../../components/button/floatingbutton/FloatingButton';
import { FaCheck } from 'react-icons/fa';
import { Despesa, initialDespesaState } from '../../types/fluxocaixa/Despesa';
import { Parcela } from '../../types/fluxocaixa/Parcela';
import { formatDateToShortString, getCurrentDate } from '../../utils/DateUtils';
import { formatarValorParaReal, formatarDescricaoSituacaoParcela } from '../../utils/ValorUtils';

const DespesaPage: React.FC = () => {
  const { idStr } = useParams<{ idStr?: string }>();
  const [despesa, setDespesa] = useState<Despesa>(initialDespesaState);
  const [numeroParcelaSelecionada, setNumeroParcelaSelecionada] = useState<number | null>(null);
  const [showParcelaForm, setShowParcelaForm] = useState<boolean>(false);
  const { usuarioConfig } = useContext(UsuarioConfigContext);
  const { confirm, ConfirmModalComponent } = useConfirmModal();
  const auth = useContext(AuthContext);
  const message = useMessage();
  const despesaService = DespesaService();

  const id = typeof idStr === 'string' ? parseInt(idStr, 10) : 0;

  useEffect(() => {
    if (id > 0) {
      carregarDespesa(id);
    }
  }, [auth.usuario?.token, id]);

  const carregarDespesa = async (idDespesa: number) => {
    if (!auth.usuario?.token) return;

    try {
      const result = await despesaService.getDespesaByIdWithParcelas(auth.usuario?.token, idDespesa);
      if (result) setDespesa(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar a despesa.', error);
    }
  };

  const atualizarDespesa = (newDespesa: Partial<Despesa>) => {
    setDespesa(prevDespesa => ({ ...prevDespesa, ...newDespesa }));
  };

  const salvarDespesa = async () => {
    if (!auth.usuario?.token) return;

    try {
      let idDespesa = 0;
      if (id > 0) {
        await despesaService.editarDespesa(auth.usuario?.token, id, despesa);
        idDespesa = id;
      } else {
        const response = await despesaService.gerarDespesa(auth.usuario?.token, despesa);
        if (response?.idDespesa) idDespesa = response.idDespesa;
      }
      if (idDespesa > 0) carregarDespesa(idDespesa);
    } catch (error) {
      message.showErrorWithLog('Erro ao salvar a despesa.', error);
    }
  };

  const getDataVencimentoPadrao = (): Date => {
    const dataAtual = getCurrentDate();
    const anoAtual = dataAtual.getFullYear();
    const mesAtual = dataAtual.getMonth();
    const diaAtual = dataAtual.getDate();
    const diaPadraoVencimento = usuarioConfig.despesaDiaPadraoVencimento;
  
    let mes = diaAtual > diaPadraoVencimento ? mesAtual + 1 : mesAtual;
    const ano = mes > 11 ? anoAtual + 1 : anoAtual;
    mes = mes % 12;

    return new Date(ano, mes, diaPadraoVencimento);
  };

  const adicionarNovaParcela = () => {
    const novoNumeroParcela = despesa.parcelas.length > 0 ? despesa.parcelas[despesa.parcelas.length - 1].numero + 1 : 1;
    const novaParcela: Parcela = {
      id: 0,
      numero: novoNumeroParcela,
      dataVencimento: getDataVencimentoPadrao(),
      valor: 0,
      pago: false,
      formaPagamento: null
    };

    atualizarDespesa({ parcelas: [...despesa.parcelas, novaParcela] });
    setNumeroParcelaSelecionada(novoNumeroParcela);
    setShowParcelaForm(true);
  };

  const deletarParcela = () => {
    if (!numeroParcelaSelecionada || numeroParcelaSelecionada <= 0) return;

    try {
      const updatedParcelas = despesa.parcelas.filter(p => p.numero !== numeroParcelaSelecionada);
      atualizarDespesa({ parcelas: updatedParcelas });
      setNumeroParcelaSelecionada(null);
      message.showSuccess('Parcela excluída com sucesso.');
    } catch (error) {
      message.showErrorWithLog('Erro ao deletar a parcela.', error); 
    }
  };

  const alterarSituacaoParcela = () => {
    if (!numeroParcelaSelecionada || numeroParcelaSelecionada <= 0) return;

    const parcelaSelecionada = despesa.parcelas.find(p => p.numero === numeroParcelaSelecionada);
    if (parcelaSelecionada) {
      const updatedParcela: Parcela = {
        ...parcelaSelecionada,
        pago: !parcelaSelecionada.pago,
        formaPagamento: !parcelaSelecionada.pago && !parcelaSelecionada.formaPagamento ? usuarioConfig.despesaFormaPagamentoPadrao : parcelaSelecionada.formaPagamento
      };

      const updatedParcelas = despesa.parcelas.map(p => p.numero === numeroParcelaSelecionada ? updatedParcela : p);
      atualizarDespesa({ parcelas: updatedParcelas });
    }
  };

  const atualizarParcela = (parcelaAtualizada: Parcela) => {
    const updatedParcela = {
      ...parcelaAtualizada,
      formaPagamento: parcelaAtualizada.pago && !parcelaAtualizada.formaPagamento ? usuarioConfig.despesaFormaPagamentoPadrao : parcelaAtualizada.formaPagamento
    };
  
    const updatedParcelas = despesa.parcelas.map(p => p.numero === updatedParcela.numero ? updatedParcela : p);
    atualizarDespesa({ parcelas: updatedParcelas });
  };

  const exitParcela = () => {
    setShowParcelaForm(false);
    setNumeroParcelaSelecionada(null);
  };

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

  const handleDeletarParcela = async () => {
    const result = await confirm(`Exclusão da parcela n.º ${numeroParcelaSelecionada}`, `Tem certeza de que deseja excluir esta parcela? Esta ação não pode ser desfeita e a parcela será removida permanentemente.`);

    if (result) {
      deletarParcela();
    } else {
      message.showInfo('Ação cancelada!');
    }
  };

  return (
    <Container>
      {ConfirmModalComponent}
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={showParcelaForm ? 'Salvar Parcela' : 'Salvar Despesa'}
        mainAction={showParcelaForm ? exitParcela : salvarDespesa}
        disabled={showParcelaForm ? !isCamposObrigatoriosParcelaPreenchidos() : !isCamposObrigatoriosDespesaPreenchidos()}
      />
      {showParcelaForm ? (
        <Panel maxWidth='1000px' title='Parcela'>
          <ParcelaForm parcela={despesa.parcelas[(numeroParcelaSelecionada || despesa.parcelas.length) - 1]} onUpdate={atualizarParcela} />
        </Panel>
      ) : (
        <>
          <Panel maxWidth='1000px' title='Despesa'>
            <DespesaForm despesa={despesa} onUpdate={atualizarDespesa} />
          </Panel>
          <Panel maxWidth='1000px' title='Parcelas' footer={despesa.parcelas.length > 0 && <></>}>
            <Table
              values={despesa ? despesa.parcelas : []}
              messageEmpty="Nenhuma parcela encontrada."
              keyExtractor={(item) => item.id.toString()}
              onClickRow={handleClickRow}
              rowSelected={isRowSelected}
              columns={[
                <Column<Parcela> header="Número" value={(item) => item.numero} />,
                <Column<Parcela> header="Data Vencimento" value={(item) => formatDateToShortString(item.dataVencimento)} />,
                <Column<Parcela> header="Valor" value={(item) => formatarValorParaReal(item.valor)} />,
                <Column<Parcela> header="Situação" value={(item) => formatarDescricaoSituacaoParcela(item.pago)} />,
                <Column<Parcela> header="Forma Pagamento" value={(item) => item.formaPagamento ? item.formaPagamento?.descricao : ""} />
              ]}
            />
          </Panel>
        </>
      )}
    </Container>
  );
};

export default DespesaPage;
