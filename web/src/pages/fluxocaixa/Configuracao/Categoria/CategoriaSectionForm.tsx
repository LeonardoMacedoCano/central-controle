import { FieldValue, FlexBox } from "../../../../components";
import { Categoria } from "../../../../types";

interface CategoriaSectionSectionFormProps {
  categoria: Categoria;
  onUpdate: (categoriaAtualizada: Categoria) => void;
}

const CategoriaSectionForm: React.FC<CategoriaSectionSectionFormProps> = ({ categoria, onUpdate }) => {

  const updateCategoria = (updatedFields: Partial<Categoria>) => {
      const updatedCategoria: Categoria = {
        ...categoria!,
        ...updatedFields,
      };
      onUpdate(updatedCategoria);
    };

  const handleUpdateDescricao = (value: any) => {
    if (typeof value === 'string') {
      updateCategoria({ descricao: value });
    }
  };

  return (
    <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
      <FlexBox flexDirection="row" >
        <FlexBox.Item>
          <FieldValue 
            description='Descrição'
            type='string'
            value={categoria.descricao}
            editable={true}
            onUpdate={handleUpdateDescricao}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
};

export default CategoriaSectionForm;