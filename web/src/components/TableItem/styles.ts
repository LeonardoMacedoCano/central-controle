import styled from 'styled-components';

export const TableLine = styled.tr<{ isSelected?: boolean }>`
  background-color: ${props => (props.isSelected ? props.theme.colors.tertiary : 'transparent')};
  color: ${props => (props.isSelected ? props.theme.colors.white : props.theme.colors.black)};
`;

export const TableColumn = styled.td`
    padding: 10px 0;
`;