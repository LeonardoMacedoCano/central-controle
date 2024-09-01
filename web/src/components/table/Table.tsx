import React, { ReactNode, useState, FC } from 'react';
import styled from 'styled-components';
import Container from '../container/Container';
import SearchPagination from '../pagination/SearchPagination';
import { PagedResponse } from '../../types/PagedResponse';
import Button from '../button/button/Button';
import { FaEdit, FaEye, FaTrash } from 'react-icons/fa';

type ColumnProps<T> = {
  header: ReactNode;
  value(value: T, index: number): ReactNode;
};

export const Column = <T extends any>({}: ColumnProps<T>) => {
  return null;
};

interface TableActionsProps {
  onView?: () => void;
  onEdit?: () => void;
  onDelete?: () => void;
  visible: boolean;
}

const TableActions: FC<TableActionsProps> = ({ onView, onEdit, onDelete, visible }) => {
  const commonButtonStyles = {
    borderRadius: '50%',
    justifyContent: 'center',
    alignItems: 'center',
    display: 'flex',
    height: '25px',
    width: '25px',
  };

  return (
    <ActionsContainer>
      <ActionsWrapper visible={visible}>
        {onView && (
          <Button 
            onClick={onView}
            variant='success'
            icon={<FaEye />}
            hint='Visualizar'
            style={commonButtonStyles}
          />
        )}
        {onEdit && (
          <Button 
            variant='info'
            icon={<FaEdit />}
            onClick={onEdit} 
            style={commonButtonStyles}
          />
        )}
        {onDelete && (
          <Button 
            variant='warning'
            icon={<FaTrash />} 
            onClick={onDelete} 
            style={commonButtonStyles}
          />
        )}
      </ActionsWrapper>
    </ActionsContainer>
  );
};

interface TableProps<T> {
  values: T[] | PagedResponse<T>;
  columns: ReactNode[];
  messageEmpty?: string;
  keyExtractor(item: T, index?: number): string | number;
  onClickRow?(item: T, index?: number): void;
  rowSelected?(item: T): boolean;
  customHeader?: React.ReactNode;
  loadPage?: (pageIndex: number, pageSize: number) => void;
  onView?: (item: T) => void;
  onEdit?: (item: T) => void;
  onDelete?: (item: T) => void;
}

interface Indexable {
  [key: string]: any;
}

const getValues = (values: any[] | PagedResponse<any>): any[] => {
  if ('content' in values) {
    return (values as PagedResponse<any>).content || [];
  }
  return values as any[];
};

export const Table = <T extends Indexable>({
  values,
  columns,
  messageEmpty,
  keyExtractor,
  onClickRow,
  rowSelected,
  customHeader,
  loadPage,
  onView,
  onEdit,
  onDelete,
}: TableProps<T>) => {
  const [hoveredRowIndex, setHoveredRowIndex] = useState<number | null>(null);

  const renderTableHead = () => (
    <thead>
      <TableHeadRow>
        {columns.map((column, index) => {
          if (React.isValidElement(column)) {
            const columnProps = column.props as ColumnProps<T>;
            return (
              <TableHeadColumn key={index}>
                <TableColumnTitle>{columnProps.header}</TableColumnTitle>
              </TableHeadColumn>
            );
          }
          return null;
        })}
      </TableHeadRow>
    </thead>
  );

  const renderTableBody = () => {
    const data = getValues(values);
    return (
      <tbody>
        {data.length === 0 ? (
          <tr>
            <td colSpan={columns.length + 1}>
              <EmptyMessage>{messageEmpty}</EmptyMessage>
            </td>
          </tr>
        ) : (
          data.map((item, index) => (
            <TableRow
              key={keyExtractor(item, index)}
              onClick={() => onClickRow && onClickRow(item, index)}
              onMouseEnter={() => setHoveredRowIndex(index)}
              onMouseLeave={() => setHoveredRowIndex(null)}
            >
              {columns.map((column, columnIndex) => {
                if (React.isValidElement(column)) {
                  const columnProps = column.props as ColumnProps<T>;
                  return (
                    <TableColumn key={columnIndex} isSelected={rowSelected ? rowSelected(item) : false}>
                      <TruncatedContent>{columnProps.value(item, index)}</TruncatedContent>
                    </TableColumn>
                  );
                }
                return null;
              })}
              <ActionColumn visible={hoveredRowIndex === index}>
                <TableActions
                  onView={onView ? () => onView(item) : undefined}
                  onEdit={onEdit ? () => onEdit(item) : undefined}
                  onDelete={onDelete ? () => onDelete(item) : undefined}
                  visible={hoveredRowIndex === index}
                />
              </ActionColumn>
            </TableRow>
          ))
        )}
      </tbody>
    );
  };

  const renderPagination = () => {
    if (loadPage && 'content' in values) {
      return (
        <SearchPagination
          height='35px'
          page={values}
          loadPage={loadPage}
        />
      );
    }
    return null;
  };

  return (
    <Container backgroundColor="transparent" width="100%">
      {customHeader && <CustomHeader>{customHeader}</CustomHeader>}
      {getValues(values).length === 0 ? (
        <EmptyMessage>{messageEmpty}</EmptyMessage>
      ) : (
        <StyledTable>
          {renderTableHead()}
          {renderTableBody()}
        </StyledTable>
      )}
      {renderPagination()}
    </Container>
  );
};

