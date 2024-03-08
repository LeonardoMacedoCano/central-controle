import React, { useState, useContext, useEffect } from 'react';
import { AuthContext } from '../../contexts/auth/AuthContext';
import { DespesaResumoMensal } from '../../types/DespesaResumoMensal';
import { getStringDataAtual } from '../../utils/DateUtils';
import useDespesaApi from '../../hooks/useDespesaApi';
import Table from '../../components/table/Table';
import Column from '../../components/table/Column';
import Panel from '../../components/panel/Panel';
import Container from '../../components/container/Container';

const ConsultaDespesaResumoMensal: React.FC = () => {
  const [token, setToken] = useState<string | null>(null);
  const [dataSelecionada, setDataSelecionada] = useState(() => getStringDataAtual());
  const [idDespesaSelecionada, setIdDespesaSelecionada] = useState<number | null>(null);
  const [despesasResumoMensal, setDespesasResumoMensal] = useState<DespesaResumoMensal[]>([]);

  const auth = useContext(AuthContext);
  const api = useDespesaApi();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const carregarDespesaResumoMensal = async () => {
      if (token !== null && typeof token === 'string') {
        const [anoStr, mesStr] = dataSelecionada.split('-');
        const ano = parseInt(anoStr);
        const mes = parseInt(mesStr);

        const result = await api.listarDespesaResumoMensal(token, 0, 10, ano, mes);

        if (result) {
          setDespesasResumoMensal(result.content);
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
  
  const rowSelected = (item: DespesaResumoMensal) => {
    return idDespesaSelecionada === item.id;
  };

  return (
    <Container>
      <Panel maxWidth='1000px' title='Despesas'>
        <Table
          values={despesasResumoMensal}
          messageEmpty="Nenhuma despesa encontrada."
          keyExtractor={(item) => item.id.toString()}
          onClickRow={handleClickRow}
          rowSelected={rowSelected}
        >
          <Column<DespesaResumoMensal> fieldName="categoria.descricao" header="Categoria" value={(item) => item.categoria.descricao} />
          <Column<DespesaResumoMensal> fieldName="descricao" header="Descrição" value={(item) => item.descricao} />
          <Column<DespesaResumoMensal> fieldName="valorTotal" header="Valor" value={(item) => item.valorTotal} />
          <Column<DespesaResumoMensal> fieldName="situacao" header="Situação" value={(item) => item.situacao} />
        </Table>
      </Panel>
    </Container>
  );
};
  
export default ConsultaDespesaResumoMensal;
