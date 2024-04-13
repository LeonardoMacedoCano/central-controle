import styled from 'styled-components';

interface SearchPaginationProps {
  height?: string;
  width?: string;
}

export const SearchPagination = styled.div<SearchPaginationProps>`
  width: ${props => props.width || '100%'};
  height: ${props => props.height || '100%'};
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const ItemContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const Item = styled.li<{ disabled?: boolean}>`
  width: 35px;
  height: 100%;
  font-size: 20px;
  color: ${props => props.theme.colors.white};
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;

  &:first-child {
    margin-left: 0;
  }

  &:hover {
    color: ${props => props.theme.colors.gray};
  }

  ${props =>
    props.disabled &&
    `
      opacity: 0.2;
      cursor: not-allowed;
    `}
`;
