import * as C from './styles';
import { TableItem } from '../TableItem';

type Props<T extends Record<string, any>> = {
  list: T[];
  columnNames?: Partial<Record<keyof T, { label: string; width?: number }>>;
  columnFormatters?: Partial<Record<keyof T, (value: T[keyof T]) => React.ReactNode>>;
  onEditClick?: (itemId: number | null) => void; 
  selectedItemId?: number | null;
};

export function TableArea<T extends Record<string, any>>({
  list,
  columnNames = {},
  columnFormatters = {},
  onEditClick,
  selectedItemId,
}: Props<T>) {
  if (!list.length) {
    return null;
  }

  const columns = Object.keys(list[0]);

  const handleItemClick = (itemId: number) => {
    if (selectedItemId === itemId) {
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
            <C.TableHeadColumn key={index} style={{ width: columnNames[column]?.width }}>
              {columnNames[column]?.label || column}
            </C.TableHeadColumn>
          ))}
        </tr>
      </thead>
      <tbody>
        {list.map((item, index) => (
          <TableItem
            key={index}
            item={item}
            columnFormatters={columnFormatters}
            isSelected={item.id === selectedItemId}
            onClick={() => handleItemClick(item.id)}
          />
        ))}
      </tbody>
    </C.Table>
  );
}