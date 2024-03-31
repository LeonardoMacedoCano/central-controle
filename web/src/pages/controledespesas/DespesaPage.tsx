import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Despesa } from '../../types/Despesa';
import { Parcela } from '../../types/Parcela';
import { AuthContext } from '../../contexts/auth/AuthContext';
import DespesaService from '../../service/DespesaService';
import Panel from '../../components/panel/Panel';
import Table from '../../components/table/Table';
import Column from '../../components/table/Column';

const DespesaPage: React.FC = () => {
  const { idStr } = useParams<{ idStr?: string }>();
  const [token, setToken] = useState<string | null>(null);
  const [despesa, setDespesa] = useState<Despesa>();
  
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
    };

    carregarDespesa();
  }, [token, id])

  const handleClickRow = (item: Parcela) => {
    //
  };

  return (
    <Panel
      maxWidth='1000px' 
      title='Parcelas'
    >
      <Table
        values={despesa ? despesa.parcelas : []}
        messageEmpty="Nenhuma despesa encontrada."
        keyExtractor={(item) => item.id.toString()}
        onClickRow={handleClickRow}
      >
        <Column<Parcela> 
          fieldName="numero" 
          header="Categoria" 
          value={(item) => item.numero} 
        /> 
        <Column<Parcela> 
          fieldName="valor" 
          header="Categoria" 
          value={(item) => item.valor} 
        />  
      </Table>
    </Panel>
  )
}

export default DespesaPage;