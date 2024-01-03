import styled from 'styled-components';

export const Container = styled.div`
    background-color: ${props => props.theme.colors.white};
    box-shadow: 0px 0px 5px ${props => props.theme.colors.gray};
    border-radius: 10px;
    padding: 20px;
    margin-top: 20px;
    display: flex;
    align-items: center;
`;
export const InputLabel = styled.label`
    flex: 1;
    margin: 10px;
`;
export const InputTitle = styled.div`
    font-weight: bold;
    margin-bottom: 5px;
`;
export const Input = styled.input`
    width: 100%;
    height: 30px;
    padding: 0 5px;
    border: 1px solid ${props => props.theme.colors.tertiary};
    border-radius: 5px;
`;
export const Select = styled.select`
    width: 100%;
    height: 30px;
    padding: 0 5px;
    border: 1px solid ${props => props.theme.colors.tertiary};
    border-radius: 5px;
`;
export const ButtonAdd = styled.button`
  background-color: ${props => props.theme.colors.tertiary};
  color: ${props => props.theme.colors.white};
  width: 100%;
  height: 30px;
  padding: 0 5px;
  border: 1px solid ${props => props.theme.colors.white};
  border-radius: 5px;
`;
export const ButtonEdit = styled.button`
  background-color: ${props => props.theme.colors.tertiary};
  color: ${props => props.theme.colors.white};
  width: 75%;
  height: 30px;
  padding: 0 5px;
  border: 1px solid ${props => props.theme.colors.white};
  border-radius: 5px;
`;
export const ButtonDelete = styled.button`
  background-color: ${props => props.theme.colors.tertiary};
  color: ${props => props.theme.colors.white};
  width: 25%;
  height: 30px;
  padding: 0 5px;
  border: 1px solid ${props => props.theme.colors.white};
  border-radius: 5px;
`;