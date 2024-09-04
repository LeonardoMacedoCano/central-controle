import React, { useContext, useEffect, useState } from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Categoria, Despesa } from '../../../types';
import { formatValueToBRL } from '../../../utils';
import { AuthContext, useMessage } from '../../../contexts';
import { DespesaService } from '../../../service';

interface DespesaSectionFormProps {
  despesa: Despesa | undefined;
  onUpdate: (updatedDespesa: Despesa) => void;
}
const DespesaSectionForm: React.FC<DespesaSectionFormProps> = ({ despesa, onUpdate }) => {
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
    const updatedDespesa: Despesa = {
      ...despesa!,
      ...updatedFields
    };
    onUpdate(updatedDespesa);
  };
  
  const handleUpdateCategoria = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    updateDespesa({ categoria: selectedCategoria });
  };

  return (
    <Panel maxWidth='1000px' title='Despesa'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row">
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Situação'
              type='string'
              value={despesa?.situacao || ''}
            />
          </FlexBox.Item>
          <FlexBox.Item>
            <FieldValue 
              description='Valor Total'
              type='number'
              value={formatValueToBRL(despesa?.valorTotal || 0)}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row">
          <FlexBox.Item borderTop borderRight>
            <FieldValue 
              description='Categoria'
              type='select'
              value={{ key: despesa?.categoria.id, value: despesa?.categoria.descricao }}
              editable={true}
              options={categorias.map(categoria => ({ key: categoria.id, value: categoria.descricao }))}
              onUpdate={handleUpdateCategoria}
            />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>
    </Panel>
  );
};

export default DespesaSectionForm;
