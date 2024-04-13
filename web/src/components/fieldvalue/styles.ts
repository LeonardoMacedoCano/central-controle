import styled from 'styled-components';

interface FieldValueProps {
  width?: string;
  maxWidth?: string;
  maxHeight?: string;
  inline?: boolean;
  padding?: string;
};

export const FieldValue = styled.div<FieldValueProps>`
  width: ${props => props.width || '100%'};
  max-width: ${props => props.maxWidth || 'none'};
  max-height: ${props => props.maxHeight || 'none'};
  height: 100%;
  padding: ${props => props.padding || '5px'};
  display: flex;
  flex-direction: ${props => props.inline ? 'row' : 'column'};
  align-items: ${props => props.inline ? 'center' : 'stretch'};
`;

export const Label = styled.span`
  color: ${props => props.theme.colors.white};
  font-weight: bold;
  font-size: 15px;
  height: 100%;
  display: flex;
  align-items: center;
`;

interface InputProps {
  inputWidth?: string;
  inline?: boolean;
};

export const Input = styled.input<InputProps>`
  color: ${props => props.theme.colors.quaternary};
  width: ${props => props.inputWidth || '100%'};
  font-size: 15px;
  height: 100%;
  outline: none;
  background-color: transparent;
  margin-left: ${props => props.inline ? '5px' : 'none'};
  cursor: ${props => (props.readOnly ? 'not-allowed' : 'pointer')};
`;

export const Select = styled.select<InputProps>`
  color: ${props => props.theme.colors.quaternary};
  width: ${props => props.inputWidth || '100%'};
  font-size: 15px;
  height: 100%;
  outline: none;
  background-color: transparent;
  margin-left: ${props => props.inline ? '5px' : 'none'};
`;

export const Icon = styled.div`
  height: 100%;
  width: auto;
`;
