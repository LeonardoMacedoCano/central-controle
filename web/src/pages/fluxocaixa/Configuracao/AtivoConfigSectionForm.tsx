import React from 'react';
import { FluxoCaixaConfig} from '../../../types';
import { FieldValue, FlexBox} from '../../../components';

interface AtivoConfigSectionFormProps {
  config: FluxoCaixaConfig;
  onUpdate: (configAtualizado: FluxoCaixaConfig) => void;
}

const AtivoConfigSectionForm: React.FC<AtivoConfigSectionFormProps> = ({ config, onUpdate }) => {
  const handleMetaAporteMensal = (value: any) => {
    onUpdate({ ...config, metaAporteMensal: value });
  };

  const handleMetaAporteTotal = (value: any) => {
    onUpdate({ ...config, metaAporteTotal: value });
  };

  return (
    <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue 
            description="Meta aporte mensal"
            hint="Meta valor de aporte mensal."
            type="number"
            value={config.metaAporteMensal}
            editable={true}
            minValue={0}
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
            value={config.metaAporteTotal}
            editable={true}
            minValue={0}
            onUpdate={handleMetaAporteTotal}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
};

export default AtivoConfigSectionForm;