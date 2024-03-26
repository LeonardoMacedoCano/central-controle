import styled from 'styled-components';

export const SearchPagination = styled.div`
  width: 100%;
  padding: 10px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const ItemContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

export const Item = styled.li<{ disabled?: boolean}>`
  width: 25px;
  height: 25px;
  font-size: 20px;
  color: ${props => props.theme.colors.black};
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
