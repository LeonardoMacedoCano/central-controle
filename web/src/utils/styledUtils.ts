import { CSSObject } from 'styled-components';

export const convertReactStyleToCSSObject = (style: React.CSSProperties): CSSObject => {
  return Object.fromEntries(
    Object.entries(style).map(([key, value]) => [key, value])
  );
}

interface VariantProps {
  variant: 'success' | 'info' | 'warning';
}

export const getVariantColor = (variant: VariantProps['variant'], theme: any) => {
  const colors = theme.colors;

  switch (variant) {
    case 'success':
    case 'info':
    case 'warning':
      return colors[variant];
    default:
      return colors.defaultColor; 
  }
}