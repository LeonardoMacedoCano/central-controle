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

  const handleMetaAporteTotal = (value: any) => {
    onUpdate({ ...parametros, metaAporteTotal: value });
  };

  return (
    <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
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
      <FlexBox flexDirection="row">
        <FlexBox.Item>
          <FieldValue 
            description="Meta aporte total"
            hint="Meta valor de aporte total."
            type="number"
            value={parametros.metaAporteTotal}
            editable={true}
            minValue={0}
            onUpdate={handleMetaAporteTotal}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
};

export default ExtratoParametroSectionForm;