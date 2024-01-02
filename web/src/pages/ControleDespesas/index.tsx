import React from 'react';
import * as C from './styles';
import ListaDespesas from '../../components/ListaDespesas';

const ControleDespesas: React.FC = () => {
  return (
    <C.Container>
      <C.Body>
        <ListaDespesas />
      </C.Body>
    </C.Container>
  );
};

export default ControleDespesas;
