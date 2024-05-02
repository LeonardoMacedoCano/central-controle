export function formatarValorParaReal(valor: number): string {
  return new Intl.NumberFormat('pt-BR', { 
    style: 'currency', 
    currency: 'BRL' 
  }).format(valor);
};

export const formatarNumeroComZerosAEsquerda = (numero: number, digitos: number): string => {
  return numero.toString().padStart(digitos, '0');
};

export const formatarDescricaoSituacaoParcela = (pago: boolean): string => {
  return (pago ? 'Pago' : 'Não Pago');
}