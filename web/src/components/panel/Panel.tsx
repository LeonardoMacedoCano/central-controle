import React, { ReactNode } from 'react';
import * as C from './styles';

type PanelProps = {
  title?: ReactNode;
  children: ReactNode;
  footer?: ReactNode;
  width?: string;
  maxWidth?: string;
};

class Panel extends React.Component<PanelProps> {
  render() {
    const { title, children, footer, width, maxWidth } = this.props;
    return (
      <C.Panel width={width} maxWidth={maxWidth}>
        {title && (
          <C.Title>
            <h2>{title}</h2>
            <C.TitleLine />
          </C.Title>
        )}
        <C.Body>
          {children}
        </C.Body>
        {footer && (
          <C.Footer>
            {footer}
          </C.Footer>
        )}
      </C.Panel>
    );
  }
}

export default Panel;