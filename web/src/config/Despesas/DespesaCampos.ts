import { InputField } from '../../types/InputField';

export const DespesaCampos : InputField[] = [
  { 
    label: 'Categoria', 
    type: 'select' as const, 
    key: 'categoria' as const,
    required: true 
  },
  { 
    label: 'Descricao', 
    type: 'text' as const, 
    key: 'descricao' as const,
    required: true 
  },
  { 
    label: 'Valor', 
    type: 'number' as const, 
    key: 'valor' as const,
    required: true  
  },
  { 
    label: 'Data', 
    type: 'date' as const, 
    key: 'data' as const,
    required: true  
  },
];