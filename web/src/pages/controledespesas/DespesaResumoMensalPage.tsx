import React, { useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../contexts/auth/AuthContext';
import { useMessage } from '../../contexts/message/ContextMessageProvider';
import DespesaService from '../../service/DespesaService';
import ParcelaService from '../../service/ParcelaService';
import useConfirmModal from '../../hooks/useConfirmModal';
import { Table, Column, TableToolbar } from '../../components/table/Table';
import Panel from '../../components/panel/Panel';
import Container from '../../components/container/Container';
import FieldValue from '../../components/fieldvalue/FieldValue';
import SearchPagination from '../../components/pagination/SearchPagination';
import FlexBox from '../../components/flexbox/FlexBox';
import { DespesaResumoMensal } from '../../types/DespesaResumoMensal';
import { PagedResponse } from '../../types/PagedResponse';
import { getDataAtual, formataraMesAnoParaData, formatarDataParaAnoMes } from '../../utils/DateUtils';
import { formatarValorParaReal } from '../../utils/ValorUtils';
import { UsuarioConfigContext } from '../../contexts/usuarioconfig/UsuarioConfigContext';

const DespesaResumoMensalPage: React.FC = () => {
  const [dataSelecionada, setDataSelecionada] = useState(() => getDataAtual());
  const [idDespesaSelecionada, setIdDespesaSelecionada] = useState<number | null>(null);
  const [despesasPage, setDespesasPage] = useState<PagedResponse<DespesaResumoMensal> | undefined>(undefined);
  const [valorTotal, setValorTotal] = useState<number>(0); 
  const [indexPagina, setIndexPagina] = useState<number>(0); 
  const [registrosPorPagina, setRegistrosPorPagina] = useState<number | null>(null); 
  const { usuarioConfig } = useContext(UsuarioConfigContext);
  const { confirm, ConfirmModalComponent } = useConfirmModal();
  
  const auth = useContext(AuthContext);
  const message = useMessage();
  const navigate = useNavigate();
  const despesaService = DespesaService();
  const parcelaService = ParcelaService();

  useEffect(() => {
    const carregarConfiguracao = async () => {
      if (!auth.usuario?.token || !usuarioConfig) return; 

      try {
        setRegistrosPorPagina(usuarioConfig.despesaNumeroMaxItemPagina);
      } catch (error) {
        message.showErrorWithLog('Erro ao carregar as configurações do usuário.', error);
      }
    };
  
    carregarConfiguracao();
  }, [auth.usuario?.token, usuarioConfig]);  

  useEffect(() => {
    carregarDespesaResumoMensal();
  }, [auth.usuario?.token, dataSelecionada, indexPagina, registrosPorPagina]);

  const carregarDespesaResumoMensal = async () => {
    if (!auth.usuario?.token || !registrosPorPagina) return;

    const [anoStr, mesStr] = formatarDataParaAnoMes(dataSelecionada).split('-');
    const ano = parseInt(anoStr);
    const mes = parseInt(mesStr);

    const [resultDespesas, resultValorTotal] = await Promise.all([
      despesaService.listarDespesaResumoMensal(auth.usuario?.token, indexPagina, registrosPorPagina, ano, mes),
      parcelaService.getValorTotalParcelasMensal(auth.usuario?.token, ano, mes)
    ]);

    setDespesasPage(resultDespesas);
    setValorTotal(resultValorTotal?.valueOf() || 0);
    setIdDespesaSelecionada(null);
  };

  const isRowSelected = (item: DespesaResumoMensal) => idDespesaSelecionada === item.id;

  const carregarPagina = (indexPagina: number, registrosPorPagina: number) => {
    setIndexPagina(indexPagina);
    setRegistrosPorPagina(registrosPorPagina);
  };

  const deletarDespesa = async () => {
    if (auth.usuario?.token && idDespesaSelecionada) {
      await despesaService.excluirDespesa(auth.usuario?.token, idDespesaSelecionada);
      carregarDespesaResumoMensal();
    }
  };

  const atualizarDataVencimento = (value: string) => {
    setDataSelecionada(formataraMesAnoParaData(value)); 
  };

  const handleClickRow = (item: DespesaResumoMensal) => setIdDespesaSelecionada(prevId => prevId === item.id ? null : item.id);

  const handleAddDespesa = () => navigate('/despesa');

  const handleEditDespesa = () => navigate(`/despesa/${idDespesaSelecionada}`);

  const handleDeletarParcela = async () => {
    const result = await confirm("Exclusão de despesa", `Tem certeza de que deseja excluir esta despesa? Esta ação não pode ser desfeita e a despesa será removida permanentemente.`);

    if (result) {
      deletarDespesa();
    } else {
      message.showInfo('Ação cancelada!');
    }
  };

  return (
    <Container>
      {ConfirmModalComponent}
      <Panel maxWidth='1000px' title='Resumo Mensal'>
        <FlexBox>
          <FlexBox.Item 
            borderRight  
            width='160px' 
          >
            <FieldValue 
              description='Data' 
              type='month' 
              value={formatarDataParaAnoMes(dataSelecionada)} 
              editable={true}
              width='160px'
              inputWidth='150px'
              onUpdate={atualizarDataVencimento} 
            />
          </FlexBox.Item>
          <FlexBox.Item 
            borderLeft 
            width='160px'
            alignRight 
          >
            <FieldValue 
              description='Valor Total' 
              type='string' 
              value={formatarValorParaReal(valorTotal)}
              width='160px'
              inputWidth='150px' 
              variant={valorTotal > usuarioConfig.despesaValorMetaMensal ? 'warning': 'success'}
            />
          </FlexBox.Item>
        </FlexBox>
      </Panel>
      <Panel 
        maxWidth='1000px' 
        title='Despesas'
        footer={
          despesasPage && despesasPage?.totalElements > 0 && 
          <SearchPagination
            page={despesasPage}
            carregarPagina={carregarPagina}
          />
        }>
        <Table
          values={despesasPage?.content || []}
          messageEmpty="Nenhuma despesa encontrada."
          keyExtractor={(item) => item.id.toString()}
          onClickRow={handleClickRow}
          rowSelected={isRowSelected}
          customHeader={
            <TableToolbar
              handleAdd={handleAddDespesa}
              handleEdit={handleEditDespesa}
              handleDelete={handleDeletarParcela}
              isItemSelected={!!idDespesaSelecionada}
            />
          }
          columns={[
            <Column<DespesaResumoMensal> 
              header="Categoria" 
              value={(item) => item.categoria.descricao} 
            />,
            <Column<DespesaResumoMensal> 
              header="Descrição" 
              value={(item) => item.descricao} 
            />,
            <Column<DespesaResumoMensal> 
              header="Valor" 
              value={(item) => formatarValorParaReal(item.valorTotal)} 
            />,
            <Column<DespesaResumoMensal> 
              header="Situação" 
              value={(item) => item.situacao} 
            />
          ]}
        />
      </Panel>
    </Container>
  );
};
  
export default DespesaResumoMensalPage;
