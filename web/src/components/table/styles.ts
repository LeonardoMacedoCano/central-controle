import styled from 'styled-components';

export const TableContainer = styled.div`
  width: 100%;
  background-color: ${props => props.theme.colors.white};
  padding: 5px;
  box-shadow: 0px 0px 5px ${props => props.theme.colors.gray};
  border-radius: 10px;
  margin-top: 20px;
`;

export const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
`;

export const TableHeadColumn = styled.th<{ width?: number }>`
  width: ${props => props.width ? `${props.width}px` : 'auto'};
  padding: 10px;
  text-align: left;
`;

export const TableSeparatorRow = styled.tr`
  height: 1px;
  background-color: ${props => props.theme.colors.tertiary}; 
`;

export const TableSeparatorCell = styled.td`
  border: none;
  height: 1px;
  padding: 0;
  margin: 0;
`;

export const TableColumnTitle = styled.div`
  display: flex;
  align-items: center;
  height: 20px;
`;

export const TableRow = styled.tr<{ isSelected?: boolean }>`
  background-color: ${props => (props.isSelected ? props.theme.colors.tertiary : 'transparent')};
  color: ${props => (props.isSelected ? props.theme.colors.white : props.theme.colors.black)};
`;

export const TableColumn = styled.td`
  padding: 10px;
`;

export const EmptyMessage = styled.div`
  padding: 10px;
`;