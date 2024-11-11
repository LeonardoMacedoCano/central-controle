import { CSSObject } from 'styled-components';

export const convertReactStyleToCSSObject = (style: React.CSSProperties): CSSObject => {
  return Object.fromEntries(
    Object.entries(style).map(([key, value]) => [key, value])
  );
}

interface VariantProps {
  variant: 'success' | 'info' | 'warning';
}

export const getVariantColor = (theme: any, variant?: VariantProps['variant']) => {
  const colors = theme.colors;

  const validVariant = variant || 'info';

  switch (validVariant) {
    case 'success':
    case 'info':
    case 'warning':
      return colors[validVariant];
    default:
      return colors.defaultColor;
  }
};
