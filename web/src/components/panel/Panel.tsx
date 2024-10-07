import React, { ReactNode } from 'react';
import styled from 'styled-components';
import { Container } from '../';

interface PanelProps {
  title?: ReactNode;
  children: ReactNode;
  footer?: ReactNode;
  width?: string;
  maxWidth?: string;
  padding?: string;
}

const Panel: React.FC<PanelProps> = ({ title, children, footer, width, maxWidth, padding }) => {
  return (
    <Container
      width={width || '100%'} 
      padding={padding}
      margin='auto'
      backgroundColor='transparent'
      style={{maxWidth: maxWidth || 'none'}}
    >
      {title && (
        <Title>
          <h3>{title}</h3>
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

const Title = styled.div`
  h3 {
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

const Body = styled(BaseBox)`
  justify-content: space-between;
`;

const Footer = styled(BaseBox)`
  height: 35px;
  justify-content: center;
`;
