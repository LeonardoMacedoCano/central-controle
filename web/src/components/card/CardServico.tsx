import React, { useEffect, useState } from 'react';
import styled, { keyframes } from 'styled-components';
import Color from 'color';
import { DockerStatusEnum, getDescricaoDockerStatus, Servico, ServidorConfig } from '../../types';
import Button from '../button/Button';
import { FaAlignLeft, FaLink } from 'react-icons/fa';
import ServicoCategoriaIcon from '../icon/ServicoCategoriaIcon';
import { copyLinkToClipboard, formatNumberWithLeadingZeros } from '../../utils';
import { useMessage } from '../../contexts';

interface CardServicoProps {
  servico: Servico;
  servidorConfig: ServidorConfig;
  onCardClick: () => void;
  loadArquivo: (idarquivo: number) => Promise<string | null>;
}

const CardServico: React.FC<CardServicoProps> = ({ 
  servico, 
  servidorConfig, 
  onCardClick,
  loadArquivo
}) => {
  const [imageUrl, setImageUrl] = useState<string | null>(null);
  const message = useMessage();

  useEffect(() => {
    const fetchData = async () => {
      if (servico.idarquivo) {
        const url = await loadArquivo(servico.idarquivo);
        setImageUrl(url);
      }
    };
    fetchData();
  }, [servico]);

  const copyLink = async () => {
    try {
      const link = `${servidorConfig.ipExterno}:${servico.porta}`;
      await copyLinkToClipboard(link);
      message.showSuccess('Link copiado com sucesso!');
    } catch (error) {
      message.showErrorWithLog('Erro ao copiar o link.', error);
    }
  };

  return (
    <CardContainer onClick={onCardClick}>
      <CardInner>
        <CardHeader>
          <StatusWrapper>
            <Button 
              variant={servico.status === DockerStatusEnum.RUNNING ? 'success' : servico.status === DockerStatusEnum.STOPPED ? 'warning' : 'info'}
              hint={getDescricaoDockerStatus(servico.status)}
              disabledHover
              style={{
                borderRadius: '50%',
                height: '10px',
                width: '10px',
                margin: '0 2px',
                boxShadow: '0 4px 8px rgba(0, 0, 0, 0.5)',
                border: '1px solid rgba(0, 0, 0, 0.3)',
              }}
            />
          </StatusWrapper>
          <CardTitle>{servico.nome}</CardTitle>
          <PortWrapper>
            {servico.porta && (
              <>
                {servico.porta}
                <Button 
                  onClick={(e) => {
                    e.stopPropagation();
                    copyLink();
                  }}
                  variant='info'
                  disabledHover
                  icon={<FaLink />}
                  hint='Copiar Link'
                  style={{
                    borderRadius: '50%',
                    height: '22px',
                    width: '22px',
                    marginLeft: '2px',
                    border: '1px solid rgba(0, 0, 0, 0.3)'
                  }}
                />
              </>
            )}
          </PortWrapper>
        </CardHeader>
        <CardImageContainer>
          {imageUrl ? (
            <CardImage src={imageUrl} alt={servico.nome} />
          ) : (
            <CardImagePlaceholder>Sem Imagem</CardImagePlaceholder>
          )}
        </CardImageContainer>
        <CardId>Nº {formatNumberWithLeadingZeros(servico.id, 3)}</CardId>
        <CardInfoBox>
          <TitleInfoBoxContainer>
            <ButtonGroup>
              <Button 
                variant='info'
                disabledHover
                icon={<FaAlignLeft />}
                style={{
                  borderRadius: '50%',
                  height: '20px',
                  width: '20px',
                  margin: '0 2px',
                  boxShadow: '0 8px 16px rgba(0, 0, 0, 0.3)',
                  fontSize: '0.7rem',
                  border: '1px solid rgba(0, 0, 0, 0.3)'
                }}
              />
            </ButtonGroup>
            <CardTitleInfoBox>Descrição</CardTitleInfoBox>
          </TitleInfoBoxContainer>
          <CardDescription>{servico.descricao}</CardDescription>
          {servico.categorias.length > 0 && (
            <CardCategorias>
              <TitleInfoBoxContainer>
                <ButtonGroup>
                  {servico.categorias.map((categoria, index) => (
                    <Button 
                      key={index}
                      variant='success'
                      disabledHover
                      icon={<ServicoCategoriaIcon servicoCategoria={categoria} />}
                      style={{
                        borderRadius: '50%',
                        height: '20px',
                        width: '20px',
                        margin: '0 2px',
                        boxShadow: '0 8px 16px rgba(0, 0, 0, 0.3)',
                        fontSize: '0.7rem',
                        border: '1px solid rgba(0, 0, 0, 0.3)'
                      }}
                    />
                  ))}
                </ButtonGroup>
                <CardTitleInfoBox>Categorias</CardTitleInfoBox>
              </TitleInfoBoxContainer>
              <CardDescription>
                {servico.categorias.map((categoria, index) => (
                  <CardCategoryDescricao key={categoria.id || index}>
                    {categoria.descricao}
                    {index < servico.categorias.length - 1 && ' | '}
                  </CardCategoryDescricao>
                ))}
              </CardDescription>
            </CardCategorias>
          )}
        </CardInfoBox>
      </CardInner>
    </CardContainer>
  );
};

