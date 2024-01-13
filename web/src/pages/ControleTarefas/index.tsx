import React from 'react';
import * as C from './styles';
import ListaTarefas from '../../components/ListaTarefas';

const ControleTarefas: React.FC = () => {
  return (
    <C.Container>
      <C.Body>
        <ListaTarefas />
      </C.Body>
    </C.Container>
  );
};

export default ControleTarefas;
