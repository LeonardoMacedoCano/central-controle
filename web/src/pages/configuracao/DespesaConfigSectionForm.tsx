import React from 'react';
import { UsuarioConfig } from '../../types/UsuarioConfig';
import FlexBox from '../../components/flexbox/FlexBox';
import FieldValue from '../../components/fieldvalue/FieldValue';

interface DespesaConfigSectionFormProps {
  usuarioConfig: UsuarioConfig;
  onUpdate: (usuarioConfigAtualizado: UsuarioConfig) => void;
}

const DespesaConfigSectionForm: React.FC<DespesaConfigSectionFormProps> = ({ usuarioConfig, onUpdate }) => {

  const updateUsuarioConfig = (updatedFields: Partial<UsuarioConfig>) => {
    const usuarioConfigAtualizado: UsuarioConfig = {
      ...usuarioConfig!,
      ...updatedFields
    };
    onUpdate(usuarioConfigAtualizado);
  };

  const handleValorMetaMensal = (value: any) => {
    if (typeof value === 'number') {
      updateUsuarioConfig({ despesaValorMetaMensal: value });
    }
  };

  return (
    <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
      <FlexBox flexDirection="row" >
        <FlexBox.Item >
          <FieldValue 
            description='Valor Meta Mensal'
            hint='Valor teto para meta de gastos mensais'
            type='number'
            value={usuarioConfig.despesaValorMetaMensal}
            editable={true}
            minValue={0}
            onUpdate={handleValorMetaMensal}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
}

export default DespesaConfigSectionForm;