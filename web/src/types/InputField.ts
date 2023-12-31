import { InputFieldType } from './InputFieldType';
import { FormFields } from './FormFields';

export type InputField = {
  label: string;
  type: InputFieldType;
  key: keyof FormFields;
};