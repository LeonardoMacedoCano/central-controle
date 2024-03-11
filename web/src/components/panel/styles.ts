import styled from 'styled-components';

interface PanelProps {
  width?: string;
  maxWidth?: string;
  margin?: string;
}

export const Panel = styled.div<PanelProps>`
  width: ${props => props.width || '100%'};
  max-width: ${props => props.maxWidth || 'none'};
  margin: ${props => props.margin || 'auto'};
  padding: 10px 0;
`;

export const Title = styled.div`
  h2 {
    font-weight: bold;
    margin-bottom: 5px;
    color: ${props => props.theme.colors.white};
  }
`;

export const TitleLine = styled.div`
  width: 100%;
  height: 2px;
  background-color: ${props => props.theme.colors.gray};
`;

export const Body = styled.div`
  width: 100%;
  background-color: ${props => props.theme.colors.white};
  padding: 5px;
  box-shadow: 0px 0px 5px ${props => props.theme.colors.gray};
  border-radius: 5px;
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const Footer = styled.div`
`;
