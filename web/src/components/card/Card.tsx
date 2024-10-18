import React, { useContext, useEffect, useState } from 'react';
import styled, { keyframes } from 'styled-components';
import Color from 'color';
import { Servico, ServidorConfig } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import Button from '../button/button/Button';
import { FaLink } from 'react-icons/fa';
import { ArquivoService } from '../../service';

interface CardProps {
  servico: Servico;
  servidorConfig: ServidorConfig;
}

const Card: React.FC<CardProps> = ({ servico, servidorConfig }) => {
  const { usuario } = useContext(AuthContext);
  const message = useMessage();
  const arquivoService = ArquivoService();

  const [imageUrl, setImageUrl] = useState<string | null>(null);

  useEffect(() => {
    if (servico.idarquivo) {
      loadArquivo();
    }
  }, [servico.idarquivo, usuario?.token]);

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

  const copyLink = () => {
    try {
      const link = `http://${servidorConfig.ipExterno}:${servico.porta}`;
      navigator.clipboard.writeText(link);
      message.showSuccess('Link copiado com sucesso!');
    } catch (error) {
      message.showErrorWithLog('Erro ao copiar o link.', error);
    }
  };

  return (
    <CardContainer>
      <CardInner>
        <CardHeader>
          <CardTitle>{servico.nome}</CardTitle>
          {servico.porta && (
            <CardPort>
              {servico.porta}
              <Button 
                onClick={copyLink}
                variant='info'
                icon={<FaLink />}
                hint='Copiar Link'
                style={{
                  borderRadius: '50%',
                  justifyContent: 'center',
                  alignItems: 'center',
                  display: 'flex',
                  height: '25px',
                  width: '25px',
                  marginLeft: '5px'
                }}
              />
            </CardPort>
          )}
        </CardHeader>
        <CardImageContainer>
          {imageUrl ? (
            <CardImage src={imageUrl} alt={servico.nome} />
          ) : (
            <CardImagePlaceholder>Sem Imagem</CardImagePlaceholder>
          )}
        </CardImageContainer>
        <CardInfoBox>
          <CardDescription>{servico.descricao}</CardDescription>
        </CardInfoBox>
      </CardInner>
    </CardContainer>
  );
};

export default Card;

const holographicShine = keyframes`
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
`;

const imageShine = keyframes`
  0% { opacity: 0.6; }
  50% { opacity: 1; }
  100% { opacity: 0.6; }
`;

const CardContainer = styled.div`
  width: 270px;
  height: 370px;
  background: ${({ theme }) => {
    const quaternary = Color(theme.colors.quaternary);
    return `linear-gradient(
      135deg,
      ${quaternary.darken(0.2).hex()} 0%,
      ${quaternary.darken(0.4).hex()} 25%,
      ${quaternary.darken(0.6).hex()} 50%,
      ${quaternary.darken(0.4).hex()} 75%,
      ${quaternary.darken(0.2).hex()} 100%
    )`;
  }};
  background-size: 200% 200%;
  animation: ${holographicShine} 3s linear infinite;
  border-radius: 20px;
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
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(8px);
  border-radius: 15px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  box-shadow: inset 0 0 15px rgba(0, 0, 0, 0.3);
`;

const CardHeader = styled.div`
  background: ${({ theme }) => {
    const quaternary = Color(theme.colors.quaternary);
    return `linear-gradient(
      135deg,
      ${quaternary.darken(0.2).hex()} 0%,
      ${quaternary.darken(0.4).hex()} 25%,
      ${quaternary.darken(0.6).hex()} 50%,
      ${quaternary.darken(0.4).hex()} 75%,
      ${quaternary.darken(0.2).hex()} 100%
    )`;
  }};
  background-size: 200% 200%;
  animation: ${holographicShine} 3s linear infinite;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 15px 15px 0 0;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.5);
`;

const CardTitle = styled.span`
  color: ${({ theme }) => theme.colors.black};
  font-weight: bold;
  font-size: 1.3rem;
  text-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
`;

const CardPort = styled.span`
  color: ${({ theme }) => theme.colors.black};
  font-weight: bold;
  font-size: 0.8rem;
  text-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
`;

const CardImageContainer = styled.div`
  border: 4px solid ${({ theme }) => Color(theme.colors.quaternary).lighten(0.3).hex()};
  margin: 10px;
  height: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  border-radius: 8px;
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.3);
`;

const CardImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  animation: ${imageShine} 3s linear infinite;
`;

const CardImagePlaceholder = styled.div`
  font-size: 1rem;
  color: ${({ theme }) => theme.colors.black};
  text-align: center;
  padding: 10px;
`;

const CardInfoBox = styled.div`
  background: ${({ theme }) => Color(theme.colors.quaternary).alpha(0.3).string()};
  margin: 0 10px 10px 10px;
  padding: 5px;
  flex-grow: 1;
  border-radius: 8px;
  height: 90px;
`;

const CardDescription = styled.div`
  height: 90px;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 0.9rem;
  color: ${({ theme }) => theme.colors.black};
  text-align: center;
  text-shadow: 0 0 3px rgba(0, 0, 0, 0.5);
`;
