import React, { useContext, useEffect, useState } from 'react';
import { Categoria, FluxoCaixaConfig, initialCategoriaState, PAGE_SIZE_COMPACT, PagedResponse } from '../../../types';
import { Button, Column, ConfirmModal, FieldValue, FlexBox, Panel, Table } from '../../../components';
import { ReceitaCategoriaService } from '../../../service';
import { AuthContext, useMessage } from '../../../contexts';
import { useConfirmModal } from '../../../hooks';
import CategoriaSectionForm from './CategoriaSectionForm';
import { FaMoneyBill, FaPlus, FaStar } from 'react-icons/fa';

interface ReceitaConfigSectionFormProps {
  config: FluxoCaixaConfig;
  onUpdate: (configAtualizado: FluxoCaixaConfig) => void;
}

const ReceitaConfigSectionForm: React.FC<ReceitaConfigSectionFormProps> = ({ config, onUpdate }) => {
  const [categorias, setCategorias] = useState<PagedResponse<Categoria>>();
  const [modalOpen, setModalOpen] = useState(false);
  const [categoriaSelecionada, setCategoriaSelecionada] = useState<Categoria | undefined>();
  const [pageIndex, setPageIndex] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(PAGE_SIZE_COMPACT);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const categoriaService = ReceitaCategoriaService();
  const { confirm, ConfirmModalComponent } = useConfirmModal();

  const loadCategorias = async () => {
    if (!auth.usuario?.token) return;

    try {
      const result = await categoriaService.getAllCategoriasPaged(auth.usuario?.token, pageIndex, pageSize);
      setCategorias(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as categorias de receita.', error);
    }
  };

  useEffect(() => {
    loadCategorias();
  }, [pageIndex, pageSize]);

  const handleDelete = async (id: number) => {
    const confirmed = await confirm(
      "Exclusão de Categoria", 
      "Tem certeza de que deseja excluir esta categoria? Esta ação não pode ser desfeita."
    );

    if (confirmed) {
      await categoriaService.deleteCategoria(auth.usuario?.token!, id);
      await loadCategorias();
    }
  };

  const handleUpdateCategoriaPadrao = (value: any) => {
    const selectedCategoria = categorias?.content.find(c => String(c.id) === String(value));
    onUpdate({ ...config, receitaCategoriaPadrao: selectedCategoria });
  };

  const handleUpdateCategoriaGanhoAtivo = (value: any) => {
    const selectedCategoria = categorias?.content.find(c => String(c.id) === String(value));
    onUpdate({ ...config, receitaCategoriaParaGanhoAtivo: selectedCategoria });
  };

  const handleEditCategoria = (categoria: Categoria) => {
    setCategoriaSelecionada({ ...categoria });
    setModalOpen(true);
  };

  const handleAddCategoria = () => {
    setCategoriaSelecionada({ ...initialCategoriaState });
    setModalOpen(true);
  };

  const handleUpdateCategoriaSelecionada = async (categoriaAtualizada: Categoria) => {
    setCategoriaSelecionada((prevCategoria) => ({
      ...prevCategoria,
      ...categoriaAtualizada,
    }));
  };

  const handleSaveCategoriaSelecionada = async () => {
    if (!categoriaSelecionada) return;

    await categoriaService.saveCategoria(auth.usuario?.token!, categoriaSelecionada);
    await loadCategorias();
    setModalOpen(false);
    setCategoriaSelecionada(undefined);
  }

  const handleCancelarEdicao = () => {
    setModalOpen(false);
    setCategoriaSelecionada(undefined);
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
          variant='info'
          isOpen={modalOpen}
          title={categoriaSelecionada.id > 0 ? "Editar Categoria" : "Salvar Categoria"}
          content={(
            <CategoriaSectionForm
            categoria={categoriaSelecionada}
            onUpdate={handleUpdateCategoriaSelecionada}
          />
          )}
          onClose={handleCancelarEdicao}
          onConfirm={handleSaveCategoriaSelecionada}
        />
      )}

      <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
        <FlexBox flexDirection="row">
          <FlexBox.Item borderBottom>
            <FieldValue 
              description='Categoria Ganhos Ativo'
              hint="Categoria de receita para ganhos dos ativos."
              type='string'
              value={config?.receitaCategoriaParaGanhoAtivo?.descricao || ''}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row">
          <FlexBox.Item>
            <FieldValue 
              description='Categoria Padrão'
              hint="Categoria de receita padrão."
              type='string'
              value={config?.receitaCategoriaPadrao?.descricao || ''}
            />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>

      <Panel 
        maxWidth="1000px" 
        title="Categorias" 
        padding='15px 0 0 0'
        actionButton={
          <Button
              variant="success"
              onClick={handleAddCategoria}
              icon={<FaPlus />}
              hint="Adicionar categoria"
              style={{
                borderTopLeftRadius: '50%',
                borderTopRightRadius: '50%',
                justifyContent: 'center',
                alignItems: 'center',
                display: 'flex',
                height: '25px',
                width: '25px',
              }}
            />
        }
      >
        <Table<Categoria>
          values={categorias || []}
          messageEmpty="Nenhuma categoria encontrada."
          keyExtractor={(item) => item.id.toString()}
          onEdit={handleEditCategoria}
          onDelete={(item) => handleDelete(item.id)}
          customActions={(item) => (
            <>
              <Button
                variant="success"
                onClick={() => (handleUpdateCategoriaPadrao(item.id))}
                icon={<FaStar />}
                hint="Definir Principal"
                style={{
                  borderRadius: '50%',
                  justifyContent: 'center',
                  alignItems: 'center',
                  display: 'flex',
                  height: '25px',
                  width: '25px',
                }}
              />
              <Button
                variant="success"
                onClick={() => (handleUpdateCategoriaGanhoAtivo(item.id))}
                icon={<FaMoneyBill />}
                hint="Definir Ganho Ativo"
                style={{
                  borderRadius: '50%',
                  justifyContent: 'center',
                  alignItems: 'center',
                  display: 'flex',
                  height: '25px',
                  width: '25px',
                }}
              />
            </>
          )}
          loadPage={loadPage}
          columns={[
            <Column<Categoria> 
              header="Descrição" 
              value={(item) => item.descricao} 
            />
          ]}
        />
      </Panel>
    </>
  );
};

export default ReceitaConfigSectionForm;