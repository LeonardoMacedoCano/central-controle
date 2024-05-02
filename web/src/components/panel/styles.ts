import styled from 'styled-components';

interface PanelProps {
  width?: string;
  maxWidth?: string;
  margin?: string;
}

export const Panel = styled.div<PanelProps>`
  width: ${({ width }) => width || '100%'};
  max-width: ${({ maxWidth }) => maxWidth || 'none'};
  margin: ${({ margin }) => margin || 'auto'};
  padding: 10px 0;
`;

export const Title = styled.div`
  h2 {
    font-weight: bold;
    color: ${({ theme }) => theme.colors.white};
    border-bottom: 2px solid ${({ theme }) => theme.colors.gray};
  }
`;

export const PanelContainer = styled.div`
  width: 100%;
  background-color: ${({ theme }) => theme.colors.secondary};
  box-shadow: 0px 0px 5px ${({ theme }) => theme.colors.gray};
  border-radius: 5px;
  margin-top: 20px;
`;

const BaseBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Body = styled(BaseBox)`
  justify-content: space-between;
`;

export const Footer = styled(BaseBox)`
  height: 35px;
  justify-content: center;
`;
