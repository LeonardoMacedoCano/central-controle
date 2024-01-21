import { InputField } from '../../types/InputField';

const TarefaCampos: InputField[] = [
  { 
    label: 'Categoria', 
    type: 'select', 
    key: 'categoria',
    required: true 
  },
  { 
    label: 'Título', 
    type: 'text', 
    key: 'titulo',
    required: true  
  },
  { 
    label: 'Descrição', 
    type: 'text', 
    key: 'descricao',
    required: true  
  },
  { 
    label: 'Data Prazo', 
    type: 'date' as const, 
    key: 'dataPrazo' as const,
    required: true  
  },
  { 
    label: 'Finalizado', 
    type: 'boolean' as const, 
    key: 'finalizado' as const,
    required: true  
  },
];

export { TarefaCampos };