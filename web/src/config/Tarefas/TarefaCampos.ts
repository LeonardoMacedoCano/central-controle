import { InputField } from '../../types/InputField';

const TarefaCampos: InputField[] = [
  { label: 'Categoria', type: 'select', key: 'categoria' },
  { label: 'Título', type: 'text', key: 'titulo' },
  { label: 'Descrição', type: 'text', key: 'descricao' },
  { label: 'Data Inclusão', type: 'date', key: 'dataInclusao' },
  { label: 'Data Prazo', type: 'date' as const, key: 'dataPrazo' as const },
  { label: 'Finalizado', type: 'boolean' as const, key: 'finalizado' as const },
];

export { TarefaCampos };