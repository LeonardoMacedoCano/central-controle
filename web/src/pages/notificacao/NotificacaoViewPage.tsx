import React, { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Notificacao } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import { NotificacaoService } from '../../service';
import { Container, FieldValue, FlexBox, FloatingButton, Panel } from '../../components';
import { parseShortStringToDateTime } from '../../utils';
import { FaBars, FaEnvelope, FaLink, FaTrash } from 'react-icons/fa';
import { useConfirmModal } from '../../hooks';

const NotificacaoViewPage: React.FC = () => {
  const { id } = useParams<{ id?: string }>();
  const [notificacao, setNotificacao] = useState<Notificacao>();

  const auth = useContext(AuthContext);
  const { confirm, ConfirmModalComponent } = useConfirmModal();
  const message = useMessage();
  const service = NotificacaoService();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      loadNotificacao(id);
      markAsRead();
    }
  }, [auth.usuario?.token, id]);

  const loadNotificacao = async (id: string) => {
    if (!auth.usuario?.token) return;

    try {
      const result = await service.getNotificacao(auth.usuario?.token, id);
      if (result) setNotificacao(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar a notificação.', error);
    }
  };

  const handleDelete = async () => {
    const confirmed = await confirm(
      "Exclusão de Notificação", 
      "Tem certeza de que deseja excluir esta notificação? Esta ação não pode ser desfeita."
    );

    if (confirmed) {
      await service.deleteNotificacao(auth.usuario!.token, id!);
      navigate('/notificacoes')
    } else {
      message.showInfo('Ação cancelada!');
    }
  };

  const markAsUnread = async () => {
    await service.markAsRead(auth.usuario!.token, id!, true);
  };

  const markAsRead = async () => {
    await service.markAsRead(auth.usuario!.token, id!, false);
  };

  return (
    <Container>
      {ConfirmModalComponent}
      <FloatingButton
        mainButtonIcon={<FaBars />}
        options={[
          { icon: <FaLink />, hint: 'Link', action: () => navigate(`${notificacao?.link}`), disabled: !notificacao?.link || notificacao.link.trim() === "" },
          { icon: <FaEnvelope />, hint: 'Marcar como não lida', action: () => markAsUnread() },
          { icon: <FaTrash />, hint: 'Excluir', action: () => handleDelete() },
        ]}
      />
      <Panel maxWidth='1000px' title='Notificação'>
        <FlexBox flexDirection="column">
          <FlexBox flexDirection="row">
            <FlexBox.Item borderRight>
              <FieldValue 
                description='Data'
                type='string'
                value={parseShortStringToDateTime(notificacao?.dataHora)}
              />
            </FlexBox.Item>
          </FlexBox>
          <FlexBox flexDirection="row">
            <FlexBox.Item borderTop>
              <FieldValue 
                description='Descrição'
                type='string'
                value={notificacao?.mensagem}
              />
            </FlexBox.Item>
          </FlexBox>
        </FlexBox>
      </Panel>
    </Container>
  );
};

export default NotificacaoViewPage;
