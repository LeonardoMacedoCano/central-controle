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

  const handleNumeroMaxItemPagina = (value: any) => {
    if (typeof value === 'number') {
      updateUsuarioConfig({ despesaNumeroMaxItemPagina: value });
    }
  };

  const handleValorMetaMensal = (value: any) => {
    if (typeof value === 'number') {
      updateUsuarioConfig({ despesaValorMetaMensal: value });
    }
  };

  const handleDiaPadraoVencimento = (value: any) => {
    if (typeof value === 'number') {
      updateUsuarioConfig({ despesaDiaPadraoVencimento: value });
    }
  };

  return (
    <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
      <FlexBox flexDirection="row" >
        <FlexBox.Item borderRight >
          <FieldValue 
            description='Número Itens Página'
            hint='Número máximo de itens por página'
            type='number'
            value={usuarioConfig.despesaNumeroMaxItemPagina}
            minValue={1}
            editable={true}
            onUpdate={handleNumeroMaxItemPagina}
          />
        </FlexBox.Item>
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
      <FlexBox flexDirection="row" borderTop>
        <FlexBox.Item borderRight >
          <FieldValue 
            description='Dia Padrão Vencimento'
            hint='Dia padrão para vencimento'
            type='number'
            value={usuarioConfig.despesaDiaPadraoVencimento}
            editable={true}
            minValue={1}
            maxValue={28}
            onUpdate={handleDiaPadraoVencimento}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
}

export default DespesaConfigSectionForm;