import React, { useContext, useState } from 'react';
import { Container, DragDropFile, FieldValue, FlexBox, FloatingButton, Loading, Panel } from '../../../components';
import { formatDateToYMDString, getCurrentDate, isDateValid } from '../../../utils';
import { ExtratoFaturaCartaoDTO } from '../../../types/fluxocaixa/ExtratoFaturaCartao';
import { ExtratoFluxoCaixaService } from '../../../service';
import { AuthContext, useMessage } from '../../../contexts';
import { FaCheck } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

const ExtratoFaturaCartaoFormPage: React.FC = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [extratoDTO, setExtratoDTO] = useState<ExtratoFaturaCartaoDTO>(
    {
      dataVencimento: getCurrentDate(),
      file: null
    }
  );

  const auth = useContext(AuthContext);
  const message = useMessage();
  const extratoFluxoCaixaService = ExtratoFluxoCaixaService();
  const navigate = useNavigate();

  const importarExtratoFaturaCartao = async () => {
    if (!auth.usuario?.token) return;

    setIsLoading(true);
    try {
      await extratoFluxoCaixaService.importExtratoFaturaCartao(auth.usuario?.token, extratoDTO);
    } catch (error) {
      message.showErrorWithLog('Erro ao importar o extrato da fatura de cartão.', error);
    } finally {
      setIsLoading(false);
      navigate(`/lancamentos`);
    }
  };

  const updateExtratoDTO = (updatedFields: Partial<ExtratoFaturaCartaoDTO>) => {
    const updatedExtratoDTO: ExtratoFaturaCartaoDTO = {
      ...extratoDTO!,
      ...updatedFields,
    };
    setExtratoDTO(prevExtratoDTO => ({ ...prevExtratoDTO, ...updatedExtratoDTO }));
  };

  const handleUpdateDataVencimento = (value: any) => {
    if (value instanceof Date) {
      updateExtratoDTO({ dataVencimento: value });
    }
  };

  const handleFileChange = (value: File | null) => {
    updateExtratoDTO({ file: value });
  };

  const isRequiredFieldsFilled = (): boolean => isDateValid(extratoDTO.dataVencimento) && extratoDTO.file !== null;

  return (
    <Container>
      <Loading isLoading={isLoading} />
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={'Importar Extrato Fatura Cartão'}
        mainAction={importarExtratoFaturaCartao}
        disabled={!isRequiredFieldsFilled()}
      />
      <Panel
        maxWidth='1000px'
        title='Extrato Fatura Cartão'
      >
        <FlexBox flexDirection="column">
          <FlexBox flexDirection="row" borderBottom>
            <FieldValue 
              description='Data Vencimento'
              type='date'
              value={formatDateToYMDString(extratoDTO.dataVencimento)}
              editable={true}
              onUpdate={handleUpdateDataVencimento}
            />
          </FlexBox>
          <FlexBox flexDirection="row">
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

export default ExtratoFaturaCartaoFormPage;
