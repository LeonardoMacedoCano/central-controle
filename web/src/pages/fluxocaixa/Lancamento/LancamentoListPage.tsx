import React, { useContext, useEffect, useState } from 'react';
import { FaBars, FaFileImport, FaPlus } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import { 
  Container, Panel, Column, Table, FloatingButton, Loading, SearchFilter
} from '../../../components';
import { 
  PagedResponse, Lancamento, getDescricaoTipoLancamento, tipoLancamentoFilters, 
  PAGE_SIZE_DEFAULT,
  FilterDTO,
  TipoLancamentoEnum
} from '../../../types';
import { AuthContext, useMessage } from '../../../contexts';
import { LancamentoService } from '../../../service';
import { formatDateToShortString, VariantColor } from '../../../utils';
import { useConfirmModal } from '../../../hooks';
import Card from '../../../components/card/Card';

const LancamentoListPage: React.FC = () => {
  const [lancamentos, setLancamentos] = useState<PagedResponse<Lancamento>>();
  const [pageIndex, setPageIndex] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(PAGE_SIZE_DEFAULT);
  const [filters, setFilters] = useState<FilterDTO[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const { confirm, ConfirmModalComponent } = useConfirmModal();
  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const navigate = useNavigate();
  const lancamentoService = LancamentoService();

  useEffect(() => {
    loadLancamentos(filters);
  }, [usuario?.token, pageIndex, filters]);

  const loadLancamentos = async (filters?: FilterDTO[]) => {
    if (!usuario?.token) return;

    setIsLoading(true);
    try {
      const result = await lancamentoService.getLancamentos(usuario.token, pageIndex, pageSize, filters);
      setLancamentos(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os lançamentos.', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleNavigation = (path: string) => navigate(path);

  const handleDelete = async (id: number) => {
    const confirmed = await confirm(
      "Exclusão de Lançamento", 
      "Tem certeza de que deseja excluir este lançamento? Esta ação não pode ser desfeita."
    );

    if (confirmed) {
      try {
        await lancamentoService.deleteLancamento(usuario?.token!, id);
        loadLancamentos(filters);
      } catch (error) {
        message.showErrorWithLog('Erro ao excluir lançamento.', error);
      }
    } else {
      message.showInfo('Ação cancelada!');
    }
  };

  const search = (newFilters: FilterDTO[]) => {
    setPageIndex(0);
    setFilters(newFilters);
  };

  const loadPage = (pageIndex: number, pageSize: number) => {
    setPageIndex(pageIndex);
    setPageSize(pageSize);
  };

  return (
    <Container>
      {ConfirmModalComponent}
      <Loading isLoading={isLoading} />
      <Panel maxWidth='1000px' title='Lançamentos'>
        <SearchFilter 
          fields={[
            { label: 'Data', name: 'dataLancamento', type: 'DATE' },
            { label: 'Tipo', name: 'tipo', type: 'SELECT', options: tipoLancamentoFilters },
            { label: 'Descrição', name: 'descricao', type: 'STRING' },
          ]}
          search={search}
        />
      </Panel>
      <Panel maxWidth='1000px'>
        <Table<Lancamento>
          values={lancamentos || []}
          messageEmpty="Nenhum lançamento encontrado."
          keyExtractor={(item) => item.id.toString()}
          onView={(item) => handleNavigation(`/lancamento/${item.id}`)}
          onEdit={(item) => handleNavigation(`/lancamento/editar/${item.id}`)}
          onDelete={(item) => handleDelete(item.id)}
          loadPage={loadPage}
          columns={[
            <Column<Lancamento>
              header="Tipo"
              width="100px"
              align="center"
              value={(item) => (
                <Card
                  variant={getTipoVariant(item.tipo!)}
                  width='75px'
                  height='25px'
                  style={{textAlign: 'center'}}
                >
                  {getDescricaoTipoLancamento(item.tipo)}
                </Card>
              )}
            />,
            <Column<Lancamento> 
              header="Data" 
              align="center" 
              width='100px'
              value={(item) => formatDateToShortString(item.dataLancamento)}
            />,
            <Column<Lancamento> 
              header="Descrição" 
              value={(item) => item.descricao} 
            />
          ]}
        />
      </Panel>
      <FloatingButton
        mainButtonIcon={<FaBars />}
        options={[
          { icon: <FaFileImport />, hint: 'Importar Extrato', action: () => handleNavigation('/extrato-fluxo-caixa') },
          { icon: <FaPlus />, hint: 'Lançamento Manual', action: () => handleNavigation('/lancamento/novo') }
        ]}
      />
    </Container>
  );
};

export default LancamentoListPage;

const getTipoVariant = (tipo: TipoLancamentoEnum): VariantColor => {
  switch (tipo) {
    case 'DESPESA':
      return 'warning';
    case 'RENDA':
      return 'success';
    case 'ATIVO':
      return 'info';
  }
};