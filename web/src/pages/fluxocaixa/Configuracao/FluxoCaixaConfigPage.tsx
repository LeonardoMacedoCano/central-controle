import React from "react";
import { Button, Container, FlexBox, Panel } from "../../../components";
import { useNavigate } from "react-router-dom";

const FluxoCaixaConfigPage: React.FC = () => {
  const navigate = useNavigate();

  const buttons = [
    { label: "Parâmetros", hint: "Parâmetros", path: "/parametros-fluxo-caixa" },
    { label: "Categorias", hint: "Categorias", path: "/categorias-fluxo-caixa" },
    { label: "Regras Extrato Conta Corrente", hint: "Regras Extrato Conta Corrente", path: "/Regras-extrato" },
  ];

  const buttonStyle: React.CSSProperties = {
    border: "3px solid rgba(255, 255, 255, 0.3)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    height: "50px",
    width: "100%",
  };

  return (
    <Container>
      <Panel maxWidth="1000px" title="Parâmetros Fluxo Caixa">
        <FlexBox flexDirection="column">
          <FlexBox flexDirection="row">
            {buttons.map(({ label, hint, path }, index) => (
              <FlexBox.Item key={path}>
                <Button
                  style={{
                    ...buttonStyle,
                    borderTopLeftRadius: index === 0 ? "5px" : undefined,
                    borderBottomLeftRadius: index === 0 ? "5px" : undefined,
                    borderTopRightRadius: index === buttons.length - 1 ? "5px" : undefined,
                    borderBottomRightRadius: index === buttons.length - 1 ? "5px" : undefined,
                  }}
                  description={label}
                  hint={hint}
                  onClick={() => navigate(path)}
                />
              </FlexBox.Item>
            ))}
          </FlexBox>
        </FlexBox>
      </Panel>
    </Container>
  );
};

export default FluxoCaixaConfigPage;
