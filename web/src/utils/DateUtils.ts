export const getDataAtual = (): Date => {
  return new Date();
};

export const formatarDataParaString = (data: Date | undefined): string => {
  if (!data) return '';

  const dataObj = new Date(data);
  const dia = dataObj.getDate().toString().padStart(2, '0');
  const mes = (dataObj.getMonth() + 1).toString().padStart(2, '0');
  const ano = dataObj.getFullYear().toString();

  return `${dia}/${mes}/${ano}`;
};

export const formatarDataParaAnoMes = (data: Date | undefined): string => {
  if (!data) return '';

  const dataObj = new Date(data);
  const mes = (dataObj.getMonth() + 1).toString().padStart(2, '0');
  const ano = dataObj.getFullYear().toString();

  return `${ano}-${mes}`;
};

export const formataraMesAnoParaData = (mesAno: string): Date => {
  const [anoStr, mesStr] = mesAno.split('-');
  const ano = parseInt(anoStr);
  const mesIndex = parseInt(mesStr) -1;

  return new Date(ano, mesIndex);
};