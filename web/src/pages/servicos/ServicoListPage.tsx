import React, { useContext, useEffect, useState, useCallback } from 'react';
import styled from 'styled-components';
import { CardServico, Container, Loading, Panel, Modal, Button, SearchFilter, SearchPagination } from '../../components';
import { Servico, ServidorConfig, DockerStatusEnum, ContainerActionEnum, PagedResponse, FilterDTO, getDescricaoDockerStatus } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import { ServicoService, ServidorConfigService, ArquivoService } from '../../service';

const calculatePageSize = (): number => {
  const screenWidth = window.innerWidth;
  return screenWidth > 1000 ? 3 : screenWidth > 700 ? 2 : 1;
};

const ServicoListPage: React.FC = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [servicos, setServicos] = useState<PagedResponse<Servico>>();
  const [servidorConfig, setServidorConfig] = useState<ServidorConfig>({ id: 1, ipExterno: '0.0.0.0' });
  const [filters, setFilters] = useState<FilterDTO[]>([]);
  const [pageIndex, setPageIndex] = useState(0);
  const [pageSize, setPageSize] = useState(calculatePageSize());
  const [selectedServico, setSelectedServico] = useState<Servico | null>(null);
  const [showModal, setShowModal] = useState(false);

  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const servicoService = ServicoService();
  const servidorConfigService = ServidorConfigService();
  const arquivoService = ArquivoService();

  const loadServidorConfig = useCallback(async () => {
    if (!usuario?.token) return;
    try {
      const result = await servidorConfigService.getServidorConfig(usuario.token);
      result && setServidorConfig(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar a configuração do servidor.', error);
    }
  }, [usuario?.token, servidorConfigService, message]);

  const loadServicos = useCallback(async () => {
    if (!usuario?.token) return;
    try {
      const result = await servicoService.getServicos(usuario.token, pageIndex, pageSize, filters);
      result && setServicos(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os serviços.', error);
    }
  }, [usuario?.token, pageIndex, pageSize, filters, servicoService, message]);

  const loadArquivo = useCallback(async (idarquivo: number) => {
    if (!usuario?.token) return null;
    try {
      const result = await arquivoService.getArquivoById(usuario.token, idarquivo);
      return result ? URL.createObjectURL(result) : null;
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar o arquivo.', error);
      return null;
    }
  }, [usuario?.token, arquivoService, message]);

  useEffect(() => {
    usuario?.token && loadServidorConfig();
  }, [usuario?.token]);

  useEffect(() => {
    usuario?.token && loadServicos();
  }, [usuario?.token]);

  useEffect(() => {
    const updatePageSize = () => setPageSize(calculatePageSize());
    window.addEventListener('resize', updatePageSize);
    return () => window.removeEventListener('resize', updatePageSize);
  }, []);

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

    content += selectedServico.status === DockerStatusEnum.RUNNING
      ? ' Abaixo estão as opções disponíveis para você:'
      : ' Abaixo está a opção disponível para você:';

    return <p>{content}</p>;
  };

  const OkButtonModal = () => (
    <Button 
      variant="info"
      width="100px"
      height="30px"
      style={{ borderRadius: '5px', display: 'flex', justifyContent: 'center', alignItems: 'center' }}
      description="Ok"
      onClick={() => setShowModal(false)}
    />
  );

  const renderModalActions = () => {
    if (!selectedServico) return null;
    if (!selectedServico.permissao) return OkButtonModal();

    const buttons = [];
    if (selectedServico.status === DockerStatusEnum.RUNNING) {
      buttons.push(
        <Button 
          key="parar"
          variant="warning"
          width="100px"
          height="30px"
          style={{ borderRadius: '5px', display: 'flex', justifyContent: 'center', alignItems: 'center' }}
          description="Parar"
          onClick={() => changeContainerStatusByName(selectedServico.nome, ContainerActionEnum.STOP)}
        />
      );
      buttons.push(
        <Button 
          key="reiniciar"
          variant="info"
          width="100px"
          height="30px"
          style={{ borderRadius: '5px', display: 'flex', justifyContent: 'center', alignItems: 'center' }}
          description="Reiniciar"
          onClick={() => changeContainerStatusByName(selectedServico.nome, ContainerActionEnum.RESTART)}
        />
      );
    } else if (selectedServico.status === DockerStatusEnum.STOPPED) {
      buttons.push(
        <Button
          key="iniciar"
          variant="success"
          width="100px"
          height="30px"
          style={{ borderRadius: '5px', display: 'flex', justifyContent: 'center', alignItems: 'center' }}
          description="Iniciar"
          onClick={() => changeContainerStatusByName(selectedServico.nome, ContainerActionEnum.START)}
        />
      );
    }
    return buttons.length ? buttons : OkButtonModal();
  };

  const renderPagination = () => servicos?.totalElements ? (
    <SearchPagination height="35px" page={servicos} loadPage={setPageIndex} />
  ) : null;

  return (
    <Container>
      <Loading isLoading={isLoading} />
      <Panel maxWidth="1000px" title="Serviços">
        <SearchFilter 
          fields={[
            { label: 'Nome', name: 'nome', type: 'STRING' },
            { label: 'Descrição', name: 'descricao', type: 'STRING' },
            { label: 'Categoria', name: 'categorias', type: 'STRING' },
            { label: 'Porta', name: 'porta', type: 'NUMBER' },
          ]}
          search={(newFilters) => {
            setPageIndex(0);
            setFilters(newFilters);
          }}
        />
      </Panel>
      <Panel maxWidth="1000px" footer={renderPagination()}>
        {servicos && servicos.content.length ? (
          <CardContainer>
            {servicos.content.map(servico => (
              <CardServico 
                key={servico.id} 
                servico={servico} 
                servidorConfig={servidorConfig}
                onCardClick={() => handleCardClick(servico)}
                loadArquivo={loadArquivo}
              />
            ))}
          </CardContainer>
        ) : (
          <EmptyMessage>Nenhum serviço foi encontrado</EmptyMessage>
        )}
      </Panel>
      {showModal && selectedServico && (
        <Modal
          isOpen={showModal}
          showCloseButton
          variant="info"
          title="Alterar status"
          content={renderModalContent()}
          modalWidth="350px"
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
  justify-content: space-between;
`;

const EmptyMessage = styled.p`
  text-align: center;
  padding: 20px;
`;
