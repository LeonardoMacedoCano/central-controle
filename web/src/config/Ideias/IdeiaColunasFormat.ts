type IdeiaColunasFormatArgs = {
    categoriaMap: Record<number, string>;
  };
  
  type IdeiaColunasFormatReturnType = {
    id: (value: string | number | boolean | Date) => React.ReactNode;
    titulo: (value: string | number | boolean | Date) => React.ReactNode;
    descricao: (value: string | number | boolean | Date) => React.ReactNode;
    dataPrazo: (value: string | number | boolean | Date) => React.ReactNode;
    finalizado: (value: string | number | boolean | Date) => React.ReactNode;
    idCategoria: (value: string | number | boolean | Date) => React.ReactNode;
  };
  
  const isDate = (value: any): value is Date => value instanceof Date;
  
  export const IdeiaColunasFormat = ({ categoriaMap }: IdeiaColunasFormatArgs): IdeiaColunasFormatReturnType => ({
    id: (value: string | number | boolean | Date): React.ReactNode => Number(value),
    titulo: (value: string | number | boolean | Date): React.ReactNode => String(value),
    descricao: (value: string | number | boolean | Date): React.ReactNode => String(value),
    dataPrazo: (value: string | number | boolean | Date): React.ReactNode => {
      if (typeof value === 'string') {
        return new Date(value).toLocaleDateString();
      } else {
        return String(value);
      }
    },
    finalizado: (value: string | number | boolean | Date): React.ReactNode => {
      if (typeof value === 'boolean') {
        return value ? 'Sim' : 'Não';
      } else {
        return String(value);
      }
    },
    idCategoria: (value: string | number | boolean | Date): React.ReactNode => {
      if (isDate(value)) {
        return categoriaMap[value.valueOf()] || 'Categoria Desconhecida';
      }
      const categoryId = typeof value === 'number' ? value : Number(value);
      return categoriaMap[categoryId] || 'Categoria Desconhecida';
    },
  });
  