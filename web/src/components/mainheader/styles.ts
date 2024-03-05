import styled from 'styled-components';

export const Container = styled.div`
  height: 100px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: ${props => props.theme.colors.secondary};
  border-bottom: 1px solid ${props => props.theme.colors.gray};
  box-shadow: 0 0 20px 3px;
  padding: 0 20px;

  > svg {
    color: ${props => props.theme.colors.white};
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
`;

export const Title = styled.h1`
  color: ${props => props.theme.colors.white};
  font-size: 24px;
  margin: 0;
`;