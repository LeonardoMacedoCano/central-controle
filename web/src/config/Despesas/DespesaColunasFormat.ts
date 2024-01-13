type DespesaColunasFormatArgs = {
  categoriaMap: Record<number, string>;
};

type DespesaColunasFormatReturnType = {
  id: (value: string | number | boolean | Date) => React.ReactNode;
  descricao: (value: string | number | boolean | Date) => React.ReactNode;
  valor: (value: string | number | boolean | Date) => React.ReactNode;
  data: (value: string | number | boolean | Date) => React.ReactNode;
  idCategoria: (value: string | number | boolean | Date) => React.ReactNode;
};

export const DespesaColunasFormat = ({ categoriaMap }: DespesaColunasFormatArgs): DespesaColunasFormatReturnType => {
  const isDate = (value: any): value is Date => value instanceof Date;

  return {
    id: (value: string | number | boolean | Date): React.ReactNode => Number(value),
    descricao: (value: string | number | boolean | Date): React.ReactNode => String(value),
    valor: (value: string | number | boolean | Date): React.ReactNode => {
      if (typeof value === 'number') {
        return Number(value).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
      }
      return null;
    },
    data: (value: string | number | boolean | Date): React.ReactNode => {
      if (typeof value === 'string') {
        return new Date(value).toLocaleDateString();
      } else if (value instanceof Date) {
        return value.toLocaleDateString();
      }
      return null;
    },
    idCategoria: (value: string | number | boolean | Date): React.ReactNode => {
      if (isDate(value)) {
        return categoriaMap[value.valueOf()] || 'Categoria Desconhecida';
      }
      const categoryId = typeof value === 'number' ? value : Number(value);
      return categoriaMap[categoryId] || 'Categoria Desconhecida';
    },
  };
};
