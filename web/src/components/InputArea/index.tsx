import React, { useState, useEffect } from 'react';
import * as C from './styles';
import { FormFields } from '../../types/FormFields';
import { InputField } from '../../types/InputField';
import { Categoria } from '../../types/Categoria';
import { FaTrashAlt } from 'react-icons/fa';

type Props = {
  campos: InputField[];
  onAdd: (data: FormFields) => void;
  onEdit: (data: FormFields) => void;
  onDelete: () => void;
  itemSelecionado: number | null;
  opcoesCategoria: Categoria[];
  valoresIniciais?: FormFields;
};

export const InputArea: React.FC<Props> = ({
  campos,
  onAdd,
  onEdit,
  onDelete,
  itemSelecionado,
  opcoesCategoria,
  valoresIniciais,
}) => {
  const [formFields, setFormFields] = useState<FormFields>({
    data: '',
    categoria: '',
    descricao: '',
    valor: 0,
  });

  useEffect(() => {
    setFormFields(valoresIniciais || {
      data: '',
      categoria: '',
      descricao: '',
      valor: 0,
    });
  }, [valoresIniciais]);

  const clearFields = () => {
    setFormFields({
      data: '',
      categoria: '',
      descricao: '',
      valor: 0,
    });
  };

  const handleInputChange = (key: keyof FormFields, value: string | number) => {
    setFormFields((prevFields) => ({ ...prevFields, [key]: value }));
  };

  const handleDeleteEvent = () => {
    if (itemSelecionado !== null) {
      clearFields();
      onDelete();
    }
  };
  
  const handleAddOrEditEvent = () => {
    const errors: string[] = campos
      .filter(({ key }) => !formFields[key])
      .map(({ label }) => `${label} vazio!`);
  
    if (isNaN(new Date(formFields.data).getTime())) {
      errors.push('Data inválida!');
    }
  
    if (errors.length > 0) {
      alert(errors.join('\n'));
    } else {
      itemSelecionado === null ? onAdd(formFields) : onEdit(formFields);
      clearFields();
    }
  };
  
  return (
    <C.Container>
      {campos.map(({ label, type, key }) => (
        <C.InputLabel key={key}>
          <C.InputTitle>{label}</C.InputTitle>
          {type === 'select' ? (
            <C.Select value={formFields[key] as string} onChange={(e) => handleInputChange(key, e.target.value)}>
              <option value="">Selecione...</option>
              {opcoesCategoria.map((categoria) => (
                <option key={categoria.id} value={categoria.descricao}>
                  {categoria.descricao}
                </option>
              ))}
            </C.Select>
          ) : (
            <C.Input
              type={type}
              value={formFields[key]}
              onChange={(e) => handleInputChange(key, e.target.value)}
            />
          )}
        </C.InputLabel>
      ))}

      <C.InputLabel>
        <C.InputTitle>&nbsp;</C.InputTitle>
        {itemSelecionado === null ? (
          <C.ButtonAdd onClick={handleAddOrEditEvent}>
          Adicionar
        </C.ButtonAdd>
        ) : (
          <C.ButtonEdit onClick={handleAddOrEditEvent}>
          Editar
        </C.ButtonEdit>
        )}
        {itemSelecionado !== null && (
          <C.ButtonDelete onClick={handleDeleteEvent}>
            <FaTrashAlt />
          </C.ButtonDelete>
        )}
      </C.InputLabel>
    </C.Container>
  );
};
