import { Component } from 'react';
import * as C from './styles';
import { PagedResponse } from '../../types/PagedResponse';
import { FaAngleDoubleLeft, FaAngleDoubleRight, FaAngleLeft, FaAngleRight } from 'react-icons/fa';
import FieldValue from '../fieldvalue/FieldValue';
import { formatarNumeroComZerosAEsquerda } from '../../utils/ValorUtils';
import FlexBox from '../../components/flexbox/FlexBox';

interface SearchPaginationProps {
  page: PagedResponse<any>;
  height?: string;
  width?: string;
  carregarPagina: (indexPagina: number, registrosPorPagina: number) => void;
}

interface SearchPaginationState {
  indexPaginaAtual: number;
  registrosPorPagina: number;
}

class SearchPagination extends Component<SearchPaginationProps, SearchPaginationState> {
  constructor(props: SearchPaginationProps) {
    super(props);

    let registrosPorPagina = this.props.page.size;
    if (registrosPorPagina > this.props.page.totalElements) {
      registrosPorPagina = this.props.page.totalElements;
    }

    this.state = {
      indexPaginaAtual: this.props.page.number,
      registrosPorPagina: registrosPorPagina
    };
  }

  carregarPagina = (indexPagina: number, registrosPorPagina: number) => {
    const { carregarPagina } = this.props;
    this.setState({ indexPaginaAtual: indexPagina, registrosPorPagina: registrosPorPagina });
    carregarPagina(indexPagina, registrosPorPagina);
  };

  handleUpdateRegistrosPorPagina = (value: any) => {
    if (typeof value === 'number') {
      this.setState({ registrosPorPagina: value }, () => {
        this.carregarPagina(0, this.state.registrosPorPagina);
      });
    }
  };

  render() {
    const { page, height, width } = this.props;
    const { indexPaginaAtual, registrosPorPagina } = this.state;

    const numeroPaginaAtual = indexPaginaAtual + 1;
    const numeroUltimaPagina = page.totalPages;
    const isUltimaPagina = numeroPaginaAtual === numeroUltimaPagina;
    const isPrimeiraPagina = indexPaginaAtual === 0;
    const totalRegistros = page.totalElements;

    return (
      <C.SearchPagination height={height} width={width}>
        <FlexBox>
          <FlexBox.Item borderRight width='160px'>
            <FieldValue
              description='Itens por pág: '
              type='number'
              value={formatarNumeroComZerosAEsquerda(registrosPorPagina, 3)}
              inline={true}
              maxHeight={height}
              width='160px'
              inputWidth='50px'
              minValue={1}
              maxValue={totalRegistros}
              editable={true}
              onUpdate={this.handleUpdateRegistrosPorPagina}
            />
          </FlexBox.Item>
          <FlexBox.Item alignCenter>
            <C.ItemContainer>
              <C.Item onClick={this.carregarPrimeiraPagina} disabled={isPrimeiraPagina}>
                <FaAngleDoubleLeft />
              </C.Item>
              <C.Item onClick={this.carregarPaginaAnterior} disabled={isPrimeiraPagina}>
                <FaAngleLeft />
              </C.Item>
              {numeroPaginaAtual} / {numeroUltimaPagina}
              <C.Item onClick={this.carregarProximaPagina} disabled={isUltimaPagina}>
                <FaAngleRight />
              </C.Item>
              <C.Item onClick={this.carregarUltimaPagina} disabled={isUltimaPagina}>
                <FaAngleDoubleRight />
              </C.Item>
            </C.ItemContainer>
          </FlexBox.Item>
          <FlexBox.Item borderLeft width='160px' alignRight>
            <FieldValue
              description='Total de itens: '
              type='number'
              value={formatarNumeroComZerosAEsquerda(totalRegistros, 3)}
              inline={true}
              maxHeight={height}
              width='160px'
              inputWidth='50px'
            />
          </FlexBox.Item>
        </FlexBox>
      </C.SearchPagination>
    );
  }

  private carregarPrimeiraPagina = () => {
    if (this.state.indexPaginaAtual > 0) {
      this.carregarPagina(0, this.state.registrosPorPagina);
    }
  };

  private carregarPaginaAnterior = () => {
    if (this.state.indexPaginaAtual > 0) {
      this.carregarPagina(this.state.indexPaginaAtual - 1, this.state.registrosPorPagina);
    }
  };

  private carregarProximaPagina = () => {
    if (this.state.indexPaginaAtual < this.props.page.totalPages - 1) {
      this.carregarPagina(this.state.indexPaginaAtual + 1, this.state.registrosPorPagina);
    }
  };

  private carregarUltimaPagina = () => {
    if (this.state.indexPaginaAtual < this.props.page.totalPages - 1) {
      this.carregarPagina(this.props.page.totalPages - 1, this.state.registrosPorPagina);
    }
  };
}

export default SearchPagination;
