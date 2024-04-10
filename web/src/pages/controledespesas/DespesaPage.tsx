import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AuthContext } from '../../contexts/auth/AuthContext';
import DespesaService from '../../service/DespesaService';
import Panel from '../../components/panel/Panel';
import Table from '../../components/table/Table';
import Column from '../../components/table/Column';
import DespesaForm from '../../components/form/despesa/DespesaForm';
import ParcelaForm from '../../components/form/despesa/ParcelaForm';
import Button from '../../components/button/button/Button';
import FloatingButton from '../../components/button/Floatingbutton/FloatingButton';
import { FaSave } from 'react-icons/fa';
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
    carregarDespesa();
  }, [token, id]);

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

  const criarNovaParcela = () => {
    const novoNumeroParcela = despesa.parcelas.length + 1;
    const novaParcela: Parcela = {
      id: 0,
      numero: novoNumeroParcela,
      dataVencimento: getDataAtual(),
      valor: 0,
      pago: false
    };
  
    setDespesa(prevDespesa => ({ ...prevDespesa, parcelas: [...prevDespesa.parcelas, novaParcela] }));
    setNumeroParcelaSelecionada(novoNumeroParcela);
  };

  const isRowSelected = (item: Parcela) => numeroParcelaSelecionada === item.numero;

  const handleClickRow = (item: Parcela) => {
    setNumeroParcelaSelecionada(prevId => prevId === item.numero ? null : item.numero);
  };

  const handleUpdateDespesa = (despesaAtualizada: Despesa) => {
    setDespesa(despesaAtualizada);
  };

  const handleUpdateParcela = (parcelaAtualizada: Parcela) => {
    const updatedParcelas = despesa.parcelas.map(p => p.numero === parcelaAtualizada.numero ? parcelaAtualizada : p);
    setDespesa(prevDespesa => ({ ...prevDespesa, parcelas: updatedParcelas }));
  };

  const handleAddParcela = () => {
    criarNovaParcela();
    setShowParcelaForm(true);
  };

  const handleEditParcela = () => setShowParcelaForm(true);

  const handleDeleteParcela = () => {
    console.log('handleDeleteParcela');  
  };

  const handleSaveDespesa = () => {
    setShowParcelaForm(false);

    if (token) {
      if (id > 0) {
        despesaService.editarDespesa(token, id, despesa);
      } else {
        despesaService.gerarDespesa(token, despesa);
      }
    }
  };

  const handleSaveParcela = () => {
    setShowParcelaForm(false);
  };

  return (
    <>
      <FloatingButton
        mainButtonIcon={<FaSave />}
        mainButtonHint='Salvar Despesa'
        mainAction={showParcelaForm ? handleSaveParcela : handleSaveDespesa}
      />
      {showParcelaForm ? (
        <Panel
          maxWidth='1000px' 
          title='Parcela'
        >
          <ParcelaForm
            parcela={despesa.parcelas[despesa.parcelas.length - 1]}
            onUpdate={handleUpdateParcela}
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
              rowSelected={isRowSelected}
              customHeader={
                <>
                  <Button 
                    variant='table-add' 
                    onClick={handleAddParcela} 
                    disabled={numeroParcelaSelecionada !== null && numeroParcelaSelecionada > 0} 
                  />
                  <Button 
                    variant='table-edit' 
                    onClick={handleEditParcela} 
                    disabled={!numeroParcelaSelecionada} 
                  />
                  <Button 
                    variant='table-delete' 
                    onClick={handleDeleteParcela} 
                    disabled={!numeroParcelaSelecionada} 
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
      )}
    </>
  )
}

export default DespesaPage;
