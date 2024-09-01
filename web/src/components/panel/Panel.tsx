import React, { ReactNode } from 'react';
import styled from 'styled-components';
import Container from '../container/Container';

interface PanelProps {
  title?: ReactNode;
  children: ReactNode;
  footer?: ReactNode;
  width?: string;
  maxWidth?: string;
}

const Panel: React.FC<PanelProps> = ({ title, children, footer, width, maxWidth }) => {
  return (
    <Container
      width={width || '100%'} 
      padding={'10px 0'}
      margin='auto'
      backgroundColor='transparent'
      style={{maxWidth: maxWidth || 'none'}}
    >
      {title && (
        <Title>
          <h2>{title}</h2>
        </Title>
      )}
      <Container
        width={'100%'} 
        variantColor='secondary'
        margin='20px 0 0 0'
        style={{
          boxShadow: '0 0 2px',
          borderRadius: '5px'
        }}
      >
        <Body>
          {children}
        </Body>
        {footer && (
          <Footer>
            {footer}
          </Footer>
        )}
      </Container>
    </Container>
  );
};

export default Panel;

export const Title = styled.div`
  h2 {
    font-weight: bold;
    color: ${({ theme }) => theme.colors.white};
    border-bottom: 2px solid ${({ theme }) => theme.colors.gray};
  }
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
