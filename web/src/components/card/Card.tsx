import React, { useContext, useEffect, useState } from 'react';
import styled, { keyframes } from 'styled-components';
import Color from 'color';
import { Servico, ServicoCategoria, ServidorConfig } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import Button from '../button/button/Button';
import { FaLink } from 'react-icons/fa';
import { ArquivoService, ServicoCategoriaService } from '../../service';
import ServicoCategoriaIcon from '../icon/ServicoCategoriaIcon';
import { copyLinkToClipboard, formatNumberWithLeadingZeros } from '../../utils';

interface CardProps {
  servico: Servico;
  servidorConfig: ServidorConfig;
}

const Card: React.FC<CardProps> = ({ servico, servidorConfig }) => {
  const [imageUrl, setImageUrl] = useState<string | null>(null);
  const [categorias, setCategorias] = useState<ServicoCategoria[]>([]);
  const ativo = true;

  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const arquivoService = ArquivoService();
  const servicoCategoriaService = ServicoCategoriaService();

  useEffect(() => {
    loadData();
  }, [usuario?.token]);

  const loadData = async () => {
    try {
      await Promise.all([loadArquivo(), loadCategorias()]);
    } catch (error) {
      console.error('Erro ao carregar dados:', error);
    }
  };

  const loadArquivo = async () => {
    if (!usuario?.token || !servico.idarquivo) return;
    try {
      const result = await arquivoService.getArquivoById(usuario.token, servico.idarquivo);
      if (result) {
        const url = URL.createObjectURL(result);
        setImageUrl(url);
      }
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os serviços.', error);
    }
  };

  const loadCategorias = async () => {
    if (!usuario?.token) return;
    try {
      const result = await servicoCategoriaService.getCategoriasByServicoId(usuario.token, servico.id);
      setCategorias(result || []);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os serviços.', error);
    }
  };

  const copyLink = async () => {
    try {
      const link = `http://${servidorConfig.ipExterno}:${servico.porta}`;
      await copyLinkToClipboard(link);
      message.showSuccess('Link copiado com sucesso!');
    } catch (error) {
      message.showErrorWithLog('Erro ao copiar o link.', error);
    }
  };

  return (
    <CardContainer>
      <CardInner>
        <CardHeader>
          <StatusWrapper>
            <Button 
              variant={ativo ? 'success' : 'warning'}
              hint={ativo ? 'Ligado' : 'Desligado'}
              disabledHover
              style={{
                borderRadius: '50%',
                height: '10px',
                width: '10px',
                margin: '0 2px',
                cursor: 'default',
                boxShadow: '0 4px 8px rgba(0, 0, 0, 0.5)',
                border: '1px solid rgba(0, 0, 0, 0.3)'
              }}
            />
          </StatusWrapper>
          <CardTitle>{servico.nome}</CardTitle>
          <PortWrapper>
            {servico.porta}
            <Button 
              onClick={copyLink}
              variant='info'
              disabledHover
              icon={<FaLink />}
              hint='Copiar Link'
              style={{
                borderRadius: '50%',
                height: '22px',
                width: '22px',
                marginLeft: '2px',
                marginBottom: '2px',
                border: '1px solid rgba(0, 0, 0, 0.3)'
              }}
            />
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
          <CardDescription>{servico.descricao}</CardDescription>
          {categorias.length > 0 && (
            <CardCategorias>
              <ButtonContainer>
                {categorias.map((categoria, index) => (
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
                      cursor: 'default',
                      boxShadow: '0 8px 16px rgba(0, 0, 0, 0.3)',
                      fontSize: '0.7rem',
                      border: '1px solid rgba(0, 0, 0, 0.3)'
                    }}
                  />
                ))}
                <CardCategoryTitle>Categorias</CardCategoryTitle>
              </ButtonContainer>
              <Descriptions>
                {categorias.map((categoria, index) => (
                  <CardCategoryDescricao key={categoria.id || index}>
                    {categoria.descricao}
                    {index < categorias.length - 1 && ' | '}
                  </CardCategoryDescricao>
                ))}
              </Descriptions>
            </CardCategorias>
          )}
        </CardInfoBox>
      </CardInner>
    </CardContainer>
  );
};

export default Card;

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
  margin-bottom: 10px;
`;

const CardInfoBox = styled.div`
  padding: 10px;
  flex-grow: 1;
  border-top: none;
  border-radius: 0 0 8px 8px;
`;

const CardDescription = styled.div`
  height: 50px;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 0.85rem;
  color: ${({ theme }) => theme.colors.black};
  text-align: justify;
  margin-bottom: 10px;
`;

const CardCategorias = styled.div`
  font-size: 0.85rem;
  color: ${({ theme }) => theme.colors.black};
  text-align: center;
`;

const ButtonContainer = styled.div`
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
`;

const CardCategoryTitle = styled.h3`
  margin: 2px 0 2px 8px;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.black};
  font-size: 0.9rem;
`;

const Descriptions = styled.div`
  margin-top: 5px;
  display: flex;
  flex-wrap: wrap;
  color: ${({ theme }) => theme.colors.black};
`;

const CardCategoryDescricao = styled.span`
  margin: 0 2px;
  color: ${({ theme }) => theme.colors.black};
  font-size: 0.8rem;
`;