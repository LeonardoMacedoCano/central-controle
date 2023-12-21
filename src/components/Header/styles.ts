import styled from 'styled-components';

export const Container = styled.div`
  height: 100px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #1A202C; 
  box-shadow: 0 0 20px 3px;
  padding: 0 20px;

  > svg {
    color: white;
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
`;

export const Title = styled.h1`
  color: white;
  font-size: 24px;
  margin: 0;
`;