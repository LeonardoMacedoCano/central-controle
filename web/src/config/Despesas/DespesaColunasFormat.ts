type DespesaColunasFormatArgs = {
  categoriaMap: Record<number, string>;
};

export const DespesaColunasFormat = ({ categoriaMap }: DespesaColunasFormatArgs) => ({
  id: (value: string | number | Date) => Number(value),
  descricao: (value: string | number | Date) => String(value),
  valor: (value: string | number | Date) => Number(value).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }),
  data: (value: string | number | Date) => new Date(value).toLocaleDateString(),
  idCategoria: (value: string | number | Date) => {
    const categoryId = typeof value === 'number' ? value : Number(value);
    return categoriaMap[categoryId] || 'Categoria Desconhecida';
  },
});