export default CardServico;

const holographicShine = keyframes`
  0% { background-position: 0% 50%; }
  25% { background-position: 100% 50%; }
  50% { background-position: 0% 50%; }
  75% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
`;

const imageShine = keyframes`
  0% { opacity: 0.8; }
  50% { opacity: 1; }
  100% { opacity: 0.8; }
`;

const CardContainer = styled.div`
  width: 270px;
  height: 370px;
  background: ${({ theme }) => {
    const quaternary = Color(theme.colors.quaternary);
    return `linear-gradient(
      135deg,
      ${quaternary.lighten(0.15).hex()} 0%,
      ${quaternary.lighten(0.3).hex()} 25%,
      ${quaternary.lighten(0.45).hex()} 50%,
      ${quaternary.lighten(0.3).hex()} 75%,
      ${quaternary.lighten(0.15).hex()} 100%
    )`;
  }};
  background-size: 200% 200%;
  animation: ${holographicShine} 6s linear infinite;
  border-radius: 10px;
  padding: 10px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
  margin: 10px;
  display: flex;
  flex-direction: column;
  transition: transform 0.4s ease, box-shadow 0.4s ease;
  cursor: pointer;

  &:hover {
    transform: scale(1.05) rotate(2deg);
    box-shadow: 0 16px 32px rgba(0, 0, 0, 0.6);
  }
`;

const CardInner = styled.div`
  width: 100%;
  height: 100%;
  border: 2px solid ${({ theme }) => Color(theme.colors.quaternary).darken(0.2).hex()};
  border-radius: 6px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 1;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
  animation: ${holographicShine} 6s linear infinite;

  background: ${({ theme }) => {
    const quaternary = Color(theme.colors.quaternary);
    return `linear-gradient(
      135deg,
      ${quaternary.darken(0.0).hex()} 0%,
      ${quaternary.darken(0.15).hex()} 25%,
      ${quaternary.darken(0.3).hex()} 50%,
      ${quaternary.darken(0.15).hex()} 75%,
      ${quaternary.darken(0.0).hex()} 100%
    )`;
  }};
  background-size: 200% 200%;
`;

const CardHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 5px ridge ${({ theme }) => theme.colors.gray};
`;

const StatusWrapper = styled.div`
  font-weight: bold;
  font-size: 0.8rem;
  color: ${({ theme }) => theme.colors.black};
`;

const CardTitle = styled.div`
  font-weight: bold;
  color: ${({ theme }) => theme.colors.black};
  font-size: 1.0rem;
  margin-left: 5px;
  flex-grow: 1;
`;

const PortWrapper = styled.div`
  font-weight: bold;
  display: flex;
  align-items: center;
  font-size: 0.9rem;
  color: ${({ theme }) => theme.colors.black};
  height: 27px;
`;

const CardImageContainer = styled.div`
  border-left: 5px solid ${({ theme }) => Color(theme.colors.gray).darken(0.4).hex()};
  border-right: 5px solid ${({ theme }) => Color(theme.colors.gray).darken(0.4).hex()};
  margin: 0 5px 0 5px;
  height: 140px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
`;

const CardImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  animation: ${imageShine} 2s ease-in-out infinite;
`;

const CardImagePlaceholder = styled.div`
  font-size: 1rem;
  color: ${({ theme }) => theme.colors.black};
  text-align: center;
  padding: 10px;
`;

const CardId = styled.div`
  background: ${({ theme }) => theme.colors.gray};
  height: 15px;
  text-align: center;
  font-size: 0.6rem;
  color: ${({ theme }) => Color(theme.colors.black).lighten(0.4).hex()};
  border-top: 2px solid ${({ theme }) => Color(theme.colors.gray).darken(0.4).hex()};
  border-bottom: 2px solid ${({ theme }) => Color(theme.colors.gray).darken(0.4).hex()};
  border-left: 5px solid ${({ theme }) => Color(theme.colors.gray).darken(0.4).hex()};
  border-right: 5px solid ${({ theme }) => Color(theme.colors.gray).darken(0.4).hex()};
  border-radius: 50px;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.3);
  margin-bottom: 5px;
`;

const CardInfoBox = styled.div`
  padding: 10px;
  flex-grow: 1;
  border-top: none;
  border-radius: 0 0 8px 8px;
`;

const CardDescription = styled.div`
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 0.85rem;
  color: ${({ theme }) => theme.colors.black};
  text-align: justify;
  margin: 5px 0 10px 0;
`;

const CardCategorias = styled.div`
  font-size: 0.85rem;
  color: ${({ theme }) => theme.colors.black};
  text-align: center;
`;

const TitleInfoBoxContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  position: relative;
`;

const ButtonGroup = styled.div`
  display: flex;
  position: absolute;
  left: 0;
`;

const CardTitleInfoBox = styled.h3`
  font-weight: bold;
  color: ${({ theme }) => theme.colors.black};
  font-size: 0.9rem;
  text-align: center;
  flex: 1;
`;

const CardCategoryDescricao = styled.span`
  margin: 0 2px;
  color: ${({ theme }) => theme.colors.black};
  font-size: 0.8rem;
`;
