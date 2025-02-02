import React from 'react';
import { FluxoCaixaParametro} from '../../../../types';
import { FieldValue, FlexBox} from '../../../../components';

interface props {
  parametros: FluxoCaixaParametro;
  onUpdate: (parametrosAtualizado: FluxoCaixaParametro) => void;
}

const AtivoParametroSectionForm: React.FC<props> = ({ parametros, onUpdate }) => {
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
            description="Meta Aporte Mensal"
            hint="Meta valor de aporte mensal."
            type="number"
            value={parametros.metaAporteMensal}
            editable={true}
            minValue={0}
            onUpdate={handleMetaAporteMensal}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item>
          <FieldValue 
            description="Meta Aporte Total"
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

export default AtivoParametroSectionForm;