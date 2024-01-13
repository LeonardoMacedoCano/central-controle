export type FormFieldsDespesa = {
  categoria: string;
  descricao: string;
  valor: number;
  data: string;
};

export const ValoresIniciaisDespesa = (): FormFieldsDespesa => ({
  categoria: '',
  descricao: '',
  valor: 0,
  data: '',
});

export type FormFieldsTarefa = {
  categoria: string;
  titulo: string;
  descricao: string;
  dataInclusao: string;
  dataPrazo: string;
  finalizado: boolean;
};

export const ValoresIniciaisTarefa = (): FormFieldsTarefa => ({
  categoria: '',
  titulo: '',
  descricao: '',
  dataInclusao: '',
  dataPrazo: '',
  finalizado: false,
});

export type FormFields = FormFieldsDespesa | FormFieldsTarefa;


export const ValoresIniciaisForm = (): FormFields => ({
  ...ValoresIniciaisDespesa(),
  ...ValoresIniciaisTarefa(),
});

export const isFormFieldsDespesa = (fields: FormFields): fields is FormFieldsDespesa => {
  return 'valor' in fields;
};

export const isFormFieldsTarefa = (fields: FormFields): fields is FormFieldsTarefa => {
  return 'titulo' in fields && 'finalizado' in fields;
};