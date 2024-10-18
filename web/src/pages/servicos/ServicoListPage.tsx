import React, { useContext, useEffect, useState } from 'react';
import styled from 'styled-components';
import { Card, Container, Loading, Panel } from '../../components';
import { Servico, ServidorConfig } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import { ServicoService, ServidorConfigService } from '../../service';

const ServicoListPage: React.FC = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [servicos, setServicos] = useState<Servico[]>([]);
  const [servidorConfig, setServidorConfig] = useState<ServidorConfig>({
    id: 1,
    ipExterno: '0.0.0.0'
  });

  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const servicoService = ServicoService();
  const servidorConfigService = ServidorConfigService();

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
            <Card 
              key={servico.id} 
              servico={servico} 
              servidorConfig={servidorConfig}  
            />
          ))}
        </CardContainer>
      </Panel>
    </Container>
  );
};
export default ServicoListPage;

const CardContainer = styled.div`
  display: flex; 
  flex-wrap: wrap;
  justify-content: space-between;
  margin: 10px;
  gap: 20px;
`;

