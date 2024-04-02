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

export const formatarDataParaStringYMD = (data: Date | undefined): string => {
  if (!data) return '';

  const dataObj = new Date(data);
  const dia = dataObj.getDate().toString().padStart(2, '0');
  const mes = (dataObj.getMonth() + 1).toString().padStart(2, '0');
  const ano = dataObj.getFullYear().toString();

  return `${ano}-${mes}-${dia}`;
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

export const formatarStringParaData = (dataStr: string): Date => {
  const parts = dataStr.split('-');
  const year = parseInt(parts[0], 10);
  const month = parseInt(parts[1], 10) - 1;
  const day = parseInt(parts[2], 10);

  return new Date(year, month, day);
};