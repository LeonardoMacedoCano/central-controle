import { InputFieldType } from './InputFieldType';
import { FormFieldsDespesa, FormFieldsTarefa } from './FormFields';

type InputFieldDespesa = {
  label: string;
  type: InputFieldType;
  key: keyof FormFieldsDespesa;
};

type InputFieldTarefa = {
  label: string;
  type: InputFieldType;
  key: keyof FormFieldsTarefa;
};

export type InputField = InputFieldDespesa | InputFieldTarefa;
