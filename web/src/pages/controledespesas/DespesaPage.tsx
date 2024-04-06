import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Despesa } from '../../types/Despesa';
import { Parcela } from '../../types/Parcela';
import { AuthContext } from '../../contexts/auth/AuthContext';
import DespesaService from '../../service/DespesaService';
import Panel from '../../components/panel/Panel';
import Table from '../../components/table/Table';
import Column from '../../components/table/Column';
import { formatarDataParaString } from '../../utils/DateUtils';
import { formatarValorParaReal, formatarDescricaoSituacaoParcela } from '../../utils/ValorUtils';
import DespesaForm from '../../components/form/despesaform/DespesaForm';
import { Categoria } from '../../types/Categoria';

const DespesaPage: React.FC = () => {
  const { idStr } = useParams<{ idStr?: string }>();
  const [token, setToken] = useState<string | null>(null);
  const [despesa, setDespesa] = useState<Despesa>();
  const [categorias, setCategorias] = useState<Categoria[]>([]);
  
  const auth = useContext(AuthContext);
  const despesaService = DespesaService();

  const id = typeof idStr === 'string' ? parseInt(idStr, 10) : 0;

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const carregarDespesa = async () => {
      if (!token || id <= 0) return;

      const resultDespesa = await despesaService.getDespesaByIdWithParcelas(token, id);
      setDespesa(resultDespesa || undefined);

      const resultCategorias = await despesaService.getTodasCategoriasDespesa(token);
      setCategorias(resultCategorias || []);
    };

    carregarDespesa();
  }, [token, id])

  const handleClickRow = (item: Parcela) => {
    console.log(`item: ${item}`);
  };

  const handleAddDespesa = (novaDespesa: Despesa) => {
    console.log(`novaDespesa: ${novaDespesa}`);
  };

  const handleUpdateDespesa = (despesaAtualizada: Despesa) => {
    setDespesa(despesaAtualizada)
  };

  return (
    <>
    <Panel
      maxWidth='1000px' 
      title='Despesa'
    >
      <DespesaForm
        despesa={despesa || null}
        categorias={categorias}
        onAdd={handleAddDespesa}
        onUpdate={handleUpdateDespesa}
      />
    </Panel>

    <Panel
      maxWidth='1000px' 
      title='Parcelas'
    >
      <Table
        values={despesa ? despesa.parcelas : []}
        messageEmpty="Nenhuma parcela encontrada."
        keyExtractor={(item) => item.id.toString()}
        onClickRow={handleClickRow}
      >
        <Column<Parcela> 
          fieldName="numero" 
          header="Número" 
          value={(item) => item.numero} 
        /> 
        <Column<Parcela> 
          fieldName="dataVencimento" 
          header="Data Vencimento" 
          value={(item) => formatarDataParaString(item.dataVencimento)} 
        /> 
        <Column<Parcela> 
          fieldName="valor" 
          header="Valor" 
          value={(item) => formatarValorParaReal(item.valor)} 
        />  
        <Column<Parcela> 
          fieldName="pago" 
          header="Situação" 
          value={(item) => formatarDescricaoSituacaoParcela(item.pago)} 
        /> 
      </Table>
    </Panel>
  </>
  )
}

export default DespesaPage;