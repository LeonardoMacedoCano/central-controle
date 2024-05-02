import React, { ReactNode } from 'react';
import * as C from './styles';
import Button from '../../components/button/button/Button';

type ColumnProps<T> = {
  header: ReactNode;
  value(value: T, index: number): ReactNode;
};

export const Column = <T extends any>({}: ColumnProps<T>) => {
  return null;
};

interface TableToolbarProps {
  handleAdd?: () => void;
  handleEdit?: () => void;
  handleDelete?: () => void;
  handleMoney?: () => void;
  isItemSelected: boolean;
}

export const TableToolbar: React.FC<TableToolbarProps> = ({
  handleAdd,
  handleEdit,
  handleDelete,
  handleMoney,
  isItemSelected,
}) => {
  return (
    <>
      {handleAdd && (
        <Button 
          variant='table-add' 
          onClick={handleAdd} 
          disabled={isItemSelected} 
        />
      )}
      {handleEdit && (
        <Button 
          variant='table-edit' 
          onClick={handleEdit} 
          disabled={!isItemSelected} 
        />
      )}
      {handleDelete && (
        <Button 
          variant='table-delete' 
          onClick={handleDelete} 
          disabled={!isItemSelected} 
        />
      )}
      {handleMoney && (
        <Button 
          variant='table-money' 
          onClick={handleMoney} 
          disabled={!isItemSelected} 
        />
      )}
    </>
  );
};

interface TableProps<T> {
  values: T[];
  columns: ReactNode[];
  messageEmpty?: string;
  keyExtractor(item: T, index?: number): string | number;
  onClickRow(item: T, index?: number): void;
  rowSelected?(item: T): boolean;
  customHeader?: React.ReactNode;
}

interface Indexable {
  [key: string]: any;
}

export const Table = <T extends Indexable>({
  values,
  columns,
  messageEmpty = 'Nenhuma parcela encontrada.',
  keyExtractor,
  onClickRow,
  rowSelected,
  customHeader,
}: TableProps<T>) => {
  const renderTableHead = () => {
    return (
      <thead>
        <C.TableHeadRow>
          {columns.map((column, index) => {
            if (React.isValidElement(column)) {
              const columnProps = column.props as ColumnProps<T>;
              return (
                <C.TableHeadColumn key={index}>
                  <C.TableColumnTitle>
                    {columnProps.header}
                  </C.TableColumnTitle>
                </C.TableHeadColumn>
              );
            }
            return null;
          })}
        </C.TableHeadRow>
      </thead>
    );
  };

  const renderTableBody = () => {
    return (
      <tbody>
        {values.map((item, index) => (
          <C.TableRow
            key={keyExtractor(item, index)}
            onClick={() => onClickRow(item, index)}
          >
            {columns.map((column, columnIndex) => {
              if (React.isValidElement(column)) {
                const columnProps = column.props as ColumnProps<T>;
                return (
                  <C.TableColumn
                    key={columnIndex}
                    isSelected={rowSelected ? rowSelected(item) : false}
                  >
                    {columnProps.value(item, index)}
                  </C.TableColumn>
                );
              }
              return null;
            })}
          </C.TableRow>
        ))}
      </tbody>
    );
  };

  return (
    <C.TableContainer>
      {customHeader && (
        <C.CustomHeader>
          {customHeader}
        </C.CustomHeader>
      )}
      {values.length === 0 ? (
        <C.EmptyMessage>{messageEmpty}</C.EmptyMessage>
      ) : (
        <C.StyledTable>
          {renderTableHead()}
          {renderTableBody()}
        </C.StyledTable>
      )}
    </C.TableContainer>
  );
};
