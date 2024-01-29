import React from 'react';

interface PaginationProps {
  pageGroupIndex: number;
  setPageGroupIndex: (pageGroupIndex: number) => void;
  currentPage: number;
  setCurrentPage: (currentPage: number) => void;
}

// 공통 페이지네이션
const Pagination = ({
  pageGroupIndex,
  setPageGroupIndex,
  currentPage,
  setCurrentPage,
}: PaginationProps) => {

  const pageGroupSize = 5;
  const pages = [];
  for (
    let i = 1 + pageGroupSize * pageGroupIndex;
    i <= pageGroupSize * (pageGroupIndex + 1);
    i++
  ) {
    pages.push(
      <button
        key={i}
        onClick={() => setCurrentPage(i)}
        style={{ fontWeight: currentPage === i ? 'bold' : 'normal' }}
      >
        {i}
      </button>,
    );
  }

  return (
    <div>
      <button
        onClick={() => setPageGroupIndex(pageGroupIndex - 1)}
        disabled={pageGroupIndex === 0}
      >
        {'<'}
      </button>
      {pages}
      <button onClick={() => setPageGroupIndex(pageGroupIndex + 1)}>{'>'}</button>
    </div>
  );
};

export default Pagination;
