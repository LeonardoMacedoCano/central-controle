import * as C from './styles';

type Props<T extends Record<string, any>> = {
  item: T;
  colunasFormat?: Partial<Record<keyof T, (value: T[keyof T]) => React.ReactNode>>;
  isSelecionado?: boolean;
  onClick?: () => void;
};

export function TableItem<T extends Record<string, any>>({
  item,
  colunasFormat = {},
  isSelecionado = false,
  onClick,
}: Props<T>) {
  const columns = Object.keys(item);

  return (
    <C.TableLine isSelected={isSelecionado} onClick={onClick}>
      {columns.map((column, index) => (
        <C.TableColumn key={index}>
          {colunasFormat[column as keyof T]
            ? colunasFormat[column as keyof T]!(item[column])
            : item[column]}
        </C.TableColumn>
      ))}
    </C.TableLine>
  );
}
