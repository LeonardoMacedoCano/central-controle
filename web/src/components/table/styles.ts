import styled from 'styled-components';

export const TableContainer = styled.div`
  width: 100%;
  overflow-x: auto;
`;

export const CustomHeader = styled.div`
  width: 100%;
  height: 45px;
  display: flex;
  align-items: center;
  border-bottom: 2px solid ${({ theme }) => theme.colors.quaternary};
`;

export const StyledTable = styled.table`
  width: 100%;
  border-collapse: collapse;
`;

export const TableHeadRow = styled.tr`
  width: 100%;
  border-bottom: 2px solid ${({ theme }) => theme.colors.quaternary};
`;

export const TableHeadColumn = styled.th`
  padding: 0 10px;
  text-align: left;
  background-color: ${({ theme }) => theme.colors.secondary};
  border-right: 1px solid ${({ theme }) => theme.colors.quaternary};
  &:last-child {
    border-right: none;
  }
`;

export const TableColumnTitle = styled.div`
  height: 35px;
  text-align: left;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  color: ${({ theme }) => theme.colors.quaternary};
`;

export const TableRow = styled.tr<{ isSelected?: boolean }>`
  background-color: ${({ theme }) => theme.colors.secondary};
  
  &:nth-child(odd) {
    background-color: ${({ theme }) => theme.colors.tertiary};
  }

  &:last-child {
    border-bottom: 2px solid ${({ theme }) => theme.colors.quaternary};
  }
`;

export const TableColumn = styled.td<{ isSelected?: boolean }>`
  height: 35px;
  padding: 0 10px;
  text-align: left;
  border-right: 1px solid ${({ theme }) => theme.colors.quaternary};
  position: relative;
  
  &:first-child::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    width: 5px;
    background-color: ${({ theme, isSelected }) => (isSelected ? theme.colors.quaternary : 'transparent')};
  }

  &:last-child {
    border-right: none;
  }
`;

export const EmptyMessage = styled.div`
  padding: 10px;
`;