import * as C from './styles';
import { TableItem } from '../TableItem';

type Props<T extends Record<string, any>> = {
  list: T[];
  columnNames?: Partial<Record<keyof T, { label: string; width?: number }>>; // Updated type
  columnFormatters?: Partial<Record<keyof T, (value: T[keyof T]) => React.ReactNode>>;
};

export function TableArea<T extends Record<string, any>>({ list, columnNames = {}, columnFormatters = {} }: Props<T>) {
  if (!list.length) {
    return null;
  }

  const columns = Object.keys(list[0]);

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
          <TableItem key={index} item={item} columnFormatters={columnFormatters} />
        ))}
      </tbody>
    </C.Table>
  );
}
