import React, { useContext, useEffect, useState } from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Categoria, Receita } from '../../../types';
import { AuthContext, useMessage } from '../../../contexts';
import { ReceitaCategoriaService } from '../../../service';
import { formatDateToYMDString } from '../../../utils';

interface ReceitaSectionFormProps {
  receita: Receita;
  onUpdate: (updatedReceita: Receita) => void;
}
const ReceitaSectionForm: React.FC<ReceitaSectionFormProps> = ({ receita, onUpdate }) => {
  const [categorias, setCategorias] = useState<Categoria[]>([]);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const receitaCategoriaService = ReceitaCategoriaService();

  useEffect(() => {
    const carregarCategoriasReceita = async () => {
      if (!auth.usuario?.token) return;
  
      try {
        const result = await receitaCategoriaService.getAllCategorias(auth.usuario?.token);
        setCategorias(result || []);
      } catch (error) {
        message.showErrorWithLog('Erro ao carregar as categorias de receita.', error);
      }
    };

    carregarCategoriasReceita();
  }, []);

  const updateReceita = (updatedFields: Partial<Receita>) => {
    const updatedReceita: Receita = {
      ...receita!,
      ...updatedFields
    };
    onUpdate(updatedReceita);
  };
  
  const handleUpdateCategoria = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    updateReceita({ categoria: selectedCategoria });
  };

  const handleUpdateValor = (value: any) => {
    updateReceita({ valor: value });
  };

  const handleUpdateDataRecebimento = (value: any) => {
    if (value instanceof Date) {
      updateReceita({ dataRecebimento: value });
    }
  };

  return (
    <Panel maxWidth='1000px' title='Receita' padding='15px 0 0 0'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Recebimento'
              type='date'
              value={formatDateToYMDString(receita?.dataRecebimento)}
              editable={true}
              onUpdate={handleUpdateDataRecebimento}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='select'
              value={{ key: String(receita?.categoria?.id), value: receita?.categoria?.descricao }}
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
                value={receita.valor}
                editable={true}
                onUpdate={handleUpdateValor}
              />
          </FlexBox.Item>
        </FlexBox>

      </FlexBox>
    </Panel>
  );
};

export default ReceitaSectionForm;
