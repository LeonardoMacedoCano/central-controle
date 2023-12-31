import * as C from './styles';

type Props<T extends Record<string, any>> = {
  item: T;
  columnFormatters?: Partial<Record<keyof T, (value: T[keyof T]) => React.ReactNode>>;
  isSelected?: boolean;
  onClick?: () => void;
};

export function TableItem<T extends Record<string, any>>({
  item,
  columnFormatters = {},
  isSelected = false,
  onClick,
}: Props<T>) {
  const columns = Object.keys(item);

  return (
    <C.TableLine isSelected={isSelected} onClick={onClick}>
      {columns.map((column, index) => (
        <C.TableColumn key={index}>
          {columnFormatters[column as keyof T]
            ? columnFormatters[column as keyof T]!(item[column])
            : item[column]}
        </C.TableColumn>
      ))}
    </C.TableLine>
  );
}
