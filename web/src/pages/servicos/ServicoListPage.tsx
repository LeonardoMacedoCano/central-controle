import React, { useContext, useEffect, useState } from 'react';
import { Card, Container, Loading, Panel } from '../../components';
import { Servico } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import ServicoService from '../../service/servicos/ServicoService';

const ServicoListPage: React.FC = () => {
  const [servicos, setServicos] = useState<Servico[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const servicoService = ServicoService();

  useEffect(() => {
    loadServicos();
  }, [usuario?.token]);

  const loadServicos = async () => {
    if (!usuario?.token) return;

    setIsLoading(true);
    try {
      const result = await servicoService.getAllServicos(usuario.token);
      if (result) setServicos(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os serviços.', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Container>
      <Loading isLoading={isLoading} />
      <Panel maxWidth='1000px' title='Serviços'>
        {servicos.map(servico => (
          <Card
            servico={servico}
          />
        ))}
      </Panel>
    </Container>
  );
};

export default ServicoListPage;