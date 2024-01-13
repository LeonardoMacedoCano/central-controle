import React, { useState, useEffect } from 'react';
import * as C from './styles';
import { FaTrashAlt } from 'react-icons/fa';
import { Categoria } from '../../types/Categoria';
import { InputField } from '../../types/InputField';
import { 
  FormFields, 
  ValoresIniciaisForm, 
  isFormFieldsDespesa, 
  isFormFieldsTarefa, 
} from '../../types/FormFields';

type Props<T> = {
  campos: InputField[];
  onAdd: (data: FormFields) => void;
  onEdit: (data: FormFields) => void;
  onDelete: () => void;
  itemSelecionado: number | null;
  opcoesCategoria: Categoria[];
  valoresIniciais?: T;
};

export const InputArea: React.FC<Props<FormFields>> = ({
  campos,
  onAdd,
  onEdit,
  onDelete,
  itemSelecionado,
  opcoesCategoria,
  valoresIniciais,
}) => {
  const [formFields, setFormFields] = useState<FormFields>(ValoresIniciaisForm);

  useEffect(() => {
    setFormFields(valoresIniciais || ValoresIniciaisForm);
  }, [valoresIniciais]);

  const clearFields = () => {
    setFormFields(ValoresIniciaisForm);
  };

  const handleInputChange = (key: keyof FormFields, value: string | number) => {
    setFormFields((prevFields) => ({ ...prevFields, [key as string]: value }));
  };

  const handleDeleteEvent = () => {
    if (itemSelecionado !== null) {
      clearFields();
      onDelete();
    }
  };
  
  const handleAddOrEditEvent = () => {
    const errors: string[] = campos
      .filter(({ key }) => !formFields[key as keyof FormFields])
      .map(({ label, key }) => {
        if (key === 'valor' && isFormFieldsDespesa(formFields) && 'valor' in formFields && (formFields.valor as number) === 0) {
          return `${label} vazio!`;
        } else if (key === 'data' && isFormFieldsTarefa(formFields) && 'data' in formFields) {
          const dateValue = formFields.data as string;
          if (isNaN(new Date(dateValue).getTime())) {
            return 'Data inválida!';
          }
        }
        return '';
      })
      .filter((error) => error !== '');
  
    if (errors.length > 0) {
      alert(errors.join('\n'));
    } else {
      itemSelecionado === null ? onAdd(formFields) : onEdit(formFields);
      clearFields();
    }
  };
  
  return (
    <C.Container>
      {campos.map(({ label, type, key }) => {
        const fieldValue = formFields[key as keyof FormFields];
        return (
          <C.InputLabel key={key as keyof FormFields}>
            <C.InputTitle>{label}</C.InputTitle>
            {type === 'select' ? (
              <C.Select
                value={fieldValue as string}
                onChange={(e) => handleInputChange(key as keyof FormFields, e.target.value)}
              >
                <option value="">Selecione...</option>
                {opcoesCategoria.map((categoria) => (
                  <option key={categoria.id} value={categoria.descricao}>
                    {categoria.descricao}
                  </option>
                ))}
              </C.Select>
            ) : type === 'boolean' ? (
              <C.Select
                value={fieldValue as string}
                onChange={(e) => handleInputChange(key as keyof FormFields, e.target.value)}
              >
                <option value="">False</option>
                <option key={'true'} value={'true'}>
                  True
                </option>
              </C.Select>
            ) : (  
              <C.Input
                type={type}
                value={fieldValue}
                onChange={(e) => handleInputChange(key as keyof FormFields, e.target.value)}
              />
            )}
          </C.InputLabel>
        );
      })}

      <C.InputLabel>
        <C.InputTitle>&nbsp;</C.InputTitle>
        {itemSelecionado === null ? (
          <C.ButtonAdd onClick={handleAddOrEditEvent}>Adicionar</C.ButtonAdd>
        ) : (
          <C.ButtonEdit onClick={handleAddOrEditEvent}>Editar</C.ButtonEdit>
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
