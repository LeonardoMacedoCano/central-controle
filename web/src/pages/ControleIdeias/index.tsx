import React from 'react';
import * as C from './styles';
import ListaIdeias from '../../components/ListaIdeias';

const ControleIdeias: React.FC = () => {
  return (
    <C.Container>
      <C.Body>
        <ListaIdeias/>
      </C.Body>
    </C.Container>
  );
};

export default ControleIdeias;