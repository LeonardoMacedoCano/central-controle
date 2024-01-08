export type Tarefa = {
    id: number;
    idCategoria: number;
    titulo: string;
    descricao: string;
    dataInclusao: Date;
    dataPrazo: Date;
    finalizado: Boolean;
  }