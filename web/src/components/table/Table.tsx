import React, { ReactElement } from 'react';
import { Props as ColumnProps } from './Column';
import * as C from './styles';

interface Props<T> {
  values: T[];
  children: ReactElement<ColumnProps<T>>[];
  messageEmpty?: string;
  keyExtractor(item: T, index?: number): string | number;
  onClickRow(item: T, index?: number): void;
  rowSelected?(item: T): boolean;
  customHeader?: React.ReactNode;
}

interface Indexable {
  [key: string]: any;
}

class Table<T extends Indexable> extends React.Component<Props<T>> {
  renderTableHead() {
    const { children } = this.props;
    return (
      <thead>
        <C.TableHeadRow>
          {children.map((child, index) => (
            <C.TableHeadColumn key={index}>
              <C.TableColumnTitle>
                {child.props.header}
              </C.TableColumnTitle>
            </C.TableHeadColumn>
          ))}
        </C.TableHeadRow>
      </thead>
    );
  }

  renderTableBody() {
    const { values, children, keyExtractor, onClickRow, rowSelected } = this.props;
    return (
      <tbody>
        {values.map((item, index) => (
          <C.TableRow
            key={keyExtractor(item, index)}
            onClick={() => onClickRow(item, index)}
          >
            {children.map((child, childIndex) => (
              <C.TableColumn 
                key={childIndex}
                isSelected={rowSelected ? rowSelected(item) : false}>
                {child.props.value ? child.props.value(item, index) : item[child.props.fieldName]}
              </C.TableColumn>
            ))}
          </C.TableRow>
        ))}
      </tbody>
    );
  }

  render() {
    const { values, messageEmpty, customHeader } = this.props;
    return (
      <C.TableContainer>
        {customHeader && (
          <C.CustomHeader>
            {customHeader}
          </C.CustomHeader>
        )}
        {values.length === 0 ? (
          <C.EmptyMessage>{messageEmpty}</C.EmptyMessage>
        ) : (
          <C.StyledTable>
            {this.renderTableHead()}
            {this.renderTableBody()}
          </C.StyledTable>
        )}
      </C.TableContainer>
    );
  }
}

export default Table;
