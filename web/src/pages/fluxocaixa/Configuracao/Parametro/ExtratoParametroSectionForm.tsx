import React, {  } from 'react';
import { FluxoCaixaParametro} from '../../../../types';
import { FieldValue, FlexBox} from '../../../../components';

interface props {
  parametros: FluxoCaixaParametro;
  onUpdate: (parametrosAtualizado: FluxoCaixaParametro) => void;
}

const ExtratoParametroSectionForm: React.FC<props> = ({ parametros, onUpdate }) => {
  const handleMetaAporteMensal = (value: any) => {
    onUpdate({ ...parametros, metaAporteMensal: value });
  };

  return (
    <FlexBox flexDirection="column">
      <FlexBox flexDirection="row">
        <FlexBox.Item>
          <FieldValue 
            description="Dia Vencimento Fatura"
            hint="Dia padrão do vencimento da fatura do cartão."
            type="number"
            value={parametros.diaPadraoVencimentoCartao}
            editable={true}
            minValue={1}
            maxValue={28}
            onUpdate={handleMetaAporteMensal}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
};

export default ExtratoParametroSectionForm;