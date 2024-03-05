import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  align-items: center;
  background-color: ${props => props.theme.colors.primary};
  border: 1px solid ${props => props.theme.colors.gray};
  font-size: 20px;
  color: ${props => props.theme.colors.white};
  padding: 10px;
  cursor: pointer;
  border-radius: 10px;
  margin: 0 15px 20px;

  > svg {
    margin: 0 20px;
  }

  &:hover {
    color: ${props => props.theme.colors.gray};
  }
`;