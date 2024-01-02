export const formatarDataParaString = (data: Date | undefined): string => {
  if (!data) return '';

  const dataObj = new Date(data);
  const dia = dataObj.getDate().toString().padStart(2, '0');
  const mes = (dataObj.getMonth() + 1).toString().padStart(2, '0');
  const ano = dataObj.getFullYear().toString();

  return `${ano}-${mes}-${dia}`;
};

export const getMesAnoAtual = (): string => {
  const now = new Date();
  return formatarDataParaString(now);
};

export const formatarMesAno = (mesAno: string): string => {
  const meses = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
  const [anoStr, mesStr] = mesAno.split('-');
  const ano = parseInt(anoStr);
  const mes = parseInt(mesStr);

  if (isNaN(ano) || isNaN(mes) || mes < 1 || mes > 12) {
    return 'Mês inválido';
  }
  
  return `${meses[mes - 1]} / ${ano}`;
};
