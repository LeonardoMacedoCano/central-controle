import React, { useContext, useEffect, useState } from 'react';
import styled from 'styled-components';
import { CardServico, Container, Loading, Panel, Modal, Button, SearchFilter, SearchPagination } from '../../components';
import { Servico, ServidorConfig, DockerStatusEnum, ContainerActionEnum, PagedResponse, FilterDTO, getDescricaoDockerStatus } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import { ServicoService, ServidorConfigService, ArquivoService } from '../../service';

const calculatePageSize = (): number => {
  const screenWidth = window.innerWidth;
  
  if (screenWidth > 1000) {
    return 3;
  } else if (screenWidth > 700) {
    return 2;
  } else {
    return 1;
  }
};

const ServicoListPage: React.FC = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [servicos, setServicos] = useState<PagedResponse<Servico>>();
  const [servidorConfig, setServidorConfig] = useState<ServidorConfig>({
    id: 1,
    ipExterno: '0.0.0.0'
  });
  const [filters, setFilters] = useState<FilterDTO[]>([]);
  const [pageIndex, setPageIndex] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(calculatePageSize());
  const [selectedServico, setSelectedServico] = useState<Servico | null>(null);
  const [showModal, setShowModal] = useState<boolean>(false);

  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const servicoService = ServicoService();
  const servidorConfigService = ServidorConfigService();
  const arquivoService = ArquivoService();

  useEffect(() => {
    if (usuario?.token) {
      loadServidorConfig();
    }
  }, [usuario?.token]);

  useEffect(() => {
    if (usuario?.token) {
      loadServicos();
    }
  }, [usuario?.token, pageIndex, pageSize, filters]);

  useEffect(() => {
    const updatePageSize = () => {
      setPageSize(calculatePageSize());
    };

    updatePageSize();
    window.addEventListener('resize', updatePageSize);
    return () => window.removeEventListener('resize', updatePageSize);
  }, []);

  const loadServidorConfig = async () => {
    if (!usuario?.token) return;
    try {
      const result = await servidorConfigService.getServidorConfig(usuario.token);
      if (result) setServidorConfig(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar a configuração do servidor.', error);
    }
  };
  
  const loadServicos = async () => {
    if (!usuario?.token) return;
    try {
      const result = await servicoService.getServicos(usuario.token, pageIndex, pageSize, filters);
      if (result) setServicos(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os serviços.', error);
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

  const search = (newFilters: FilterDTO[]) => {
    setPageIndex(0);
    setFilters(newFilters);
  };

  const loadPage = (pageIndex: number) => {
    setPageIndex(pageIndex);
  };
  
  const changeContainerStatusByName = async (servicoNome: string, action: string) => {
    if (!usuario?.token) return;
    setIsLoading(true);
    try {
      await servicoService.changeContainerStatusByName(usuario.token, servicoNome, action);
      setShowModal(false);
      await loadServicos();
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

    const statusDescription = getDescricaoDockerStatus(selectedServico.status);
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

  const renderPagination = () => {
    if (servicos) {
      return (
        <SearchPagination
          height='35px'
          page={servicos}
          loadPage={loadPage}
        />
      );
    }
    return null;
  };

  return (
    <Container>
      <Loading isLoading={isLoading} />
      <Panel maxWidth='1000px' title='Serviços'>
        <SearchFilter 
          fields={[
            { label: 'Nome', name: 'nome', type: 'STRING' },
            { label: 'Descrição', name: 'descricao', type: 'STRING' },
            { label: 'Categoria', name: 'categorias', type: 'STRING' },
            { label: 'Porta', name: 'porta', type: 'NUMBER' },
          ]}
          search={search}
        />
      </Panel>
      <Panel 
        maxWidth='1000px' 
        footer={renderPagination()}
      >
        <CardContainer>
          {servicos && servicos.content.map(servico => (
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
