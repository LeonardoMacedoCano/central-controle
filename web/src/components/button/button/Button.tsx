import React from 'react';
import * as C from './styles';
import { FaPlus, FaEdit, FaTrash, FaDollarSign  } from 'react-icons/fa';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant: 'table-add' | 'table-edit' | 'table-delete' | 'table-money' | 'success' | 'info' | 'warning';
  width?: string;
  height?: string;
  icon?: React.ReactNode;
  description?: string;
  hint?: string;
  disabled?: boolean;
  style?: React.CSSProperties;
}

const getIconAndHint = (variant: ButtonProps['variant'], iconProp?: React.ReactNode, hintProp?: string) => {
  let icon, hint;

  switch (variant) {
    case 'table-add':
      icon = <FaPlus />;
      hint = 'Adicionar';
      break;
    case 'table-edit':
      icon = <FaEdit />;
      hint = 'Editar';
      break;
    case 'table-delete':
      icon = <FaTrash />;
      hint = 'Deletar';
      break;
    case 'table-money':
      icon = <FaDollarSign />;
      hint = 'Alterar Situção';
      break;
    default:
      icon = iconProp;
      hint = hintProp;
      break;
  }

  return { icon, hint };
};

const Button: React.FC<ButtonProps> = ({ variant, description, width, height, icon, hint, disabled, ...props }) => {
  const { icon: computedIcon, hint: computedHint } = getIconAndHint(variant, icon, hint);

  return (
    <C.StyledButton 
      variant={variant} 
      width={width} 
      height={height} 
      title={computedHint} 
      disabled={disabled}
      {...props}
    >
      {computedIcon && <span style={{ display: 'flex', alignItems: 'center' }}>{computedIcon}</span>}
      {description && <span style={{ marginLeft: '8px' }}>{description}</span>}
    </C.StyledButton>
  );
};

export default Button;
