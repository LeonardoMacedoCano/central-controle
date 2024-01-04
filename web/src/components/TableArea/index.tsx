import { useState } from 'react';
import * as C from './styles';
import { TableItem } from '../TableItem';
import { FaSortUp, FaSortDown } from "react-icons/fa";

type Props<T extends Record<string, any>> = {
  lista: T[];
  colunasConfig?: Partial<Record<keyof T, { label: string; width?: number }>>;
  colunasFormat?: Partial<Record<keyof T, (value: T[keyof T]) => React.ReactNode>>;
  onEditClick?: (itemId: number | null) => void; 
  itemIdSelecionado?: number | null;
};

export function TableArea<T extends Record<string, any>>({
  lista,
  colunasConfig = {},
  colunasFormat = {},
  onEditClick,
  itemIdSelecionado,
}: Props<T>) {

  if (!lista.length) {
    return null;
  }

  const columns = Object.keys(lista[0]);
  const [colunaSelecionada, setColunaSelecionada] = useState<keyof T | null>(null);
  const [ordemAscendente, setOrdemAscendente] = useState(true);

  const ordenarLista = (column: keyof T, ascendente: boolean) => {
    return lista.slice().sort((a, b) => {
      const valorA = a[column];
      const valorB = b[column];

      if (typeof valorA === 'string' && typeof valorB === 'string') {
        return ascendente ? valorA.localeCompare(valorB) : valorB.localeCompare(valorA);
      } else {
        return ascendente ? valorA - valorB : valorB - valorA;
      }
    });
  };

  const colunasOrdenadas = colunaSelecionada
    ? ordenarLista(colunaSelecionada, ordemAscendente)
    : lista;

  const handleItemClick = (itemId: number) => {
    if (itemIdSelecionado === itemId) {
      onEditClick && onEditClick(null);
    } else {
      onEditClick && onEditClick(itemId);
    }
  };

  const handleColumnClick = (column: keyof T) => {
    if (colunaSelecionada === column) {
      setOrdemAscendente(!ordemAscendente);
    } else {
      setColunaSelecionada(column);
      setOrdemAscendente(true);
    }
  };

  return (
    <C.TableContainer>
      <C.Table>
        <thead>
          <tr>
            {columns.map((column, index) => (
              <C.TableHeadColumn
                key={index}
                style={{ width: colunasConfig[column]?.width }}
                onClick={() => handleColumnClick(column as keyof T)}
              >
                <C.TableColumnTitle>
                  {colunasConfig[column]?.label || column}
                  {colunaSelecionada === column && (
                    <C.TableColumnIcon>
                      {ordemAscendente ? <FaSortUp /> : <FaSortDown />}
                    </C.TableColumnIcon>
                  )}
                </C.TableColumnTitle>
              </C.TableHeadColumn>
            ))}
          </tr>
          <C.TableSeparatorRow>
            {columns.map((_, index) => (
              <C.TableSeparatorCell key={index} />
            ))}
          </C.TableSeparatorRow>
        </thead>
        <tbody>
          {colunasOrdenadas.map((item, index) => (
            <TableItem
              key={index}
              item={item}
              colunasFormat={colunasFormat}
              isSelecionado={item.id === itemIdSelecionado}
              onClick={() => handleItemClick(item.id)}
            />
          ))}
        </tbody>
      </C.Table>
    </C.TableContainer>
  );
};