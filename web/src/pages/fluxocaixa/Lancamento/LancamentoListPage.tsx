import React, { useContext, useEffect, useState } from 'react';
import { FaPlus } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import { 
  Container,
  Panel,
  Column, 
  Table,
  FloatingButton
} from '../../../components';
import { 
  PagedResponse,
  Lancamento,
  getTipoLancamentoDescricao
} from '../../../types';
import { 
  AuthContext,
  useMessage 
} from '../../../contexts';
import { LancamentoService } from '../../../service';
import { formatDateToShortString } from '../../../utils';
import { useConfirmModal } from '../../../hooks';

const LancamentoListPage: React.FC = () => {
  const [lancamentos, setLancamentos] = useState<PagedResponse<Lancamento> | undefined>(undefined);
  const [pageIndex, setPageIndex] = useState<number>(0); 
  const [pageSize, setPageSize] = useState<number | null>(10); 

  const { confirm, ConfirmModalComponent } = useConfirmModal();
  const lancamentoService = LancamentoService();
  const auth = useContext(AuthContext);
  const message = useMessage();
  const navigate = useNavigate();

  useEffect(() => {
    loadLancamentos();
  }, [auth.usuario?.token, pageIndex, pageSize]);

  const loadLancamentos = async () => {
    if (!auth.usuario?.token || !pageSize) return;

    try {
      const result = await lancamentoService.getLancamentos(auth.usuario?.token, pageIndex, pageSize);
      setLancamentos(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os lançamentos.', error);
    }
  };

  const loadPage = (pageIndex: number, pageSize: number) => {
    setPageIndex(pageIndex);
    setPageSize(pageSize);
  };

  const handleView = (id: number) => navigate(`/lancamento/${id}`);

  const handleEdit = (id: number) => navigate(`/lancamento/${id}`);

  const handleDelete = async (id: number) => {
    const result = await confirm("Exclusão de Lançamento", `Tem certeza de que deseja excluir este lançamento? Esta ação não pode ser desfeita e o lançamento será removido permanentemente.`);

    if (result) {
      deleteLancamento(id);
    } else {
      message.showInfo('Ação cancelada!');
    }
  };

  const deleteLancamento = async (id: number) => {
    if (auth.usuario?.token && id) {
      try {
        await lancamentoService.deleteLancamento(auth.usuario?.token, id);
        loadLancamentos();
      } catch (error) {
        message.showErrorWithLog('Erro ao excluir lançamento.', error);
      }
    }
  };

  return (
    <Container>
      {ConfirmModalComponent}
      <Panel 
        maxWidth='1000px'
        title='Lançamentos'
      >
      <Table<Lancamento>
        values={lancamentos || []}
        messageEmpty="Nenhum lançamento encontrado."
        keyExtractor={(item) => item.id.toString()}
        onView={(item) => handleView(item.id)}
        onEdit={(item) => handleEdit(item.id)}
        onDelete={(item) => handleDelete(item.id)}
        loadPage={loadPage}
        columns={[
          <Column<Lancamento> 
            header="Data" 
            value={(item) => formatDateToShortString(item.dataLancamento)} 
          />,
          <Column<Lancamento> 
            header="Tipo" 
            value={(item) => getTipoLancamentoDescricao(item.tipo)} 
          />,
          <Column<Lancamento> 
            header="Descrição" 
            value={(item) => item.descricao} 
          />
        ]}
      />
      </Panel>
      <FloatingButton
        mainButtonIcon={<FaPlus />}
        mainButtonHint={'Novo Lançamento'}
        mainAction={() => {}}
      />
    </Container>
  );
};

export default LancamentoListPage;
