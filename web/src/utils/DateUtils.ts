export const getCurrentDate = (): Date => {
  return new Date();
};

export const formatDateToShortString = (date: Date | undefined): string => {
  if (!date) return '';

  const dateObj = new Date(date);
  const day = dateObj.getDate().toString().padStart(2, '0');
  const month = (dateObj.getMonth() + 1).toString().padStart(2, '0');
  const year = dateObj.getFullYear().toString().slice(-2);

  return `${day}/${month}/${year}`;
};

export const formatDateToYMDString = (date: Date | undefined): string => {
  if (!date) return '';

  const dateObj = new Date(date);
  const year = dateObj.getFullYear().toString(); 
  const month = (dateObj.getMonth() + 1).toString().padStart(2, '0'); 
  const day = dateObj.getDate().toString().padStart(2, '0');

  return `${year}-${month}-${day}`;
};

export const formatDateToYMString = (date: Date | undefined): string => {
  if (!date) return '';

  const dateObj = new Date(date);
  const year = dateObj.getFullYear().toString(); 
  const month = (dateObj.getMonth() + 1).toString().padStart(2, '0'); 

  return `${year}-${month}`;
};

export const parseDateStringToDate = (dateStr: string): Date => {
  const parts = dateStr.split('-');
  const year = parseInt(parts[0], 10);
  const month = parseInt(parts[1], 10) - 1;
  const day = parseInt(parts[2], 10);

  return new Date(year, month, day);
};
  