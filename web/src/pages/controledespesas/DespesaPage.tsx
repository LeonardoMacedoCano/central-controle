import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Despesa } from '../../types/Despesa';
import { Parcela } from '../../types/Parcela';
import { AuthContext } from '../../contexts/auth/AuthContext';
import DespesaService from '../../service/DespesaService';
import Panel from '../../components/panel/Panel';
import Table from '../../components/table/Table';
import Column from '../../components/table/Column';
import { formatarDataParaString, getDataAtual } from '../../utils/DateUtils';
import { formatarValorParaReal, formatarDescricaoSituacaoParcela } from '../../utils/ValorUtils';
import DespesaForm from '../../components/form/despesaform/DespesaForm';
import { Categoria } from '../../types/Categoria';
import Button from '../../components/button/Button';

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
  const [idParcelaSelecionada, setIdParcelaSelecionada] = useState<number | null>(null);
  
  const auth = useContext(AuthContext);
  const despesaService = DespesaService();

  const id = typeof idStr === 'string' ? parseInt(idStr, 10) : 0;

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    const carregarDespesa = async () => {
      if (token) {
        try {
          const resultDespesa = (id > 0 ? await despesaService.getDespesaByIdWithParcelas(token, id) : null);
          const resultCategorias = await despesaService.getTodasCategoriasDespesa(token);
  
          if (resultDespesa) {
            setDespesa(resultDespesa);
          }
          setCategorias(resultCategorias || []);
        } catch (error) {
          console.error("Erro ao carregar a despesa:", error);
        }
      }
    };
  
    carregarDespesa();
  }, [token, id]);
  

  const handleClickRow = (item: Parcela) => {
    setIdParcelaSelecionada(prevId => prevId === item.id ? null : item.id);
  };

  const handleRowSelected = (item: Parcela) => {
    return idParcelaSelecionada === item.id;
  };

  const handleUpdateDespesa = (despesaAtualizada: Despesa) => {
    setDespesa(despesaAtualizada)
  };

  const handleAddParcela = () => {
    console.log('handleAddParcela');  
  };
  
  const handleEditParcela = () => {
    console.log('handleEditParcela');  
  };
  
  const handleDeleteParcela = () => {
    console.log('handleDeleteParcela');  
  };

  return (
    <>
    <Panel
      maxWidth='1000px' 
      title='Despesa'
    >
      <DespesaForm
        despesa={despesa}
        categorias={categorias}
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
        rowSelected={handleRowSelected}
          customHeader={
            <>
              <Button 
                variant='table-add' 
                onClick={handleAddParcela} 
                disabled={idParcelaSelecionada !== null && idParcelaSelecionada > 0} 
              />
              <Button 
                variant='table-edit' 
                onClick={handleEditParcela} 
                disabled={!idParcelaSelecionada} 
              />
              <Button 
                variant='table-delete' 
                onClick={handleDeleteParcela} 
                disabled={!idParcelaSelecionada} 
              />
            </>
          }
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
