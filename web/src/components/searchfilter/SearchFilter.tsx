import React, { useState, useEffect } from 'react';
import FieldValue from '../fieldvalue/FieldValue';
import { Field, Operator, OPERATORS, parseDateStringToDate, formatDateToYMDString, getCurrentDate } from '../../utils';
import { FilterDTO } from '../../utils/Filters';

type Props = {
  fields: Field[];
  search(filters: FilterDTO[]): void;
};

const SearchFilter: React.FC<Props> = ({ fields, search }) => {
  const [selectedField, setSelectedField] = useState<Field | null>(null);
  const [selectedOperator, setSelectedOperator] = useState<Operator | null>(null);
  const [searchValue, setSearchValue] = useState<string | number | boolean | null>(null);
  const [filters, setFilters] = useState<FilterDTO[]>([]);

  useEffect(() => {
    if (selectedField?.type.toLowerCase() === 'date' && !searchValue) {
      setSearchValue(formatDateToYMDString(getCurrentDate()));
    }
  }, [selectedField, searchValue]);

  const handleFieldChange = (fieldName: string) => {
    const field = fields.find(f => f.name === fieldName);
    if (field) {
      setSelectedField(field);
      setSelectedOperator(OPERATORS[field.type][0]);
      setSearchValue(null);
    }
  };

  const handleOperatorChange = (operatorName: string) => {
    if (selectedField) {
      const operator = OPERATORS[selectedField.type].find(op => op.name === operatorName);
      if (operator) setSelectedOperator(operator);
    }
  };

  const handleValueChange = (value: any) => {
    if (selectedField?.type.toLowerCase() === 'date') {
      const formattedValue = formatDateValue(value);
      setSearchValue(formattedValue);
    } else {
      setSearchValue(value);
    }
  };

  const formatDateValue = (value: any): string => {
    if (value instanceof Date) {
      return formatDateToYMDString(value);
    }
    const parsedDate = parseDateStringToDate(value);
    return parsedDate ? formatDateToYMDString(parsedDate) : String(value);
  };

  const onAddFilter = () => {
    if (selectedField && selectedOperator && searchValue !== null) {
      const formattedValue = selectedField.type.toLowerCase() === 'date'
        ? formatDateValue(searchValue)
        : String(searchValue);

      const newFilter: FilterDTO = {
        field: selectedField.name,
        operator: selectedOperator.symbol,
        operadorDescr: selectedOperator.name,
        value: formattedValue,
      };

      const newFilters = [...filters, newFilter];
      setFilters(newFilters);
      search(newFilters);

      resetFilterState();
    }
  };

  const resetFilterState = () => {
    setSelectedField(null);
    setSelectedOperator(null);
    setSearchValue(null);
  };

  const onRemove = (index: number) => {
    const updatedFilters = filters.filter((_, i) => i !== index);
    setFilters(updatedFilters);
    search(updatedFilters);
  };

  return (
    <div style={styles.searchFilter}>
      <div style={styles.filterControls}>
        <FieldValue
          type="select"
          value={selectedField?.name || ''}
          options={fields.map(field => ({ key: field.name, value: field.label }))}
          placeholder="Selecione o campo"
          onUpdate={handleFieldChange}
          editable
        />

        <FieldValue
          type="select"
          value={selectedOperator?.name || ''}
          options={
            selectedField
              ? OPERATORS[selectedField.type].map(op => ({ key: op.name, value: op.name }))
              : []
          }
          placeholder="Selecione o operador"
          onUpdate={handleOperatorChange}
          editable
        />

        <FieldValue
          type={selectedField?.type.toLowerCase() as 'string' | 'number' | 'boolean' | 'date' | 'select'}
          value={searchValue || ''}
          onUpdate={handleValueChange}
          options={selectedField?.type === 'SELECT' && selectedField?.options ? selectedField?.options : undefined}
          editable
          placeholder="Digite o valor"
        />

        <button style={styles.filterButton} onClick={onAddFilter}>
          Filtrar
        </button>
      </div>

      <div style={styles.panelFilterTags}>
        {filters.map((filter, index) => (
          <div key={index} style={styles.filterTag}>
            <span>{fields.find(f => f.name === filter.field)?.label} {filter.operadorDescr} {filter.value}</span>
            <button style={styles.removeButton} onClick={() => onRemove(index)}>Remover</button>
          </div>
        ))}
      </div>
    </div>
  );
};

const styles = {
  searchFilter: {
    display: 'flex',
    flexDirection: 'column' as const,
    gap: '8px',
  },
  filterControls: {
    display: 'flex',
    gap: '8px',
    alignItems: 'center' as const,
  },
  panelFilterTags: {
    display: 'flex',
    flexWrap: 'wrap' as const,
    gap: '8px',
  },
  filterTag: {
    display: 'flex',
    gap: '8px',
    padding: '4px 8px',
    backgroundColor: '#3d3d3d',
    borderRadius: '4px',
  },
  removeButton: {
    background: 'none',
    border: 'none',
    color: 'red',
    cursor: 'pointer',
  },
  filterButton: {
    padding: '4px 8px',
    cursor: 'pointer',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
  },
};

export default SearchFilter;
