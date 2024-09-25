import React, { useContext, useEffect, useState } from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Categoria, Despesa, despesaFormaPagamentoOptions, getCodigoDespesaFormaPagamento, getDescricaoDespesaFormaPagamento, getDespesaFormaPagamentoByCodigo } from '../../../types';
import { formatDateToYMDString } from '../../../utils';
import { AuthContext, useMessage } from '../../../contexts';
import { CategoriaDespesaService } from '../../../service';

interface DespesaSectionFormProps {
  despesa: Despesa;
  onUpdate: (updatedDespesa: Despesa) => void;
}
const DespesaSectionForm: React.FC<DespesaSectionFormProps> = ({ despesa, onUpdate }) => {
  const [categorias, setCategorias] = useState<Categoria[]>([]);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const categoriaDespesaService = CategoriaDespesaService();

  useEffect(() => {
    const carregarCategoriasDespesa = async () => {
      if (!auth.usuario?.token) return;
  
      try {
        const result = await categoriaDespesaService.getTodasCategoriasDespesa(auth.usuario?.token);
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

  const handleUpdateDataVencimento = (value: any) => {
    if (value instanceof Date) {
      updateDespesa({ dataVencimento: value });
    }
  };

  const handleUpdatePago = (value: any) => {
    if (typeof value === 'boolean') {
      updateDespesa({ pago: value });
    }
  };

  const handleFormaPagamento = (value: any) => {
    const selectedFormaPagamento = getDespesaFormaPagamentoByCodigo(Number(value)); 
    updateDespesa({ formaPagamento: selectedFormaPagamento });
  };

  const handleUpdateValor = (value: any) => {
    if (typeof value === 'number') {
      updateDespesa({ valor: value });
    }
  };

  return (
    <Panel maxWidth='1000px' title='Despesa'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Vencimento'
              type='date'
              value={formatDateToYMDString(despesa?.dataVencimento)}
              editable={true}
              onUpdate={handleUpdateDataVencimento}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='select'
              value={{ key: despesa?.categoria?.id, value: despesa?.categoria?.descricao }}
              editable={true}
              options={categorias.map(categoria => ({ key: categoria.id, value: categoria.descricao }))}
              onUpdate={handleUpdateCategoria}
            />
          </FlexBox.Item>
        </FlexBox>

        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight >
            <FieldValue 
              description='Valor'
              type='number'
              value={despesa.valor}
              editable={true}
              onUpdate={handleUpdateValor}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Forma Pagamento'
              type='select'
              value={{ key: getCodigoDespesaFormaPagamento(despesa?.formaPagamento), value: getDescricaoDespesaFormaPagamento(despesa?.formaPagamento) }}
              editable={true}
              options={despesaFormaPagamentoOptions}
              onUpdate={handleFormaPagamento}
            />
          </FlexBox.Item>
        </FlexBox>

        <FlexBox flexDirection="row">
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Pago'
              type='boolean'
              value={despesa?.pago || false}
              editable={true}
              onUpdate={handleUpdatePago}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Situação'
              type='string'
              value={despesa?.situacao || ''}
            />
          </FlexBox.Item>
        </FlexBox>

      </FlexBox>
    </Panel>
  );
};

export default DespesaSectionForm;
