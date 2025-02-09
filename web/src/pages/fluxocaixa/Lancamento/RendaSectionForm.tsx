import React, { useContext, useEffect, useState } from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Categoria, Renda } from '../../../types';
import { AuthContext, useMessage } from '../../../contexts';
import { RendaCategoriaService } from '../../../service';
import { formatDateToYMDString } from '../../../utils';

interface RendaSectionFormProps {
  renda: Renda;
  onUpdate: (updatedRenda: Renda) => void;
}
const RendaSectionForm: React.FC<RendaSectionFormProps> = ({ renda, onUpdate }) => {
  const [categorias, setCategorias] = useState<Categoria[]>([]);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const rendaCategoriaService = RendaCategoriaService();

  useEffect(() => {
    const carregarCategoriasRenda = async () => {
      if (!auth.usuario?.token) return;
  
      try {
        const result = await rendaCategoriaService.getAllCategorias(auth.usuario?.token);
        setCategorias(result || []);
      } catch (error) {
        message.showErrorWithLog('Erro ao carregar as categorias de renda.', error);
      }
    };

    carregarCategoriasRenda();
  }, []);

  const updateRenda = (updatedFields: Partial<Renda>) => {
    const updatedRenda: Renda = {
      ...renda!,
      ...updatedFields
    };
    onUpdate(updatedRenda);
  };
  
  const handleUpdateCategoria = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    updateRenda({ categoria: selectedCategoria });
  };

  const handleUpdateValor = (value: any) => {
    updateRenda({ valor: value });
  };

  const handleUpdateDataRecebimento = (value: any) => {
    if (value instanceof Date) {
      updateRenda({ dataRecebimento: value });
    }
  };

  return (
    <Panel maxWidth='1000px' title='Renda' padding='15px 0 0 0'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Recebimento'
              type='date'
              value={formatDateToYMDString(renda?.dataRecebimento)}
              editable={true}
              onUpdate={handleUpdateDataRecebimento}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='select'
              value={{ key: String(renda?.categoria?.id), value: renda?.categoria?.descricao }}
              editable={true}
              options={categorias.map(categoria => ({ key: String(categoria.id), value: categoria.descricao }))}
              onUpdate={handleUpdateCategoria}
            />
          </FlexBox.Item>
        </FlexBox>

        <FlexBox flexDirection="row" >
          <FlexBox.Item >
            <FieldValue 
                description='Valor'
                type='number'
                value={renda.valor}
                editable={true}
                onUpdate={handleUpdateValor}
              />
          </FlexBox.Item>
        </FlexBox>

      </FlexBox>
    </Panel>
  );
};

export default RendaSectionForm;
