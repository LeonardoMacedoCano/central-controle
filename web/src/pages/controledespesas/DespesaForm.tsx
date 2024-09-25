import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../contexts/auth/AuthContext';
import { useMessage } from '../../contexts/message/ContextMessageProvider';
import DespesaService from '../../service/fluxocaixa/CategoriaDespesaService';
import { Despesa } from '../../types/fluxocaixa/Despesa';
import { Categoria } from '../../types/Categoria';
import { formatDateToShortString } from '../../utils/DateUtils';
import { formatValueToBRL } from '../../utils/ValorUtils';
import FlexBox from '../../components/flexbox/FlexBox';
import FieldValue from '../../components/fieldvalue/FieldValue';

interface DespesaFormProps {
  despesa: Despesa;
  onUpdate: (despesaAtualizada: Despesa) => void;
}

const DespesaForm: React.FC<DespesaFormProps> = ({ despesa, onUpdate }) => {
  const [categorias, setCategorias] = useState<Categoria[]>([]);
  
  const auth = useContext(AuthContext);
  const message = useMessage();
  const despesaService = DespesaService();

  useEffect(() => {
    const carregarCategoriasDespesa = async () => {
      if (!auth.usuario?.token) return;
  
      try {
        const result = await despesaService.getTodasCategoriasDespesa(auth.usuario?.token);
        setCategorias(result || []);
      } catch (error) {
        message.showErrorWithLog('Erro ao carregar as categorias de despesa.', error);
      }
    };

    carregarCategoriasDespesa();
  }, []);

  const updateDespesa = (updatedFields: Partial<Despesa>) => {
    const despesaAtualizada: Despesa = {
      ...despesa!,
      ...updatedFields
    };
    onUpdate(despesaAtualizada);
  };

  const handleUpdateCategoria = (value: any) => {
    const categoriaId = String(value);
    const categoriaSelecionada = categorias.find(c => String(c.id) === categoriaId); 
    if (categoriaSelecionada) {
      updateDespesa({ categoria: categoriaSelecionada });
    }
  };

  const handleUpdateDescricao = (value: any) => {
    if (typeof value === 'string') {
      updateDespesa({ descricao: value });
    }
  };
  
  return (
    <FlexBox flexDirection="column">
      <FlexBox flexDirection="row">
        <FlexBox.Item borderRight>
          <FieldValue 
            description='Data Lançamento'
            type='string'
            value={formatDateToShortString(despesa.dataLancamento)}
          />
        </FlexBox.Item>
        <FlexBox.Item borderRight>
          <FieldValue 
            description='Situação'
            type='string'
            value={despesa.situacao}
          />
        </FlexBox.Item>
        <FlexBox.Item>
          <FieldValue 
            description='Valor Total'
            type='number'
            value={formatValueToBRL(despesa.valorTotal)}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderTop borderRight>
          <FieldValue 
            description='Categoria'
            type='select'
            value={{ key: despesa.categoria.id, value: despesa.categoria.descricao }}
            editable={true}
            options={categorias.map(categoria => ({ key: categoria.id, value: categoria.descricao }))}
            onUpdate={handleUpdateCategoria}
          />
        </FlexBox.Item>
        <FlexBox.Item borderTop>
          <FieldValue 
            description='Descrição'
            type='string'
            value={despesa.descricao}
            editable={true}
            onUpdate={handleUpdateDescricao}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
}

export default DespesaForm;
