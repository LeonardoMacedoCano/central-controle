import React, { useContext, useEffect, useState } from 'react';
import { Categoria, FluxoCaixaConfig, PAGE_SIZE, PagedResponse } from '../../../types';
import { Column, ConfirmModal, FieldValue, FlexBox, Panel, Table } from '../../../components';
import { DespesaCategoriaService } from '../../../service';
import { AuthContext, useMessage } from '../../../contexts';
import { useConfirmModal } from '../../../hooks';
import CategoriaSectionForm from './CategoriaSectionForm';

interface DespesaConfigSectionFormProps {
  config: FluxoCaixaConfig;
  onUpdate: (configAtualizado: FluxoCaixaConfig) => void;
}

const DespesaConfigSectionForm: React.FC<DespesaConfigSectionFormProps> = ({ config, onUpdate }) => {
  const [categorias, setCategorias] = useState<PagedResponse<Categoria>>();
  const [modalOpen, setModalOpen] = useState(false);
  const [categoriaSelecionada, setCategoriaSelecionada] = useState<Categoria | undefined>();
  const [pageIndex, setPageIndex] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(PAGE_SIZE);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const despesaCategoriaService = DespesaCategoriaService();
  const { confirm, ConfirmModalComponent } = useConfirmModal();

  const carregarCategoriasDespesa = async () => {
    if (!auth.usuario?.token) return;

    try {
      const result = await despesaCategoriaService.getTodasCategoriasDespesa(auth.usuario?.token, pageIndex, pageSize);
      setCategorias(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as categorias de despesa.', error);
    }
  };

  useEffect(() => {
    carregarCategoriasDespesa();
  }, [pageIndex, pageSize]);

  const handleDelete = async (id: number) => {
    const confirmed = await confirm(
      "Exclusão de Categoria", 
      "Tem certeza de que deseja excluir esta categoria? Esta ação não pode ser desfeita."
    );

    if (confirmed) {
      try {
        await despesaCategoriaService.deleteCategoria(auth.usuario?.token!, id);
        await carregarCategoriasDespesa();
        message.showSuccess('Categoria excluída com sucesso!');
      } catch (error) {
        message.showErrorWithLog('Erro ao excluir categoria.', error);
      }
    }
  };

  const loadPage = (pageIndex: number, pageSize: number) => {
    setPageIndex(pageIndex);
    setPageSize(pageSize);
  };

  const handleUpdateCategoria = (value: any) => {
    const selectedCategoria = categorias?.content.find(c => String(c.id) === String(value));
    onUpdate({ ...config, despesaCategoriaPadrao: selectedCategoria });
  };

  const handleEditCategoria = (categoria: Categoria) => {
    setCategoriaSelecionada({ ...categoria });
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

    try {
      await despesaCategoriaService.saveCategoria(auth.usuario?.token!, categoriaSelecionada);
      message.showSuccess("Categoria atualizada com sucesso!");
      await carregarCategoriasDespesa();
      setModalOpen(false);
      setCategoriaSelecionada(undefined);
    } catch (error) {
      message.showErrorWithLog("Erro ao salvar a categoria.", error);
    }
  }

  const handleCancelarEdicao = () => {
    setModalOpen(false);
    setCategoriaSelecionada(undefined);
  };

  return (
    <>
      {ConfirmModalComponent}
      <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
        <FlexBox flexDirection="row">
          <FlexBox.Item borderRight>
            <FieldValue 
              description="Valor Meta Mensal"
              hint="Meta máxima para o total de despesas em um mês."
              type="number"
              value={config.metaLimiteDespesaMensal || 0}
              editable={true}
              minValue={0}
              onUpdate={(value) => onUpdate({ ...config, metaLimiteDespesaMensal: value })}
            />
          </FlexBox.Item>
          <FlexBox.Item>
            <FieldValue 
              description="Categoria Padrão"
              hint="Categoria de despesa padrão."
              type="select"
              value={{ key: String(config?.despesaCategoriaPadrao?.id), value: config?.despesaCategoriaPadrao?.descricao }}
              editable={true}
              options={categorias?.content.map((categoria) => ({
                key: String(categoria.id),
                value: categoria.descricao,
              }))}
              onUpdate={handleUpdateCategoria}
            />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>

      <Panel maxWidth="1000px">
        <Table<Categoria>
          values={categorias || []}
          messageEmpty="Nenhuma categoria encontrada."
          keyExtractor={(item) => item.id.toString()}
          onEdit={handleEditCategoria}
          onDelete={(item) => handleDelete(item.id)}
          loadPage={loadPage}
          columns={[
            <Column<Categoria> 
              header="Descrição" 
              value={(item) => item.descricao} 
            />
          ]}
        />
      </Panel>

      {modalOpen && categoriaSelecionada && (
        <ConfirmModal
          isOpen={modalOpen}
          title={"Editar Categoria"}
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
    </>
  );
};

export default DespesaConfigSectionForm;