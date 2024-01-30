import React from 'react';
import styled from 'styled-components';
import { ReactComponent as ChevronRight } from '../../assets/icons/chevron-right.svg';
import { ReactComponent as ChevronLeft } from '../../assets/icons/chevron-left.svg';
import { white, success, black } from '../../assets/styles/palettes';

type PaginationProps = {
  startPage: number;
  setStartPage: (setStartPage: number) => void;
  currentPage: number;
  setCurrentPage: (currentPage: number) => void;
  totalPage: number;
};

// 공통 페이지네이션
function Pagination({
  startPage,
  setStartPage,
  currentPage,
  setCurrentPage,
  totalPage,
}: PaginationProps) {
  const pageGroupSize = 5;

  function showPages() {
    const pages = [];
    for (
      let i = 1 + pageGroupSize * startPage;
      i <= Math.min(pageGroupSize * (startPage + 1), totalPage);
      i++
    ) {
      pages.push(
        <PageButton
          key={i}
          onClick={() => setCurrentPage(i)}
          active={currentPage === i}
        >
          {i}
        </PageButton>,
      );
    }
    return pages;
  }

  return (
    <Container>
      <ChevronButton
        onClick={() => setStartPage(Math.max(startPage - 1, 0))}
        disabled={startPage === 0}
      >
        <ChevronLeft width="35px" />
      </ChevronButton>
      <PagesContainer>{showPages()}</PagesContainer>
      <ChevronButton
        onClick={() =>
          setStartPage(
            Math.min(
              startPage + 1,
              Math.floor((totalPage - 1) / pageGroupSize),
            ),
          )
        }
        disabled={startPage >= Math.floor((totalPage - 1) / pageGroupSize)}
      >
        <ChevronRight width="35px" />
      </ChevronButton>
    </Container>
  );
}

export default Pagination;

const ChevronButton = styled.button`
  cursor: pointer;
  border: none;
  background: none;
`;

const PageButton = styled.button<{ active?: boolean }>`
  width: 30px;
  height: 30px;
  margin: 1%;
  font-size: 0.9rem;
  box-sizing: border-box;
  border: none;
  cursor: pointer;
  text-align: center;
  background-color: ${(props) => (props.active ? success : white)};
  color: ${(props) => (props.active ? white : { black })};
`;

const Container = styled.div`
  display: flex;
  justify-content: center;
  padding-bottom: 10px;
`;

const PagesContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 200px;
  padding-top: 4px;
`;
