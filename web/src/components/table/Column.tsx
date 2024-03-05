import React, { ReactNode } from 'react';

export type Props<T> = {
  fieldName: string;
  header: ReactNode;
  value(value: T, index: number): ReactNode;
};

class Column<T> extends React.Component<Props<T>> {
  render() {
    return null;
  }
}

export default Column;
