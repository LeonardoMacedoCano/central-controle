import React, { useState, useEffect } from 'react';
import * as C from './styles';
import { FormFields } from '../../types/FormFields';
import { InputAreaProps } from '../../types/InputAreaProps';

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

  const handleAddEvent = () => {
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

  const handleDeleteEvent = () => {
    if (selectedItem !== null) {
      onDelete();
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
        <C.Button onClick={handleAddEvent}>
          {selectedItem === null ? 'Adicionar' : 'Editar'}
        </C.Button>
        {selectedItem !== null && (
          <C.Button onClick={handleDeleteEvent}>
            Deletar
          </C.Button>
        )}
      </C.InputLabel>
    </C.Container>
  );
};