export default Table;

const CustomHeader = styled.div`
  width: 100%;
  height: 45px;
  display: flex;
  align-items: center;
  border-bottom: 2px solid ${({ theme }) => theme.colors.quaternary};
`;

const StyledTable = styled.table`
  width: 100%;
  border-collapse: collapse;
`;

const TableHeadRow = styled.tr`
  width: 100%;
  border-bottom: 2px solid ${({ theme }) => theme.colors.quaternary};
`;

const TableHeadColumn = styled.th`
  padding: 0 10px;
  text-align: left;
  background-color: ${({ theme }) => theme.colors.secondary};
  border-left: 1px solid ${({ theme }) => theme.colors.quaternary};

  &:first-child {
    border-left: none;
  }
`;

const TableColumnTitle = styled.div`
  height: 40px;
  text-align: left;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  color: ${({ theme }) => theme.colors.quaternary};
`;

const TableRow = styled.tr<{ isSelected?: boolean }>`
  background-color: ${({ theme }) => theme.colors.secondary};
  position: relative;
  
  &:nth-child(odd) {
    background-color: ${({ theme }) => theme.colors.tertiary};
  }
  &:last-child {
    border-bottom: 1px solid ${({ theme }) => theme.colors.quaternary};
  }
`;

const TableColumn = styled.td<{ isSelected?: boolean }>`
  font-size: 13px; /* Adiciona ou ajusta o tamanho da fonte aqui */
  height: 35px;
  padding: 0 2px; /* Ajusta o padding para criar mais espaço */
  text-align: left;
  border-left: 1px solid ${({ theme }) => theme.colors.quaternary};
  position: relative;
  max-width: 50px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

  &:first-child::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    width: 5px;
    background-color: ${({ theme, isSelected }) => (isSelected ? theme.colors.quaternary : 'transparent')};
  }

  &:first-child {
    border-left: none;
  }
`;

const TruncatedContent = styled.div`
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const ActionColumn = styled.td<{ visible: boolean }>`
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  padding: 0;
  border-left: none;
  z-index: 1;
  background-color: ${({ theme }) => theme.colors.secondary};
  display: ${props => (props.visible ? 'flex' : 'none')};
  align-items: center;
  justify-content: center;
`;

const EmptyMessage = styled.div`
  padding: 10px;
`;

const ActionsContainer = styled.div`
  position: relative;
  height: 100%;
`;

const ActionsWrapper = styled.div<{ visible: boolean }>`
  position: absolute;
  top: 0;
  right: 5px;
  bottom: 0;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 8px;
  opacity: ${props => (props.visible ? 1 : 0)};
  pointer-events: ${props => (props.visible ? 'auto' : 'none')};
  transition: opacity 0.2s ease-in-out;
`;
