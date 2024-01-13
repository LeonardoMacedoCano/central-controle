type TarefaColunasFormatArgs = {
  categoriaMap: Record<number, string>;
};

type TarefaColunasFormatReturnType = {
  id: (value: string | number | boolean | Date) => React.ReactNode;
  titulo: (value: string | number | boolean | Date) => React.ReactNode;
  descricao: (value: string | number | boolean | Date) => React.ReactNode;
  dataInclusao: (value: string | number | boolean | Date) => React.ReactNode;
  dataPrazo: (value: string | number | boolean | Date) => React.ReactNode;
  Finalizado: (value: string | number | boolean | Date) => React.ReactNode;
  idCategoria: (value: string | number | boolean | Date) => React.ReactNode;
};

const isDate = (value: any): value is Date => value instanceof Date;

export const TarefaColunasFormat = ({ categoriaMap }: TarefaColunasFormatArgs): TarefaColunasFormatReturnType => ({
  id: (value: string | number | boolean | Date): React.ReactNode => Number(value),
  titulo: (value: string | number | boolean | Date): React.ReactNode => String(value),
  descricao: (value: string | number | boolean | Date): React.ReactNode => String(value),
  dataInclusao: (value: string | number | boolean | Date): React.ReactNode => {
    if (typeof value === 'string') {
      return new Date(value).toLocaleDateString();
    } else if (typeof value === 'number') {
      return String(value);
    } else if (typeof value === 'boolean') {
      return value;
    } else if (value instanceof Date) {
      return value.toLocaleDateString();
    }
    return null;
  },
  dataPrazo: (value: string | number | boolean | Date): React.ReactNode => {
    if (typeof value === 'string') {
      return new Date(value).toLocaleDateString();
    } else if (typeof value === 'number') {
      return String(value);
    } else if (typeof value === 'boolean') {
      return value ? 'Sim' : 'Não';
    } else if (value instanceof Date) {
      return value.toLocaleDateString();
    }
    return null;
  },
  Finalizado: (value: string | number | boolean | Date): React.ReactNode => Boolean(value),
  idCategoria: (value: string | number | boolean | Date): React.ReactNode => {
    if (isDate(value)) {
      return categoriaMap[value.valueOf()] || 'Categoria Desconhecida';
    }
    const categoryId = typeof value === 'number' ? value : Number(value);
    return categoriaMap[categoryId] || 'Categoria Desconhecida';
  },
});
