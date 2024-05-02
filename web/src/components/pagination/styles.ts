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
  color: ${props => props.theme.colors.quaternary};
`;

interface ItemProps {
  disabled?: boolean;
}

export const Item = styled.li<ItemProps>`
  width: 35px;
  height: 100%;
  font-size: 20px;
  color: ${props => props.theme.colors.white};
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: ${props => (props.disabled ? 'not-allowed' : 'pointer')};
  opacity: ${props => (props.disabled ? '0.2' : '1')};

  &:first-child {
    margin-left: 0;
  }

  &:hover {
    color: ${props => (props.disabled ? props.theme.colors.white : props.theme.colors.gray)};
  }
`;
