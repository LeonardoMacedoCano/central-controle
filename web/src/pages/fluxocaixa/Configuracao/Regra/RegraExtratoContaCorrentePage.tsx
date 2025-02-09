import { useParams } from "react-router-dom";
import { Container, FieldValue, FlexBox, Loading, Panel } from "../../../../components";
import { getDescricaoTipoRegraExtratoContaCorrente, RegraExtratoContaCorrente } from "../../../../types";
import { useContext, useEffect, useState } from "react";
import { AuthContext, useMessage } from "../../../../contexts";
import { RegraExtratoContaCorrenteService } from "../../../../service";

const RegraExtratoContaCorrentePage: React.FC = () => {
  const { id } = useParams<{ id?: string }>();
  const [regra, setRegra] = useState<RegraExtratoContaCorrente>();
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const regraService = RegraExtratoContaCorrenteService();

  useEffect(() => {
    if (!auth.usuario?.token) return;
  
    loadRegra(id!)
  }, [auth.usuario?.token, id]);
  
  const loadRegra = async (id: string) => {
    setIsLoading(true);
    try {
      const result = await regraService.getRegra(auth.usuario?.token!, id);
      if (result) setRegra(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar a regra.', error);
    } finally {
      setIsLoading(false);
    }
  };

  const getDescricaoCategoria = () => {
    switch (regra!.tipoRegra) {
      case 'CLASSIFICAR_DESPESA':
        return regra?.despesaCategoriaDestino ? regra?.despesaCategoriaDestino.descricao : "";
  
      case 'CLASSIFICAR_RENDA':
        return regra?.rendaCategoriaDestino ? regra?.rendaCategoriaDestino.descricao : "";
  
      default:
        return "";
    }
  };

  return (
    <Container>
      <Loading isLoading={isLoading} />
      {regra && (
        <Panel maxWidth='1000px' title='Regra Extrato Conta Corrente'>
          <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
            <FlexBox flexDirection="row" borderBottom>
              <FlexBox.Item>
                <FieldValue 
                  description='Descrição'
                  type='string'
                  value={regra.descricao}
                />
              </FlexBox.Item>
            </FlexBox>

            <FlexBox flexDirection="row" borderBottom>
              <FlexBox.Item borderRight>
                <FieldValue 
                  description='Tipo'
                  type='string'
                  value={getDescricaoTipoRegraExtratoContaCorrente(regra.tipoRegra)}
                />
              </FlexBox.Item>
              <FlexBox.Item>
            <FieldValue 
              description="Categoria"
              type="string"
              value={getDescricaoCategoria()}
            />
          </FlexBox.Item>

            </FlexBox>

            <FlexBox flexDirection="row" borderBottom>
              <FlexBox.Item borderRight>
                <FieldValue 
                  description='Descrição Match'
                  type='string'
                  value={regra.descricaoMatch}
                />
              </FlexBox.Item>
              <FlexBox.Item>
                <FieldValue 
                  description='Descrição Destino'
                  type='string'
                  value={regra.descricaoDestino}
                />
              </FlexBox.Item>
            </FlexBox>

            <FlexBox flexDirection="row">
              <FlexBox.Item borderRight>
                <FieldValue 
                  description='Prioridade'
                  type='number'
                  value={regra.prioridade}
                />
              </FlexBox.Item>
              <FlexBox.Item>
                <FieldValue 
                  description='Ativo'
                  type='string'
                  value={regra.ativo ? "Sim" : "Não"}
                />
              </FlexBox.Item>
            </FlexBox>
          </FlexBox>
        </Panel>
      )}
    </Container>
  );
};

export default RegraExtratoContaCorrentePage;