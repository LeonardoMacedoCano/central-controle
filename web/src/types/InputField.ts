import { InputFieldType } from './InputFieldType';
import { FormFieldsDespesa, FormFieldsTarefa } from './FormFields';

type InputFieldDespesa = {
  label: string;
  type: InputFieldType;
  key: keyof FormFieldsDespesa;
  required: boolean;
};

type InputFieldTarefa = {
  label: string;
  type: InputFieldType;
  key: keyof FormFieldsTarefa;
  required: boolean;
};

export type InputField = InputFieldDespesa | InputFieldTarefa;

const validarCampoObrigatorio = (required: boolean, value: any): boolean => !(required && (value === undefined || value === null || value === ''));  
const validarNumber = (value: any): boolean => typeof value === 'number';
const validarDate = (value: any): boolean => !isNaN(new Date(value).getTime());
const validarText = (value: any): boolean => typeof value === 'string';
const validarSelect = (value: any): boolean => typeof value === 'string' && value.trim() !== '';
const validarBoolean = (value: any): boolean => typeof value === 'boolean';

export const ValidarCampo = (type: InputFieldType, value: any, required: boolean): boolean => {
  switch (type) {
    case 'number':
      return validarNumber(value) && validarCampoObrigatorio(required, value);
    case 'date':
      return validarDate(value) && validarCampoObrigatorio(required, value);
    case 'text':
      return validarText(value) && validarCampoObrigatorio(required, value);
    case 'select':
      return validarSelect(value) && validarCampoObrigatorio(required, value);
    case 'boolean':
      return validarBoolean(value) && validarCampoObrigatorio(required, value);
    default:
      return false;
  }
};