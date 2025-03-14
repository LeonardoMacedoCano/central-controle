import React, { useContext, useEffect, useState } from 'react';
import { FilterDTO, Notificacao, PAGE_SIZE_DEFAULT, PagedResponse } from '../../types';
import { AuthContext, NotificationContext, useMessage } from '../../contexts';
import { useNavigate } from 'react-router-dom';
import { NotificacaoService } from '../../service';
import { Button, Column, Container, Loading, Panel, SearchFilter, Table } from '../../components';
import { parseShortStringToDateTime } from '../../utils';
import { FaEnvelope, FaEnvelopeOpen, FaLink } from 'react-icons/fa';
import { useConfirmModal } from '../../hooks';

const NotificacaoListPage: React.FC = () => {
  const [notificacoes, setNotificacoes] = useState<PagedResponse<Notificacao>>();
  const [pageIndex, setPageIndex] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(PAGE_SIZE_DEFAULT);
  const [filters, setFilters] = useState<FilterDTO[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const { usuario } = useContext(AuthContext);
  const { refreshNotifications } = useContext(NotificationContext);
  const { confirm, ConfirmModalComponent } = useConfirmModal();
  const message = useMessage();
  const navigate = useNavigate();
  const notificacaoService = NotificacaoService();

  useEffect(() => {
    loadNotificacoes(filters);
  }, [usuario?.token, pageIndex, filters]);

  const loadNotificacoes = async (filters?: FilterDTO[]) => {
    if (!usuario?.token) return;

    setIsLoading(true);
    try {
      const result = await notificacaoService.getNotificacoes(usuario.token, pageIndex, pageSize, filters);
      setNotificacoes(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as notificações.', error);
    } finally {
      setIsLoading(false);
      refreshNotifications();
    }
  };

  const handleNavigation = (path: string) => navigate(path);

  const search = (newFilters: FilterDTO[]) => {
    setPageIndex(0);
    setFilters(newFilters);
  };

  const loadPage = (pageIndex: number, pageSize: number) => {
    setPageIndex(pageIndex);
    setPageSize(pageSize);
  };

  const handleDelete = async (id: number) => {
    const confirmed = await confirm(
      "Exclusão de Notificação", 
      "Tem certeza de que deseja excluir esta notificação? Esta ação não pode ser desfeita."
    );

    if (confirmed) {
      await notificacaoService.deleteNotificacao(usuario?.token!, id);
      loadNotificacoes(filters);
    } else {
      message.showInfo('Ação cancelada!');
    }
  };

  const markAsRead = async (id: number, isUnread: boolean) => {
    await notificacaoService.markAsRead(usuario?.token!, id, isUnread);
    loadNotificacoes(filters);
  };

  return (
    <Container>
      {ConfirmModalComponent}
      <Loading isLoading={isLoading} />
      <Panel maxWidth='1000px' title='Notificações'>
        <SearchFilter 
          fields={[
            { label: 'Data', name: 'dataHora', type: 'DATE' },
            { label: 'Mensagem', name: 'mensagem', type: 'STRING' },
            { label: 'Visto', name: 'visto', type: 'BOOLEAN' },
          ]}
          search={search}
        />
      </Panel>
      <Panel maxWidth='1000px'>
        <Table<Notificacao>
          values={notificacoes || []}
          messageEmpty="Nenhuma notificação encontrada."
          keyExtractor={(item) => item.id.toString()}
          onView={(item) => handleNavigation(`/notificacao/${item.id}`)}
          onDelete={(item) => handleDelete(item.id)}
          customActions={(item) => (
            <>
              <Button
                variant="primary"
                icon={<FaLink />}
                onClick={() => handleNavigation(`${item.link}`)}
                hint={"Link"}
                style={{ borderRadius: '50%', justifyContent: 'center', alignItems: 'center', display: 'flex', height: '25px', width: '25px' }}
                disabled={!item.link || item.link.trim() === ""}
              />
              <Button
                variant="info"
                icon={item.visto ? <FaEnvelope /> : <FaEnvelopeOpen />}
                onClick={() => markAsRead(item.id, !item.visto)}
                hint={item.visto ? "Marcar como não lida" : "Marcar como lida"}
                style={{ borderRadius: '50%', justifyContent: 'center', alignItems: 'center', display: 'flex', height: '25px', width: '25px' }}
              />
            </>
          )}
          loadPage={loadPage}
          columns={[
            <Column<Notificacao> 
              header="Data" 
              align="center" 
              width='120px'
              value={(item) => (
                <>
                  <Button 
                    variant={item.visto ? 'primary' : 'success'}
                    height='10px'
                    width='10px'
                    hint={item.visto ? 'Lido' : 'Não lido'}
                    style={{
                      borderRadius: '50%',
                      marginRight: '5px'
                    }}
                  />
                  {parseShortStringToDateTime(item.dataHora)}
                </>
              )}
            />,
            <Column<Notificacao> 
              header="Mensagem" 
              value={(item) => item.mensagem} 
            />
          ]}
        />
      </Panel>
    </Container>
  );
};

export default NotificacaoListPage;