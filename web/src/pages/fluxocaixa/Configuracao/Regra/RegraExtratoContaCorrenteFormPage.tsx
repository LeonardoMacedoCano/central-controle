import { useNavigate, useParams } from "react-router-dom";
import { Container, FieldValue, FlexBox, FloatingButton, Loading, Panel } from "../../../../components";
import { ativoCategoriaOptions, Categoria, getAtivoCategoriaByCodigo, getCodigoAtivoCategoria, getCodigoTipoRegraExtratoContaCorrente, getDescricaoAtivoCategoria, getDescricaoTipoRegraExtratoContaCorrente, getTipoRegraExtratoContaCorrenteByCodigo, initialRegraExtratoContaCorrenteState, RegraExtratoContaCorrente, tipoRegraExtratoContaCorrenteOptions } from "../../../../types";
import { useContext, useEffect, useState } from "react";
import { AuthContext, useMessage } from "../../../../contexts";
import { DespesaCategoriaService, RendaCategoriaService, RegraExtratoContaCorrenteService } from "../../../../service";
import { FaCheck } from "react-icons/fa";

const RegraExtratoContaCorrenteFormPage: React.FC = () => {
  const { id } = useParams<{ id?: string }>();
  const [regra, setRegra] = useState<RegraExtratoContaCorrente>(initialRegraExtratoContaCorrenteState);
  const [categoriasDespesa, setCategoriasDespesa] = useState<Categoria[]>([]);
  const [categoriasRenda, setCategoriasRenda] = useState<Categoria[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const regraService = RegraExtratoContaCorrenteService();
  const navigate = useNavigate();
  const categoriaDespesaService = DespesaCategoriaService();
  const categoriaRendaService = RendaCategoriaService();

  useEffect(() => {
    if (!auth.usuario?.token) return;
  
    setIsLoading(true);
  
    const requests = id ? [loadRegra(id), loadCategoriasDespesa(), loadCategoriasRenda()] 
      : [loadCategoriasDespesa(), loadCategoriasRenda()];
  
    Promise.all(requests).finally(() => setIsLoading(false));
  }, [auth.usuario?.token, id]);
  
  const loadRegra = async (id: string) => {
    try {
      const result = await regraService.getRegra(auth.usuario?.token!, id);
      if (result) setRegra(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar a regra.', error);
    }
  };

  const loadCategoriasDespesa = async () => {
    try {
      const result = await categoriaDespesaService.getAllCategorias(auth.usuario!.token);
      setCategoriasDespesa(result || []);
    } catch (error) {
      message.showErrorWithLog("Erro ao carregar as categorias de despesa.", error);
    }
  };
  
  const loadCategoriasRenda = async () => {
    try {
      const result = await categoriaRendaService.getAllCategorias(auth.usuario!.token);
      setCategoriasRenda(result || []);
    } catch (error) {
      message.showErrorWithLog("Erro ao carregar as categorias de Renda.", error);
    }
  };

  const isRequiredFieldsFilled = (): boolean => {
    return true;
  };
  
  const saveRegra = async () => {
    if (!auth.usuario?.token) return;

    try {
      const response = await regraService.saveRegra(auth.usuario?.token, regra);
      if (response?.id) navigate(`/regra-extrato-conta-corrente/${response.id}`);
    } catch (error) {
      message.showErrorWithLog('Erro ao salvar regra.', error);
    }
  };

  const updateRegra = (updatedFields: Partial<RegraExtratoContaCorrente>) => {
    const updatedCategoria: RegraExtratoContaCorrente = {
      ...regra!,
      ...updatedFields,
    };
    setRegra(updatedCategoria);
  };

  const handleUpdateDescricao = (value: any) => {
    if (typeof value === 'string') {
      updateRegra({ descricao: value });
    }
  };

  const handleUpdateDescricaoMatch = (value: any) => {
    if (typeof value === 'string') {
      updateRegra({ descricaoMatch: value });
    }
  };

  const handleUpdateDescricaoDestino = (value: any) => {
    if (typeof value === 'string') {
      updateRegra({ descricaoDestino: value });
    }
  };

  const handleUpdateTipo = (value: any) => {
    const selectedTipo = getTipoRegraExtratoContaCorrenteByCodigo(String(value));
    
    updateRegra({
      tipoRegra: selectedTipo,
      despesaCategoriaDestino: undefined,
      rendaCategoriaDestino: undefined,
    });
  };

  const handleUpdateCategoriaDespesa = (value: any) => {
    const selectedCategoria = categoriasDespesa.find(c => String(c.id) === String(value)); 
    updateRegra({ despesaCategoriaDestino: selectedCategoria });
  };

  const handleUpdateCategoriaRenda = (value: any) => {
    const selectedCategoria = categoriasRenda.find(c => String(c.id) === String(value)); 
    updateRegra({ rendaCategoriaDestino: selectedCategoria });
  };

  const handleUpdateCategoriaAtivo = (value: any) => {
    const selectedCategoria = getAtivoCategoriaByCodigo(String(value)); 
    updateRegra({ ativoCategoriaDestino: selectedCategoria });
  };

  const handleUpdatePrioridade = (value: any) => {
    updateRegra({ prioridade: value });
  };

  const handleUpdateAtivo = (value: any) => {
    updateRegra({ ativo: value });
  };

  const getCategoriaConfig = () => {
    switch (regra.tipoRegra) {
      case 'CLASSIFICAR_DESPESA':
        return {
          value: {
            key: String(regra.despesaCategoriaDestino?.id || ''),
            value: regra.despesaCategoriaDestino?.descricao || '',
          },
          editable: true,
          options: categoriasDespesa.map(c => ({ key: String(c.id), value: c.descricao })),
          onUpdate: handleUpdateCategoriaDespesa,
        };
      case 'CLASSIFICAR_RENDA':
        return {
          value: {
            key: String(regra.rendaCategoriaDestino?.id || ''),
            value: regra.rendaCategoriaDestino?.descricao || '',
          },
          editable: true,
          options: categoriasRenda.map(c => ({ key: String(c.id), value: c.descricao })),
          onUpdate: handleUpdateCategoriaRenda,
        };
      case 'CLASSIFICAR_ATIVO':
        return {
          value: {
            key: getCodigoAtivoCategoria(regra.ativoCategoriaDestino),
            value: getDescricaoAtivoCategoria(regra.ativoCategoriaDestino),
          },
          editable: true,
          options: ativoCategoriaOptions,
          onUpdate: handleUpdateCategoriaAtivo,
        };
      default:
        return {
          descricao: 'Categoria',
          value: { key: '', value: '' },
          editable: false,
          options: [],
          onUpdate: () => {},
        };
    }
  };

  const categoriaConfig = getCategoriaConfig();

  return (
    <Container>
      <Loading isLoading={isLoading} />
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={'Salvar'}
        mainAction={saveRegra}
        disabled={!isRequiredFieldsFilled()}
      />
      <Panel maxWidth='1000px' title='Regra Extrato Conta Corrente'>
        <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
          <FlexBox flexDirection="row" borderBottom>
            <FlexBox.Item>
              <FieldValue 
                description='Descrição'
                type='string'
                value={regra.descricao}
                editable={true}
                onUpdate={handleUpdateDescricao}
              />
            </FlexBox.Item>
          </FlexBox>

          <FlexBox flexDirection="row" borderBottom>
            <FlexBox.Item borderRight>
              <FieldValue 
                description='Tipo'
                type='select'
                value={{ key: getCodigoTipoRegraExtratoContaCorrente(regra.tipoRegra), value: getDescricaoTipoRegraExtratoContaCorrente(regra.tipoRegra) }}
                editable={true}
                options={tipoRegraExtratoContaCorrenteOptions}
                onUpdate={handleUpdateTipo}
              />
            </FlexBox.Item>
            <FlexBox.Item>
          <FieldValue 
            description="Categoria"
            type="select"
            value={categoriaConfig.value}
            editable={categoriaConfig.editable}
            options={categoriaConfig.options}
            onUpdate={categoriaConfig.onUpdate}
          />
        </FlexBox.Item>

          </FlexBox>

          <FlexBox flexDirection="row" borderBottom>
            <FlexBox.Item borderRight>
              <FieldValue 
                description='Descrição Match'
                type='string'
                value={regra.descricaoMatch}
                editable={true}
                onUpdate={handleUpdateDescricaoMatch}
              />
            </FlexBox.Item>
            <FlexBox.Item>
              <FieldValue 
                description='Descrição Destino'
                type='string'
                value={regra.descricaoDestino}
                editable={true}
                onUpdate={handleUpdateDescricaoDestino}
              />
            </FlexBox.Item>
          </FlexBox>

          <FlexBox flexDirection="row">
            <FlexBox.Item borderRight>
              <FieldValue 
                description='Prioridade'
                type='number'
                value={regra.prioridade}
                editable={true}
                minValue={0}
                maxValue={99}
                onUpdate={handleUpdatePrioridade}
              />
            </FlexBox.Item>
            <FlexBox.Item>
              <FieldValue 
                description='Ativo'
                type='boolean'
                value={regra.ativo}
                editable={true}
                onUpdate={handleUpdateAtivo}
              />
            </FlexBox.Item>
          </FlexBox>
        </FlexBox>
      </Panel>
    </Container>
  );
};

export default RegraExtratoContaCorrenteFormPage;