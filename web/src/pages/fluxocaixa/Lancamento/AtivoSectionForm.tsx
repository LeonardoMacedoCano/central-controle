import React, { useContext, useEffect, useState } from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Ativo, ativoOperacaoOptions, Categoria, getAtivoOperacaoByCodigo, getCodigoAtivoOperacao, getDescricaoAtivoOperacao } from '../../../types';
import { formatDateToYMDString } from '../../../utils';
import { AuthContext, useMessage } from '../../../contexts';
import { AtivoCategoriaService } from '../../../service';

interface AtivoSectionFormProps {
  ativo: Ativo;
  onUpdate: (updatedAtivo: Ativo) => void;
}

const AtivoSectionForm: React.FC<AtivoSectionFormProps> = ({ ativo, onUpdate }) => {
  const [categorias, setCategorias] = useState<Categoria[]>([]);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const ativoCategoriaService = AtivoCategoriaService();

  useEffect(() => {
    const carregarCategoriasDespesa = async () => {
      if (!auth.usuario?.token) return;
  
      try {
        const result = await ativoCategoriaService.getTodasCategoriasAtivo(auth.usuario?.token);
        setCategorias(result || []);
      } catch (error) {
        message.showErrorWithLog('Erro ao carregar as categorias de ativo.', error);
      }
    };

    carregarCategoriasDespesa();
  }, []);

  const updateAtivo = (updatedFields: Partial<Ativo>) => {
    const updatedAtivo: Ativo = {
      ...ativo!,
      ...updatedFields
    };
    onUpdate(updatedAtivo);
  };

  const handleUpdateDataMovimento = (value: any) => {
    if (value instanceof Date) {
      updateAtivo({ dataMovimento: value });
    }
  };

  const handleUpdateCategoria = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    updateAtivo({ categoria: selectedCategoria });
  };

  const handleUpdatePrecoUnitario = (value: any) => {
    updateAtivo({ precoUnitario: value });
  };

  const handleUpdateQuantidade = (value: any) => {
    updateAtivo({ quantidade: value });
  };
  

  const handleUpdateOperacao = (value: any) => {
    const selectedOperacao = getAtivoOperacaoByCodigo(value); 
    updateAtivo({ operacao: selectedOperacao });
  };

  const handleUpdateTicker = (value: any) => {
    if (typeof value === 'string') {
      updateAtivo({ ticker: value });
    }
  };

  return (
    <Panel maxWidth='1000px' title='Ativo' padding='15px 0 0 0'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Movimentação'
              type='date'
              value={formatDateToYMDString(ativo?.dataMovimento)}
              editable={true}
              onUpdate={handleUpdateDataMovimento}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='select'
              value={{ key: String(ativo?.categoria?.id), value: ativo?.categoria?.descricao }}
              editable={true}
              options={categorias.map(categoria => ({ key: String(categoria.id), value: categoria.descricao }))}
              onUpdate={handleUpdateCategoria}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight >
            <FieldValue 
              description='Ticker'
              type='string'
              value={ativo?.ticker}
              editable={true}
              onUpdate={handleUpdateTicker}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
                description='Operação'
                type='select'
                value={{ key: getCodigoAtivoOperacao(ativo?.operacao), value: getDescricaoAtivoOperacao(ativo?.operacao) }}
                editable={true}
                options={ativoOperacaoOptions}
                onUpdate={handleUpdateOperacao}
              />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row">
          <FlexBox.Item borderRight >
            <FieldValue 
              description='Preço Unitário'
              type='number'
              value={ativo.precoUnitario}
              editable={true}
              onUpdate={handleUpdatePrecoUnitario}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Quantidade'
              type='number'
              value={ativo.quantidade}
              editable={true}
              maxDecimalPlaces={6}
              maxIntegerDigits={12}
              onUpdate={handleUpdateQuantidade}
            />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>
    </Panel>
  );
};

export default AtivoSectionForm;
