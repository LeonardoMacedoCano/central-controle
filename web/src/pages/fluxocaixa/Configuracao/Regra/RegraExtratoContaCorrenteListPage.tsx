import React, { useContext, useEffect, useState } from 'react';
import { Column, Container, FloatingButton, Loading, Panel, Table } from '../../../../components';
import { getDescricaoTipoRegraExtratoContaCorrente, PAGE_SIZE_DEFAULT, PagedResponse, RegraExtratoContaCorrente } from '../../../../types';
import { useConfirmModal } from '../../../../hooks';
import { AuthContext, useMessage } from '../../../../contexts';
import { useNavigate } from 'react-router-dom';
import { RegraExtratoContaCorrenteService } from '../../../../service';
import { FaPlus } from 'react-icons/fa';
import Card from '../../../../components/card/Card';

const RegraExtratoContaCorrenteListPage: React.FC = () => {
  const [regras, setRegras] = useState<PagedResponse<RegraExtratoContaCorrente>>();
  const [pageIndex, setPageIndex] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(PAGE_SIZE_DEFAULT);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const { confirm, ConfirmModalComponent } = useConfirmModal();
  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const navigate = useNavigate();
  const regraService = RegraExtratoContaCorrenteService();

  useEffect(() => {
    loadRegras();
  }, [usuario?.token, pageIndex]);

  const loadRegras = async () => {
    if (!usuario?.token) return;

    setIsLoading(true);
    try {
      const result = await regraService.getAllRegrasPaged(usuario.token, pageIndex, pageSize);
      setRegras(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as regras.', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleNavigation = (path: string) => navigate(path);

  const handleDelete = async (id: number) => {
    const confirmed = await confirm(
      "Exclusão de Regra", 
      "Tem certeza de que deseja excluir esta regra? Esta ação não pode ser desfeita."
    );

    if (confirmed) {
      try {
        await regraService.deleteRegra(usuario?.token!, id);
        loadRegras();
      } catch (error) {
        message.showErrorWithLog('Erro ao excluir regra.', error);
      }
    } else {
      message.showInfo('Ação cancelada!');
    }
  };

  const loadPage = (pageIndex: number, pageSize: number) => {
    setPageIndex(pageIndex);
    setPageSize(pageSize);
  };

  return (
    <Container>
      {ConfirmModalComponent}
      <Loading isLoading={isLoading} />
      <Panel maxWidth="1000px" title="Regra Extrato Conta Corrente">
        <Table<RegraExtratoContaCorrente>
          values={regras || []}
          messageEmpty="Nenhuma regra encontrada."
          keyExtractor={(item) => item.id.toString()}
          onView={(item) => handleNavigation(`/regra-extrato-conta-corrente/${item.id}`)}
          onEdit={(item) => handleNavigation(`/regra-extrato-conta-corrente/editar/${item.id}`)}
          onDelete={(item) => handleDelete(item.id)}
          loadPage={loadPage}
          columns={[
            <Column<RegraExtratoContaCorrente> 
              header="Ativo" 
              width="60px"
              align="center"
              value={(item) => (
                <Card
                  variant={item.ativo ? 'success' : 'warning'}
                  width='75px'
                  height='25px'
                  style={{textAlign: 'center'}}
                >
                  {item.ativo ? 'Sim' : 'Não'}
                </Card>
              )}
            />,
            <Column<RegraExtratoContaCorrente> 
              header="Tipo" 
              width="150px"
              value={(item) => getDescricaoTipoRegraExtratoContaCorrente(item.tipoRegra)} 
            />,
            <Column<RegraExtratoContaCorrente> 
              header="Descrição" 
              value={(item) => item.descricao} 
            />
          ]}
        />
        <FloatingButton
          mainButtonIcon={<FaPlus />}
          mainAction={() => handleNavigation('/regra-extrato-conta-corrente/novo')}
          mainButtonHint='Adicionar Regra'
        />
      </Panel>
    </Container>
  );
};

export default RegraExtratoContaCorrenteListPage;