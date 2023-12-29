import * as C from './styles';

type Props<T extends Record<string, any>> = {
  item: T;
};

export function TableItem<T extends Record<string, any>>({ item }: Props<T>) {
  const columns = Object.keys(item);

  return (
    <C.TableLine>
      {columns.map((column, index) => (
        <C.TableColumn key={index}>{item[column]}</C.TableColumn>
      ))}
    </C.TableLine>
  );
}
