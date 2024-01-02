import * as C from './styles';
import { TableItem } from '../TableItem';

type Props<T extends Record<string, any>> = {
  lista: T[];
  colunasConfig?: Partial<Record<keyof T, { label: string; width?: number }>>;
  colunasFormat?: Partial<Record<keyof T, (value: T[keyof T]) => React.ReactNode>>;
  onEditClick?: (itemId: number | null) => void; 
  itemIdSelecionado?: number | null;
};

export function TableArea<T extends Record<string, any>>({
  lista,
  colunasConfig = {},
  colunasFormat = {},
  onEditClick,
  itemIdSelecionado,
}: Props<T>) {
  if (!lista.length) {
    return null;
  }

  const columns = Object.keys(lista[0]);

  const handleItemClick = (itemId: number) => {
    if (itemIdSelecionado === itemId) {
      onEditClick && onEditClick(null);
    } else {
      onEditClick && onEditClick(itemId);
    }
  };

  return (
    <C.Table>
      <thead>
        <tr>
          {columns.map((column, index) => (
            <C.TableHeadColumn key={index} style={{ width: colunasConfig[column]?.width }}>
              {colunasConfig[column]?.label || column}
            </C.TableHeadColumn>
          ))}
        </tr>
      </thead>
      <tbody>
        {lista.map((item, index) => (
          <TableItem
            key={index}
            item={item}
            colunasFormat={colunasFormat}
            isSelecionado={item.id === itemIdSelecionado}
            onClick={() => handleItemClick(item.id)}
          />
        ))}
      </tbody>
    </C.Table>
  );
}