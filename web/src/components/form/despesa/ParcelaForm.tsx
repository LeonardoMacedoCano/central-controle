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

  const handleUpdateDataVencimento = (value: any) => {
    if (value instanceof Date) {
      const parcelaAtualizada: Parcela = {
        ...parcela!,
        dataVencimento: value
      };
      onUpdate(parcelaAtualizada);
    }
  };  
  
  const handleUpdateValor = (value: any) => {
    if (typeof value === 'number') {
      const parcelaAtualizada: Parcela = {
        ...parcela!,
        valor: value
      };
      onUpdate(parcelaAtualizada);
    }
  };

  return (
    <FlexBox 
      flexDirection="row"
    >
      <FlexBox.Item
        borderRight  
      >
        <FieldValue 
          description='Numero'
          type='number'
          value={parcela.numero}
        />
      </FlexBox.Item>
      <FlexBox.Item
        borderRight  
      >
        <FieldValue 
          description='Data Vencimento'
          type='date'
          value={formatarDataParaStringYMD(parcela.dataVencimento)}
          editable={true}
          onUpdate={handleUpdateDataVencimento}
        />
      </FlexBox.Item>
      <FlexBox.Item  
      >
        <FieldValue 
          description='Valor'
          type='number'
          value={parcela.valor}
          editable={true}
          onUpdate={handleUpdateValor}
        />
      </FlexBox.Item>
    </FlexBox>
  )
}

export default ParcelaForm;
