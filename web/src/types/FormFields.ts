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

export type FormFieldsIdeia = {
  categoria: string;
  titulo: string;
  descricao: string;
  dataInclusao: string;
  dataPrazo: string;
  finalizado: boolean;
};

export const ValoresIniciaisIdeia = (): FormFieldsIdeia => ({
  categoria: '',
  titulo: '',
  descricao: '',
  dataInclusao: '',
  dataPrazo: '',
  finalizado: false,
});

export type FormFields = FormFieldsDespesa | FormFieldsTarefa | FormFieldsIdeia;


export const ValoresIniciaisForm = (): FormFields => ({
  ...ValoresIniciaisDespesa(),
  ...ValoresIniciaisTarefa(),
  ...ValoresIniciaisIdeia(),
});