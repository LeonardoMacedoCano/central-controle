import styled from 'styled-components';

interface FieldValueProps {
  width?: string;
  maxWidth?: string;
  margin?: string;
}

export const FieldValue = styled.div<FieldValueProps>`
  width: ${props => props.width || '100%'};
  max-width: ${props => props.maxWidth || 'none'};
  margin: ${props => props.margin || '5px'};
  border: 1px solid ${props => props.theme.colors.tertiary};
  border-radius: 5px;
  height: 45px;
  padding: 1px;
  display: flex;
  flex-direction: column;
`;

export const Label = styled.span`
  color: ${props => props.theme.colors.primary};
  font-weight: bold;
  font-size: 15px;
  height: 100%;
  padding: 0 5px;
`;

export const Input = styled.input`
  color: ${props => props.theme.colors.tertiary};
  font-size: 15px;
  height: 100%;
  outline: none;
  padding: 0 5px;
  border: none;
`;
