import React, { useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../contexts/auth/AuthContext';
import DespesaService from '../../service/DespesaService';
import ParcelaService from '../../service/ParcelaService';
import { DespesaResumoMensal } from '../../types/DespesaResumoMensal';
import { getDataAtual, formataraMesAnoParaData, formatarDataParaAnoMes } from '../../utils/DateUtils';
import Table from '../../components/table/Table';
import Column from '../../components/table/Column';
import Panel from '../../components/panel/Panel';
import Container from '../../components/container/Container';
import FieldValue from '../../components/fieldvalue/FieldValue';
import { formatarValorParaReal } from '../../utils/ValorUtils';
import { PagedResponse } from '../../types/PagedResponse';
import SearchPagination from '../../components/pagination/SearchPagination';
import FlexBox from '../../components/flexbox/FlexBox';
import Button from '../../components/button/button/Button';

const DespesaResumoMensalPage: React.FC = () => {
  const [token, setToken] = useState<string | null>(null);
  const [dataSelecionada, setDataSelecionada] = useState(() => getDataAtual());
  const [idDespesaSelecionada, setIdDespesaSelecionada] = useState<number | null>(null);
  const [despesasPage, setDespesasPage] = useState<PagedResponse<DespesaResumoMensal>>();
  const [valorTotal, setValorTotal] = useState<number>(0); 
  const [indexPagina, setIndexPagina] = useState<number>(0); 
  const [registrosPorPagina, setRegistrosPorPagina] = useState<number>(3);  

  const auth = useContext(AuthContext);
  const navigate = useNavigate();
  const despesaService = DespesaService();
  const parcelaService = ParcelaService();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const carregarDespesaResumoMensal = async () => {
      if (!token) return;

      const [anoStr, mesStr] = formatarDataParaAnoMes(dataSelecionada).split('-');
      const ano = parseInt(anoStr);
      const mes = parseInt(mesStr);

      const resultDespesas = await despesaService.listarDespesaResumoMensal(token, indexPagina, registrosPorPagina, ano, mes);
      setDespesasPage(resultDespesas || undefined);

      const resultValorTotal = await parcelaService.getValorTotalParcelasMensal(token, ano, mes);
      setValorTotal(resultValorTotal?.valueOf() || 0);
    };

    carregarDespesaResumoMensal();
  }, [token, dataSelecionada, indexPagina, registrosPorPagina])

  const handleClickRow = (item: DespesaResumoMensal) => {
    setIdDespesaSelecionada(prevId => prevId === item.id ? null : item.id);
  };
  
  const handleRowSelected = (item: DespesaResumoMensal) => {
    return idDespesaSelecionada === item.id;
  };

  const handleUpdateVencimento = (value: any) => {
    if (typeof value === 'string') {
      setDataSelecionada(formataraMesAnoParaData(value)); 
    }
  };

  const carregarPagina = (indexPagina: number, registrosPorPagina: number) => {
    setIndexPagina(indexPagina);
    setRegistrosPorPagina(registrosPorPagina);
  };

  const handleAdd = () => {
    navigate('/despesa');
  };
  
  const handleEdit = () => {
    navigate(`/despesa/${idDespesaSelecionada}`);
  };
  
  const handleDelete = () => {
    console.log('Botão Deletar clicado');
  };

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
              onUpdate={handleUpdateVencimento} 
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
          rowSelected={handleRowSelected}
          customHeader={
            <>
              <Button 
                variant='table-add' 
                onClick={handleAdd} 
                disabled={idDespesaSelecionada !== null && idDespesaSelecionada > 0} 
              />
              <Button 
                variant='table-edit' 
                onClick={handleEdit} 
                disabled={!idDespesaSelecionada} 
              />
              <Button 
                variant='table-delete' 
                onClick={handleDelete} 
                disabled={!idDespesaSelecionada} 
              />
            </>
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
