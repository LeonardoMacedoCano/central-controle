import { InputField } from '../types/InputField';
import { FormFields } from '../types/FormFields';
import { Categoria } from '../types/Categoria';

export type InputAreaProps = {
    inputFields: InputField[];
    onAdd: (data: FormFields) => void;
    onEdit: (data: FormFields) => void;
    onDelete: () => void;
    selectedItem: number | null;
    categoriaOptions: Categoria[];
    initialValues?: FormFields;
  };