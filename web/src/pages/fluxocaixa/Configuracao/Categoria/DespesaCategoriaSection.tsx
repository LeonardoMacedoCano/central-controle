import React, { useContext, useEffect, useState, useCallback } from 'react';
import { Categoria, initialCategoriaState, PAGE_SIZE_DEFAULT, PagedResponse } from '../../../../types';
import { AuthContext, useMessage } from '../../../../contexts';
import { DespesaCategoriaService } from '../../../../service';
import { useConfirmModal } from '../../../../hooks';
import { Column, ConfirmModal, FloatingButton, Table } from '../../../../components';
import CategoriaSectionForm from './CategoriaSectionForm';
import { FaPlus } from 'react-icons/fa';

const INITIAL_PAGED_RESPONSE: PagedResponse<Categoria> = {
  content: [],
  last: false,
  totalPages: 0,
  totalElements: 0,
  size: PAGE_SIZE_DEFAULT,
  first: true,
  number: 0,
  numberOfElements: 0,
};

const DespesaCategoriaSection: React.FC = () => {
  const [categorias, setCategorias] = useState(INITIAL_PAGED_RESPONSE);
  const [modalOpen, setModalOpen] = useState(false);
  const [categoriaSelecionada, setCategoriaSelecionada] = useState<Categoria | undefined>();
  const [pageIndex, setPageIndex] = useState(0);
  const [pageSize, setPageSize] = useState(PAGE_SIZE_DEFAULT);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const categoriaService = DespesaCategoriaService();
  const { confirm, ConfirmModalComponent } = useConfirmModal();

  const loadCategorias = useCallback(async () => {
    if (!auth.usuario?.token) return;

    try {
      const result = await categoriaService.getAllCategoriasPaged(auth.usuario.token, pageIndex, pageSize);
      setCategorias(result || INITIAL_PAGED_RESPONSE);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as categorias de despesa.', error);
    }
  }, [auth.usuario?.token, pageIndex, pageSize, categoriaService, message]);

  useEffect(() => {
    loadCategorias();
  }, [loadCategorias]);

  const handleDelete = async (id: number) => {
    if (await confirm('Exclusão de Categoria', 'Tem certeza de que deseja excluir esta categoria? Esta ação não pode ser desfeita.')) {
      await categoriaService.deleteCategoria(auth.usuario?.token!, id);
      loadCategorias();
    }
  };

  const handleSaveCategoria = async () => {
    if (!auth.usuario?.token || !categoriaSelecionada) return;
    await categoriaService.saveCategoria(auth.usuario.token, categoriaSelecionada);
    loadCategorias();
    setModalOpen(false);
  };

  const handleAddCategoria = () => {
    setCategoriaSelecionada({ ...initialCategoriaState, descricao: '' });
    setModalOpen(true);
  };

  const handleEditCategoria = (categoria: Categoria) => {
    setCategoriaSelecionada(categoria);
    setModalOpen(true);
  };

  const loadPage = (pageIndex: number, pageSize: number) => {
    setPageIndex(pageIndex);
    setPageSize(pageSize);
  };

  return (
    <>
      {ConfirmModalComponent}

      {modalOpen && categoriaSelecionada && (
        <ConfirmModal
          variant="info"
          isOpen={modalOpen}
          title={categoriaSelecionada.id ? 'Editar Categoria Despesa' : 'Nova Categoria Despesa'}
          content={
            <CategoriaSectionForm
              categoria={categoriaSelecionada}
              onUpdate={(categoriaAtualizada) => setCategoriaSelecionada((prev) => ({ ...prev, ...categoriaAtualizada }))}
            />
          }
          onClose={() => setModalOpen(false)}
          onConfirm={handleSaveCategoria}
        />
      )}

      <FloatingButton mainButtonIcon={<FaPlus />} mainButtonHint="Adicionar categoria" mainAction={handleAddCategoria} />

      <Table<Categoria>
        values={categorias}
        messageEmpty="Nenhuma categoria encontrada."
        keyExtractor={(item) => item.id.toString()}
        onEdit={handleEditCategoria}
        onDelete={(item) => handleDelete(item.id)}
        loadPage={loadPage}
        columns={[
          <Column<Categoria> header="Código" value={(item) => item.id} width='80px' />,
          <Column<Categoria> header="Descrição" value={(item) => item.descricao} />,
        ]}
      />
    </>
  );
};

export default DespesaCategoriaSection;
