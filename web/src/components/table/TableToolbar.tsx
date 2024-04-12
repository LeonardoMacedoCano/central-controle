import React from 'react';
import Button from '../../components/button/button/Button';

interface TableToolbarProps {
  handleAdd?: () => void;
  handleEdit?: (show: boolean) => void;
  handleDelete?: () => void;
  handleMoney?: () => void;
  isItemSelected: boolean;
}

class TableToolbar extends React.Component<TableToolbarProps> {
  render() {
    const { handleAdd, handleEdit, handleDelete, handleMoney, isItemSelected } = this.props;

    return (
      <>
        { handleAdd && 
          <Button 
            variant='table-add' 
            onClick={handleAdd} 
            disabled={isItemSelected} 
          />
        }
        { handleEdit &&
          <Button 
            variant='table-edit' 
            onClick={() => handleEdit(true)} 
            disabled={!isItemSelected} 
          />
        }
        { handleDelete &&
          <Button 
            variant='table-delete' 
            onClick={handleDelete} 
            disabled={!isItemSelected} 
          />
        }
        { handleMoney &&
          <Button 
            variant='table-money' 
            onClick={handleMoney} 
            disabled={!isItemSelected} 
          />
        }
      </>
    );
  }
}

export default TableToolbar;
