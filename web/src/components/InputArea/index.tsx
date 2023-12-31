import React, { useState, useEffect } from 'react';
import * as C from './styles';
import { FormFields } from '../../types/FormFields';
import { InputAreaProps } from '../../types/InputAreaProps';
import { FaTrashAlt } from 'react-icons/fa';

export const InputArea: React.FC<InputAreaProps> = ({
  inputFields,
  onAdd,
  onEdit,
  onDelete,
  selectedItem,
  categoriaOptions,
  initialValues,
}) => {
  const [formFields, setFormFields] = useState<FormFields>({
    data: '',
    categoria: '',
    descricao: '',
    valor: 0,
  });

  useEffect(() => {
    setFormFields(initialValues || {
      data: '',
      categoria: '',
      descricao: '',
      valor: 0,
    });
  }, [initialValues]);

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
    if (selectedItem !== null) {
      clearFields();
      onDelete();
    }
  };
  
  const handleAddOrEditEvent = () => {
    const errors: string[] = inputFields
      .filter(({ key }) => !formFields[key])
      .map(({ label }) => `${label} vazio!`);
  
    if (isNaN(new Date(formFields.data).getTime())) {
      errors.push('Data inválida!');
    }
  
    if (errors.length > 0) {
      alert(errors.join('\n'));
    } else {
      selectedItem === null ? onAdd(formFields) : onEdit(formFields);
      clearFields();
    }
  };
  
  return (
    <C.Container>
      {inputFields.map(({ label, type, key }) => (
        <C.InputLabel key={key}>
          <C.InputTitle>{label}</C.InputTitle>
          {type === 'select' ? (
            <C.Select value={formFields[key] as string} onChange={(e) => handleInputChange(key, e.target.value)}>
              <option value="">Selecione...</option>
              {categoriaOptions.map((categoria) => (
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
        {selectedItem === null ? (
          <C.ButtonAdd onClick={handleAddOrEditEvent}>
          Adicionar
        </C.ButtonAdd>
        ) : (
          <C.ButtonEdit onClick={handleAddOrEditEvent}>
          Editar
        </C.ButtonEdit>
        )}
        {selectedItem !== null && (
          <C.ButtonDelete onClick={handleDeleteEvent}>
            <FaTrashAlt />
          </C.ButtonDelete>
        )}
      </C.InputLabel>
    </C.Container>
  );
};
