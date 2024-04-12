import React, { useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../contexts/auth/AuthContext';
import DespesaService from '../../service/DespesaService';
import ParcelaService from '../../service/ParcelaService';
import Table from '../../components/table/Table';
import Column from '../../components/table/Column';
import Panel from '../../components/panel/Panel';
import Container from '../../components/container/Container';
import FieldValue from '../../components/fieldvalue/FieldValue';
import SearchPagination from '../../components/pagination/SearchPagination';
import FlexBox from '../../components/flexbox/FlexBox';
import TableToolbar from '../../components/table/TableToolbar';
import { DespesaResumoMensal } from '../../types/DespesaResumoMensal';
import { PagedResponse } from '../../types/PagedResponse';
import { getDataAtual, formataraMesAnoParaData, formatarDataParaAnoMes } from '../../utils/DateUtils';
import { formatarValorParaReal } from '../../utils/ValorUtils';

const DespesaResumoMensalPage: React.FC = () => {
  const [token, setToken] = useState<string | null>(null);
  const [dataSelecionada, setDataSelecionada] = useState(() => getDataAtual());
  const [idDespesaSelecionada, setIdDespesaSelecionada] = useState<number | null>(null);
  const [despesasPage, setDespesasPage] = useState<PagedResponse<DespesaResumoMensal>>();
  const [valorTotal, setValorTotal] = useState<number>(0); 
  const [indexPagina, setIndexPagina] = useState<number>(0); 
  const [registrosPorPagina, setRegistrosPorPagina] = useState<number>(10);  

  const auth = useContext(AuthContext);
  const navigate = useNavigate();
  const despesaService = DespesaService();
  const parcelaService = ParcelaService();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    carregarDespesaResumoMensal();
  }, [token, dataSelecionada, indexPagina, registrosPorPagina]);

  const carregarDespesaResumoMensal = async () => {
    if (!token) return;

    const [anoStr, mesStr] = formatarDataParaAnoMes(dataSelecionada).split('-');
    const ano = parseInt(anoStr);
    const mes = parseInt(mesStr);

    const [resultDespesas, resultValorTotal] = await Promise.all([
        despesaService.listarDespesaResumoMensal(token, indexPagina, registrosPorPagina, ano, mes),
        parcelaService.getValorTotalParcelasMensal(token, ano, mes)
    ]);

    setDespesasPage(resultDespesas || undefined);
    setValorTotal(resultValorTotal?.valueOf() || 0);
    setIdDespesaSelecionada(null);
};

  const isRowSelected = (item: DespesaResumoMensal) => idDespesaSelecionada === item.id;

  const carregarPagina = (indexPagina: number, registrosPorPagina: number) => {
    setIndexPagina(indexPagina);
    setRegistrosPorPagina(registrosPorPagina);
  };

  const deletarDespesa = async () => {
    if (token && idDespesaSelecionada) {
      await despesaService.excluirDespesa(token, idDespesaSelecionada);
      carregarDespesaResumoMensal();
    }
  };

  const atualizarDataVencimento = (value: any) => {
    if (typeof value === 'string') {
      setDataSelecionada(formataraMesAnoParaData(value)); 
    }
  };

  const handleClickRow = (item: DespesaResumoMensal) => setIdDespesaSelecionada(prevId => prevId === item.id ? null : item.id);

  const handleAddDespesa = () => navigate('/despesa');

  const handleEditDespesa = () => navigate(`/despesa/${idDespesaSelecionada}`);

  return (
    <Container>
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
              width='150px'
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
              width='150px'
              inputWidth='150px' 
            />
          </FlexBox.Item>
        </FlexBox>
      </Panel>
      <Panel 
        maxWidth='1000px' 
        title='Despesas'
        footer={
          despesasPage && despesasPage?.totalElements > 0 && 
          (<SearchPagination
            page={despesasPage}
            carregarPagina={carregarPagina}
          />
        )}>
        <Table
          values={despesasPage ? despesasPage.content : []}
          messageEmpty="Nenhuma despesa encontrada."
          keyExtractor={(item) => item.id.toString()}
          onClickRow={handleClickRow}
          rowSelected={isRowSelected}
          customHeader={
            <TableToolbar
              handleAdd={handleAddDespesa}
              handleEdit={handleEditDespesa}
              handleDelete={deletarDespesa}
              isItemSelected={!!idDespesaSelecionada}
            />
          }
        >
          <Column<DespesaResumoMensal> 
            fieldName="categoria.descricao" 
            header="Categoria" 
            value={(item) => item.categoria.descricao} />
          <Column<DespesaResumoMensal> 
            fieldName="descricao" 
            header="Descrição" 
            value={(item) => item.descricao} />
          <Column<DespesaResumoMensal> 
            fieldName="valorTotal" 
            header="Valor" 
            value={(item) => formatarValorParaReal(item.valorTotal)} />
          <Column<DespesaResumoMensal> 
            fieldName="situacao" 
            header="Situação" 
            value={(item) => item.situacao} />
        </Table>
      </Panel>
    </Container>
  );
};
  
export default DespesaResumoMensalPage;
