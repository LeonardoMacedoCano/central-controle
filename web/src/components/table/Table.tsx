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
}

interface Indexable {
  [key: string]: any;
}

class Table<T extends Indexable> extends React.Component<Props<T>> {
  renderTableSeparatorRow() {
    const { children } = this.props;
    return (
      <C.TableSeparatorRow>
        {children.map((_, index) => <C.TableSeparatorCell key={index} />)}
      </C.TableSeparatorRow>
    );
  }
    
  renderTableHead() {
    const { children } = this.props;
    return (
      <thead>
        <tr>
          {children.map((child, index) => (
            <C.TableHeadColumn key={index}>
              <C.TableColumnTitle>
                {child.props.header}
              </C.TableColumnTitle>
            </C.TableHeadColumn>
          ))}
        </tr>
        {this.renderTableSeparatorRow()}
      </thead>
    );
  }

  renderTableBody() {
    const { values, children, keyExtractor, onClickRow, rowSelected } = this.props;
    return (
      <tbody>
        {values.map((item, index) => (
          <C.TableRow
            isSelected={rowSelected ? rowSelected(item) : false}
            key={keyExtractor(item, index)}
            onClick={() => onClickRow(item, index)}
          >
            {children.map((child, childIndex) => (
              <C.TableColumn key={childIndex}>
                {child.props.value ? child.props.value(item, index) : item[child.props.fieldName]}
              </C.TableColumn>
            ))}
          </C.TableRow>
        ))}
      </tbody>
    );
  }

  render() {
    const { values, messageEmpty } = this.props;
    return (
      <>
        {values.length === 0 ? (
          <C.EmptyMessage>{messageEmpty}</C.EmptyMessage>
        ) : (
          <C.Table>
            {this.renderTableHead()}
            {this.renderTableBody()}
          </C.Table>
        )}
      </>
    );
  }
}

export default Table;
