import * as C from './styles';
import { TableItem } from '../TableItem';

type Props<T extends Record<string, any>> = {
  list: T[];
};

export function TableArea<T extends Record<string, any>>({ list }: Props<T>) {
  if (!list.length) {
    return null; 
  }

  const columns = Object.keys(list[0]);

  return (
    <C.Table>
      <thead>
        <tr>
          {columns.map((column, index) => (
            <C.TableHeadColumn key={index}>{column}</C.TableHeadColumn>
          ))}
        </tr>
      </thead>
      <tbody>
        {list.map((item, index) => (
          <TableItem key={index} item={item} />
        ))}
      </tbody>
    </C.Table>
  );
}
