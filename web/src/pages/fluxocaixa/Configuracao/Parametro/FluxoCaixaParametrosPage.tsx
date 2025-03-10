import React, { useContext, useEffect, useState } from "react";
import { AuthContext, useMessage } from "../../../../contexts";
import { DespesaCategoriaService, FluxoCaixaParametroService, RendaCategoriaService } from "../../../../service";
import { Container, FloatingButton, Loading, Panel, Tabs } from "../../../../components";
import { FaCheck } from "react-icons/fa";
import RendaParametroSectionForm from "./RendaParametroSectionForm";
import AtivoParametroSectionForm from "./AtivoParametroSectionForm";
import ExtratoParametroSectionForm from "./ExtratoParametroSectionForm";
import DespesaParametroSectionForm from "./DespesaParametroSectionForm";
import { Categoria, FluxoCaixaParametro, initialFluxoCaixaParametroState } from "../../../../types";

const FluxoCaixaParametrosPage: React.FC = () => {
  const [parametros, setParametros] = useState<FluxoCaixaParametro>(initialFluxoCaixaParametroState);
  const [categoriasDespesa, setCategoriasDespesa] = useState<Categoria[]>([]);
  const [categoriasRenda, setCategoriasRenda] = useState<Categoria[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const parametroService = FluxoCaixaParametroService();
  const categoriaDespesaService = DespesaCategoriaService();
  const categoriaRendaService = RendaCategoriaService();

  useEffect(() => {
    if (!auth.usuario?.token) return;
  
    setIsLoading(true);
    Promise.all([loadParametros(), loadCategoriasDespesa(), loadCategoriasRenda()])
      .finally(() => setIsLoading(false));
  }, [auth.usuario?.token]);
  
  const loadParametros = async () => {
    try {
      const result = await parametroService.getParametros(auth.usuario!.token);
      if (result) setParametros(result);
    } catch (error) {
      message.showErrorWithLog("Erro ao carregar os parâmetros do usuário.", error);
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
  
  const salvarParametros = async () => {
    if (!auth.usuario?.token) return;
  
    await parametroService.saveParametros(auth.usuario?.token, parametros);
    loadParametros;
  };

  const updateParametros = (parametrosAtualizado: FluxoCaixaParametro) => {
    setParametros(parametrosAtualizado);
  };

  const tabs = [
    {
      label: "Despesa",
      content: <DespesaParametroSectionForm parametros={parametros} categorias={categoriasDespesa} onUpdate={updateParametros} />,
    },
    {
      label: "Renda",
      content: <RendaParametroSectionForm parametros={parametros} categorias={categoriasRenda} onUpdate={updateParametros} />,
    },
    {
      label: "Ativo",
      content: <AtivoParametroSectionForm parametros={parametros} onUpdate={updateParametros} />,
    },
    {
      label: "Extrato",
      content: <ExtratoParametroSectionForm parametros={parametros} onUpdate={updateParametros} />,
    },
  ];

  return (
    <Container>
      <Loading isLoading={isLoading} />
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={"Salvar Parâmetros"}
        mainAction={salvarParametros}
      />
      <Panel maxWidth="1000px" title="Parâmetros Fluxo Caixa">
        <Tabs tabs={tabs} />
      </Panel>
    </Container>
  );
};

export default FluxoCaixaParametrosPage;
