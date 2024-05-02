import React, { ReactNode } from 'react';
import * as C from './styles';

interface PanelProps {
  title?: ReactNode;
  children: ReactNode;
  footer?: ReactNode;
  width?: string;
  maxWidth?: string;
}

const Panel: React.FC<PanelProps> = ({ title, children, footer, width, maxWidth }) => {
  return (
    <C.Panel width={width} maxWidth={maxWidth}>
      {title && (
        <C.Title>
          <h2>{title}</h2>
        </C.Title>
      )}
      <C.PanelContainer>
        <C.Body>
          {children}
        </C.Body>
        {footer && (
          <C.Footer>
            {footer}
          </C.Footer>
        )}
      </C.PanelContainer>
    </C.Panel>
  );
};

export default Panel;
