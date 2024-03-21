import React, { useState, useContext, useEffect } from 'react';
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

const ConsultaDespesaResumoMensal: React.FC = () => {
  const [token, setToken] = useState<string | null>(null);
  const [dataSelecionada, setDataSelecionada] = useState(() => getDataAtual());
  const [idDespesaSelecionada, setIdDespesaSelecionada] = useState<number | null>(null);
  const [despesasResumoMensal, setDespesasResumoMensal] = useState<DespesaResumoMensal[]>([]);
  const [valorTotal, setValorTotal] = useState<number>(0); 

  const auth = useContext(AuthContext);
  const despesaService = DespesaService();
  const parcelaService = ParcelaService();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const carregarDespesaResumoMensal = async () => {
      if (token !== null && typeof token === 'string') {
        const [anoStr, mesStr] = formatarDataParaAnoMes(dataSelecionada).split('-');
        const ano = parseInt(anoStr);
        const mes = parseInt(mesStr);

        const resultDespesas = await despesaService.listarDespesaResumoMensal(token, 0, 10, ano, mes);

        if (resultDespesas) {
          setDespesasResumoMensal(resultDespesas.content);
        }

        const resultValorTotal = await parcelaService.getValorTotalParcelasMensal(token, ano, mes);

        if (resultValorTotal) {
          setValorTotal(resultValorTotal.valueOf());
        } else {
          setValorTotal(0);
        }
      }
    };

    carregarDespesaResumoMensal();
  }, [token, dataSelecionada])

  const handleClickRow = (item: DespesaResumoMensal) => {
    if (idDespesaSelecionada === item.id) {
      setIdDespesaSelecionada(null);
    } else {
      setIdDespesaSelecionada(item.id);
    }
  };
  
  const handleRowSelected = (item: DespesaResumoMensal) => {
    return idDespesaSelecionada === item.id;
  };

  const handleUpdateVencimento = (value: string | number | boolean | Date) => {
    if (typeof value === 'string') {
      setDataSelecionada(formataraMesAnoParaData(value)); 
    }
  };

  return (
    <Container>
      <Panel maxWidth='1000px' title='Resumo Mensal'>
        <FieldValue 
          description='Data' 
          type='month' 
          value={(dataSelecionada)} 
          editable={true}
          width='180px'
          onUpdate={handleUpdateVencimento} />
        <FieldValue 
          description='Valor Total' 
          type='string' 
          value={formatarValorParaReal(valorTotal)}
          width='180px' />
      </Panel>

      <Panel maxWidth='1000px' title='Despesas'>
        <Table
          values={despesasResumoMensal}
          messageEmpty="Nenhuma despesa encontrada."
          keyExtractor={(item) => item.id.toString()}
          onClickRow={handleClickRow}
          rowSelected={handleRowSelected}
        >
          <Column<DespesaResumoMensal> fieldName="categoria.descricao" header="Categoria" value={(item) => item.categoria.descricao} />
          <Column<DespesaResumoMensal> fieldName="descricao" header="Descrição" value={(item) => item.descricao} />
          <Column<DespesaResumoMensal> fieldName="valorTotal" header="Valor" value={(item) => formatarValorParaReal(item.valorTotal)} />
          <Column<DespesaResumoMensal> fieldName="situacao" header="Situação" value={(item) => item.situacao} />
        </Table>
      </Panel>
    </Container>
  );
};
  
export default ConsultaDespesaResumoMensal;
