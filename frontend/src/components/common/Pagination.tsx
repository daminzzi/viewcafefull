import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { ReactComponent as ChevronRight } from '../../assets/icons/chevronRight.svg';
import { ReactComponent as ChevronLeft } from '../../assets/icons/chevronLeft.svg';
import { white, success, black } from '../../assets/styles/palettes';
import FlexRowContainer from './FlexRowContainer';

type PaginationProps = {
  currentPage: number;
  setCurrentPage: (currentPage: number) => void;
  totalPage: number;
  pageGroupSize: number;
};

// 공통 페이지네이션
function Pagination({
  currentPage,
  setCurrentPage,
  totalPage,
  pageGroupSize,
}: PaginationProps) {
  // 렌더링하는 함수
  const [startPage, setStartPage] = useState(1);
  const [endPage, setEndPage] = useState(pageGroupSize);

  useEffect(() => {
    let start = Math.max(1, currentPage - Math.floor(pageGroupSize / 2));
    const end = Math.min(start + pageGroupSize - 1, totalPage);
    if (
      end - start + 1 < pageGroupSize &&
      start - pageGroupSize + (end - start + 1) >= 1
    ) {
      start -= pageGroupSize - (end - start + 1);
    }
    setStartPage(start);
    setEndPage(end);
  }, [currentPage, totalPage, pageGroupSize]);

  function showPages() {
    const pages = [];
    for (let i = startPage; i <= endPage; i++) {
      pages.push(
        <PageButton
          key={i}
          onClick={() => handlePageChange(i)}
          $active={currentPage === i}
        >
          {i}
        </PageButton>,
      );
    }
    return pages;
  }

  // 현재 페이지 이동 처리 함수
  function handlePageChange(pageNumber: number) {
    setCurrentPage(pageNumber);
  }

  // 이전 페이지 그룹으로 이동
  function handlePrevClick() {
    if (startPage > 1) {
      setCurrentPage(Math.max(startPage - pageGroupSize, 1));
    }
  }

  // 다음 페이지 그룹으로 이동
  function handleNextClick() {
    if (endPage < totalPage) {
      setCurrentPage(startPage + pageGroupSize);
    }
  }

  return (
    <FlexRowContainer
      $justifyContent="center"
      $padding="0 0 10px 0"
      $width="auto"
    >
      <ChevronButton onClick={handlePrevClick} disabled={startPage === 1}>
        <ChevronLeft width="35px" />
      </ChevronButton>
      <PagesContainer>{showPages()}</PagesContainer>
      <ChevronButton onClick={handleNextClick} disabled={endPage === totalPage}>
        <ChevronRight width="35px" />
      </ChevronButton>
    </FlexRowContainer>
  );
}

export default Pagination;

const ChevronButton = styled.button`
  cursor: pointer;
  border: none;
  background: none;
  padding-top: 7px;
`;

const PageButton = styled.button<{ $active?: boolean }>`
  width: 30px;
  height: 30px;
  margin: 1%;
  font-size: 0.9rem;
  box-sizing: border-box;
  border: none;
  cursor: pointer;
  text-align: center;
  background-color: ${(props) => (props.$active ? success : white)};
  color: ${(props) => (props.$active ? white : { black })};
`;

const PagesContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 3%;
  width: 20%;
  padding-top: 2px;

  @media (max-width: 600px) {
    width: 50%;
  }
`;
