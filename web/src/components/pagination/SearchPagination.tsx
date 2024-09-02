import { Component } from 'react';
import styled from 'styled-components';
import { 
  FaAngleDoubleLeft,
  FaAngleDoubleRight,
  FaAngleLeft,
  FaAngleRight
} from 'react-icons/fa';
import { PagedResponse } from '../../types';
import { 
  FlexBox,
  Container
} from '../../components';

interface SearchPaginationProps {
  page: PagedResponse<any>;
  height?: string;
  width?: string;
  loadPage: (pageIndex: number, pageSize: number) => void;
}

interface SearchPaginationState {
  currentPageIndex: number;
  pageSize: number;
}

class SearchPagination extends Component<SearchPaginationProps, SearchPaginationState> {
  constructor(props: SearchPaginationProps) {
    super(props);

    let pageSize = this.props.page.size;
    if (pageSize > this.props.page.totalElements) {
      pageSize = this.props.page.totalElements;
    }

    this.state = {
      currentPageIndex: this.props.page.number,
      pageSize: pageSize
    };
  }

  loadPage = (pageIndex: number, pageSize: number) => {
    const { loadPage } = this.props;
    this.setState({ currentPageIndex: pageIndex, pageSize: pageSize });
    loadPage(pageIndex, pageSize);
  };

  render() {
    const { page, height, width } = this.props;
    const { currentPageIndex } = this.state;

    const currentPageNumber = currentPageIndex + 1;
    const lastPageNumber = page.totalPages;
    const isLastPage = currentPageNumber === lastPageNumber;
    const isFirstPage = currentPageIndex === 0;

    return (
      <Container 
        height={height} 
        width={width}
        backgroundColor='transparent'  
      >
        <FlexBox>
          <FlexBox.Item alignCenter>
            <PaginationControls>
              <ControlItem onClick={this.goToFirstPage} disabled={isFirstPage}>
                <FaAngleDoubleLeft />
              </ControlItem>
              <ControlItem onClick={this.goToPreviousPage} disabled={isFirstPage}>
                <FaAngleLeft />
              </ControlItem>
              {currentPageNumber} / {lastPageNumber}
              <ControlItem onClick={this.goToNextPage} disabled={isLastPage}>
                <FaAngleRight />
              </ControlItem>
              <ControlItem onClick={this.goToLastPage} disabled={isLastPage}>
                <FaAngleDoubleRight />
              </ControlItem>
            </PaginationControls>
          </FlexBox.Item>
        </FlexBox>
      </Container>
    );
  }

  private goToFirstPage = () => {
    if (this.state.currentPageIndex > 0) {
      this.loadPage(0, this.state.pageSize);
    }
  };

  private goToPreviousPage = () => {
    if (this.state.currentPageIndex > 0) {
      this.loadPage(this.state.currentPageIndex - 1, this.state.pageSize);
    }
  };

  private goToNextPage = () => {
    if (this.state.currentPageIndex < this.props.page.totalPages - 1) {
      this.loadPage(this.state.currentPageIndex + 1, this.state.pageSize);
    }
  };

  private goToLastPage = () => {
    if (this.state.currentPageIndex < this.props.page.totalPages - 1) {
      this.loadPage(this.props.page.totalPages - 1, this.state.pageSize);
    }
  };
}

export default SearchPagination;

const PaginationControls = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: ${props => props.theme.colors.quaternary};
`;

interface ControlItemProps {
  disabled?: boolean;
}

const ControlItem = styled.li<ControlItemProps>`
  width: 35px;
  height: 100%;
  font-size: 20px;
  color: ${props => props.theme.colors.white};
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: ${props => (props.disabled ? 'not-allowed' : 'pointer')};
  opacity: ${props => (props.disabled ? '0.2' : '1')};

  &:first-child {
    margin-left: 0;
  }

  &:hover {
    color: ${props => (props.disabled ? props.theme.colors.white : props.theme.colors.gray)};
  }
`;
