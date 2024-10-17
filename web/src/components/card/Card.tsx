import React from 'react';
import styled from 'styled-components';
import { Servico } from '../../types';

interface CardProps {
  servico: Servico;
}

const Card: React.FC<CardProps> = ({ servico }) => {
  return (
    <CardContainer>
      <CardHeader>
        <CardTitle>{servico.nome}</CardTitle>
        {servico.porta && <CardPort>{servico.porta}</CardPort>}
      </CardHeader>
      <CardImageContainer>
        {servico.arquivo ? (
          <CardImage src={`/api/arquivos/${servico.arquivo}`} alt={servico.nome} />
        ) : (
          <CardImagePlaceholder>No Image</CardImagePlaceholder>
        )}
      </CardImageContainer>
      <CardDescription>{servico.descricao}</CardDescription>
    </CardContainer>
  );
};

export default Card;

const CardContainer = styled.div`
  width: 250px;
  background-color: #f8f8f8;
  border: 2px solid #dcdcdc;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  margin: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const CardHeader = styled.div`
  display: flex;
  justify-content: space-between;
  background-color: #ffd700; /* Cor inspirada no estilo Pokémon */
  padding: 10px;
  font-weight: bold;
  font-size: 1.1rem;
`;

const CardTitle = styled.span`
  color: #333;
`;

const CardPort = styled.span`
  color: #333;
`;

const CardImageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 150px;
  background-color: #f0f0f0;
`;

const CardImage = styled.img`
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
`;

const CardImagePlaceholder = styled.div`
  font-size: 0.9rem;
  color: #888;
`;

const CardDescription = styled.div`
  padding: 10px;
  text-align: center;
  font-size: 0.9rem;
  color: #555;
`;
