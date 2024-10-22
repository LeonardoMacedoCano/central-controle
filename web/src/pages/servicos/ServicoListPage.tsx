import React, { useContext, useEffect, useState } from 'react';
import styled from 'styled-components';
import { CardServico, Container, Loading, Panel, Modal, Button } from '../../components';
import { Servico, ServidorConfig, DockerStatusEnum, ContainerActionEnum, getDockerStatusDescription } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import { ServicoService, ServidorConfigService, ArquivoService } from '../../service';

const ServicoListPage: React.FC = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [servicos, setServicos] = useState<Servico[]>([]);
  const [servidorConfig, setServidorConfig] = useState<ServidorConfig>({
    id: 1,
    ipExterno: '0.0.0.0'
  });
  const [selectedServico, setSelectedServico] = useState<Servico | null>(null);
  const [showModal, setShowModal] = useState<boolean>(false);

  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const servicoService = ServicoService();
  const servidorConfigService = ServidorConfigService();
  const arquivoService = ArquivoService();

  useEffect(() => {
    if (usuario?.token) {
      loadData();
    }
  }, [usuario?.token]);
  
  const loadData = async () => {
    setIsLoading(true);
    try {
      await Promise.all([loadServicos(), loadServidorConfig()]);
    } catch (error) {
      console.error('Erro ao carregar dados:', error);
    } finally {
      setIsLoading(false);
    }
  };
  
  const loadServicos = async () => {
    if (!usuario?.token) return;
    try {
      const result = await servicoService.getAllServicos(usuario.token);
      if (result) setServicos(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os serviços.', error);
    }
  };
  
  const loadServidorConfig = async () => {
    if (!usuario?.token) return;
    try {
      const result = await servidorConfigService.getServidorConfig(usuario.token);
      if (result) setServidorConfig(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar a configuração do servidor.', error);
    }
  };

  const loadArquivo = async (idarquivo: number) => {
    if (!usuario?.token) return null;
    try {
      const result = await arquivoService.getArquivoById(usuario.token, idarquivo);
      if (result) {
        return URL.createObjectURL(result);
      }
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar o arquivo.', error);
    }
    return null;
  };
  
  const changeContainerStatusByName = async (servicoNome: string, action: string) => {
    if (!usuario?.token) return;
    setIsLoading(true);
    try {
      await servicoService.changeContainerStatusByName(usuario.token, servicoNome, action);
      setShowModal(false);
      await loadData();
    } catch (error) {
      message.showErrorWithLog('Erro ao alterar o status do container.', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleCardClick = (servico: Servico) => {
    setSelectedServico(servico);
    setShowModal(true);
  };

  const renderModalContent = () => {
    if (!selectedServico) return null;

    const statusDescription = getDockerStatusDescription(selectedServico.status);
    let content = `O serviço ${selectedServico.nome} está atualmente ${statusDescription}.`;

    if (!selectedServico.permissao) {
      return <p><b>Acesso negado</b>: seu usuário não tem permissão para alterar o status deste serviço. Para mais informações, entre em contato com o administrador.</p>;
    }

    if (selectedServico.status === DockerStatusEnum.RUNNING) {
      content += ' Abaixo estão as opções disponíveis para você:';
    } else if (selectedServico.status === DockerStatusEnum.STOPPED) {
      content += ' Abaixo está a opção disponível para você:';
    } else {
      content = `O serviço ${selectedServico.nome} não está em um estado que permite ações.`;
    }

    return <p>{content}</p>;
  };

  const OkButtonModal = () => (
    <Button 
      variant="info"
      width="100px"
      height="30px"
      style={{
        borderRadius: '5px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
      description="Ok"
      onClick={() => setShowModal(false)}
    />
  );

  const renderModalActions = () => {
    if (!selectedServico) return null;

    if (!selectedServico.permissao) {
      return OkButtonModal();
    }

    switch (selectedServico.status) {
      case DockerStatusEnum.RUNNING:
        return (
          <>
            <Button 
              variant="warning"
              width="100px"
              height="30px"
              style={{
                borderRadius: '5px',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
              }}
              description="Parar"
              onClick={() => changeContainerStatusByName(selectedServico.nome, ContainerActionEnum.STOP)}
            />
            <Button 
              variant="info"
              width="100px"
              height="30px"
              style={{
                borderRadius: '5px',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
              }}
              description="Reiniciar"
              onClick={() => changeContainerStatusByName(selectedServico.nome, ContainerActionEnum.RESTART)}
            />
          </>
        );
      case DockerStatusEnum.STOPPED:
        return (
          <Button
            variant="success"
            width="100px"
            height="30px"
            style={{
              borderRadius: '5px',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}
            description="Iniciar"
            onClick={() => changeContainerStatusByName(selectedServico.nome, ContainerActionEnum.START)}
          />
        );
      default:
        return OkButtonModal();
    }
  };

  return (
    <Container style={{ 
      display: 'flex', 
      justifyContent: 'center', 
      flexWrap: 'wrap', 
      maxWidth: '100%', 
      margin: 'auto' 
    }}>
      <Loading isLoading={isLoading} />
      <Panel maxWidth='1000px' title='Serviços'>
        <CardContainer>
          {servicos.map(servico => (
            <CardServico 
              key={servico.id} 
              servico={servico} 
              servidorConfig={servidorConfig}
              onCardClick={() => handleCardClick(servico)}
              loadArquivo={loadArquivo}
            />
          ))}
        </CardContainer>
      </Panel>
      {showModal && selectedServico && (
        <Modal
          isOpen={showModal}
          showCloseButton
          variant='info'
          title={'Alterar status'}
          content={renderModalContent()}
          modalWidth='350px'
          onClose={() => setShowModal(false)}
          actions={renderModalActions()}
        />
      )}
    </Container>
  );
};

export default ServicoListPage;

const CardContainer = styled.div`
  width: 100%;
  display: flex; 
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  margin: 10px;
  gap: 20px;
`;
