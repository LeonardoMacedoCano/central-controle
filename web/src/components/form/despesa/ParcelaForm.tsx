import React from 'react';
import { Parcela } from '../../../types/Parcela';
import FlexBox from '../../flexbox/FlexBox';
import FieldValue from '../../fieldvalue/FieldValue';
import { formatarDataParaStringYMD } from '../../../utils/DateUtils';

interface ParcelaFormProps {
  parcela: Parcela;
  onUpdate: (parcelaAtualizada: Parcela) => void;
}

const ParcelaForm: React.FC<ParcelaFormProps> = ({ parcela, onUpdate }) => {

  const updateParcela = (updatedFields: Partial<Parcela>) => {
    const parcelaAtualizada: Parcela = {
      ...parcela!,
      ...updatedFields
    };
    onUpdate(parcelaAtualizada);
  };

  const handleUpdateDataVencimento = (value: any) => {
    if (value instanceof Date) {
      updateParcela({ dataVencimento: value });
    }
  };
  
  const handleUpdateValor = (value: any) => {
    if (typeof value === 'number') {
      updateParcela({ valor: value });
    }
  };

  const handleUpdatePago = (value: any) => {
    if (typeof value === 'boolean') {
      updateParcela({ pago: value });
    }
  };

  return (
    <FlexBox flexDirection="column">
      <FlexBox flexDirection="row">
        <FlexBox.Item>
          <FieldValue 
            description='Numero'
            type='number'
            value={parcela.numero}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderTop borderRight>
          <FieldValue 
            description='Data Vencimento'
            type='date'
            value={formatarDataParaStringYMD(parcela.dataVencimento)}
            editable={true}
            onUpdate={handleUpdateDataVencimento}
          />
        </FlexBox.Item>
        <FlexBox.Item borderTop borderRight>
          <FieldValue 
            description='Valor'
            type='number'
            value={parcela.valor}
            editable={true}
            onUpdate={handleUpdateValor}
          />
        </FlexBox.Item>
        <FlexBox.Item borderTop>
          <FieldValue 
            description='Pago'
            type='boolean'
            value={parcela.pago}
            editable={true}
            onUpdate={handleUpdatePago}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
}

export default ParcelaForm;
