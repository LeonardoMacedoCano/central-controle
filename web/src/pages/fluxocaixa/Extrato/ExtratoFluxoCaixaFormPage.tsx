import React, { useContext, useState } from 'react';
import { 
  Container, 
  DragDropFile, 
  FieldValue, 
  FlexBox, 
  FloatingButton, 
  Loading, 
  Panel 
} from '../../../components';
import { 
  ExtratoFluxoCaixa, 
  ExtratoMensalCartaoDTO, 
  getCodigoTipoExtratoFluxoCaixa, 
  getDescricaoTipoExtratoFluxoCaixa, 
  getTipoExtratoFluxoCaixaByCodigo, 
  TipoExtratoFluxoCaixaEnum, 
  tipoExtratoFluxoCaixaOptions 
} from '../../../types';
import { FaCheck } from 'react-icons/fa';
import { formatDateToYMDString, getCurrentDate, isDateValid } from '../../../utils';
import { ExtratoFluxoCaixaService } from '../../../service';
import { AuthContext, useMessage } from '../../../contexts';
import { useNavigate } from 'react-router-dom';

const ExtratoFluxoCaixaFormPage: React.FC = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [extratoDTO, setExtratoDTO] = useState<ExtratoFluxoCaixa>({
    dataVencimento: getCurrentDate(),
    file: null
  });
  const [tipo, setTipo] = useState<TipoExtratoFluxoCaixaEnum>('EXTRATO_MENSAL_CARTAO');

  const auth = useContext(AuthContext);
  const message = useMessage();
  const extratoFluxoCaixaService = ExtratoFluxoCaixaService();
  const navigate = useNavigate();

  const importarExtrato = async () => {
    if (!auth.usuario?.token || !extratoDTO.file) return;

    setIsLoading(true);
    try {
      switch (tipo) {
        case 'EXTRATO_MENSAL_CARTAO':
          await extratoFluxoCaixaService.importExtratoMensalCartao(
            auth.usuario.token, 
            extratoDTO.file, 
            (extratoDTO as ExtratoMensalCartaoDTO).dataVencimento
          );
          break;
        case 'EXTRATO_CONTA_CORRENTE':
          await extratoFluxoCaixaService.importExtratoContaCorrente(auth.usuario.token, extratoDTO.file);
          break;
        case 'EXTRATO_ATIVOS_B3':
          await extratoFluxoCaixaService.importExtratoAtivosB3(auth.usuario.token, extratoDTO.file);
          break;
        default:
          throw new Error('Tipo de extrato não suportado.');
      }
    } catch (error) {
      message.showErrorWithLog('Erro ao importar o extrato da fatura de cartão.', error);
    } finally {
      setIsLoading(false);
      navigate('/lancamentos');
    }
  };

  const isRequiredFieldsFilled = (): boolean => {
    if (!extratoDTO.file) return false;
    if (tipo === 'EXTRATO_MENSAL_CARTAO') {
      return isDateValid((extratoDTO as ExtratoMensalCartaoDTO).dataVencimento);
    }
    return true;
  };

  const handleUpdateTipo = (value: any) => {
    const selectedTipo = getTipoExtratoFluxoCaixaByCodigo(String(value));
    if (selectedTipo) setTipo(selectedTipo);
  };

  const updateExtratoDTO = (updatedFields: Partial<ExtratoFluxoCaixa>) => {
    setExtratoDTO(prevExtratoDTO => ({ ...prevExtratoDTO, ...updatedFields }));
  };

  const handleUpdateDataVencimento = (value: any) => {
    if (value instanceof Date) {
      updateExtratoDTO({ dataVencimento: value });
    }
  };

  const handleFileChange = (value: File | null) => {
    updateExtratoDTO({ file: value });
  };

  return (
    <Container>
      <Loading isLoading={isLoading} />
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint='Importar Extrato'
        mainAction={importarExtrato}
        disabled={!isRequiredFieldsFilled()}
      />
      <Panel maxWidth='1000px' title='Importação Extrato'>
        <FlexBox flexDirection='column'>
          <FlexBox flexDirection='row' borderBottom>
            <FlexBox.Item>
              <FieldValue 
                description='Tipo'
                type='select'
                value={{ 
                  key: getCodigoTipoExtratoFluxoCaixa(tipo), 
                  value: getDescricaoTipoExtratoFluxoCaixa(tipo) 
                }}
                editable
                options={tipoExtratoFluxoCaixaOptions}
                onUpdate={handleUpdateTipo}
              />
            </FlexBox.Item>
          </FlexBox>
          {tipo === 'EXTRATO_MENSAL_CARTAO' && (
            <FlexBox flexDirection='row' borderBottom>
              <FieldValue 
                description='Data Vencimento'
                type='date'
                value={formatDateToYMDString((extratoDTO as ExtratoMensalCartaoDTO).dataVencimento)}
                editable
                onUpdate={handleUpdateDataVencimento}
              />
            </FlexBox>
          )}
          <FlexBox flexDirection='row'>
            <DragDropFile
              acceptedFileFormats={['text/csv']}
              onFileChange={handleFileChange}
            />
          </FlexBox>
        </FlexBox>
      </Panel>
    </Container>
  );
};

export default ExtratoFluxoCaixaFormPage;
