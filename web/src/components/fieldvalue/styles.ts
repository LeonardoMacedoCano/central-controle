import styled from 'styled-components';

interface FieldValueProps {
  width?: string;
  maxWidth?: string;
  maxHeight?: string;
  inline?: boolean;
};

export const FieldValue = styled.div<FieldValueProps>`
  width: ${props => props.width || '100%'};
  max-width: ${props => props.maxWidth || 'none'};
  max-height: ${props => props.maxHeight || 'none'};
  margin: '5px';
  border: 1px solid ${props => props.theme.colors.tertiary};
  border-radius: 5px;
  height: ${props => props.inline ? 'auto' : '45px'};
  padding: 1px;
  display: flex;
  flex-direction: ${props => props.inline ? 'row' : 'column'};
  align-items: ${props => props.inline ? 'center' : 'stretch'};
`;

export const Label = styled.span`
  color: ${props => props.theme.colors.primary};
  font-weight: bold;
  font-size: 15px;
  height: 100%;
  padding: 0 5px;
`;

interface InputProps {
  inputWidth?: string;
};

export const Input = styled.input<InputProps>`
  color: ${props => props.theme.colors.tertiary};
  width: ${props => props.inputWidth || '100%'};
  font-size: 15px;
  height: 100%;
  outline: none;
  padding: 0 5px;
  border: none;
`;
