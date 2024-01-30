import React from 'react';

type PaginationProps = {
  startPage: number;
  setStartPage: (pageGroupIndex: number) => void;
  currentPage: number;
  setCurrentPage: (currentPage: number) => void;
  totalPage: number;
};

// 공통 페이지네이션
function Pagination({startPage, setStartPage, currentPage, setCurrentPage, totalPage}: PaginationProps) {
  const pageGroupSize = 5;

  function showPages() {
    const pages = [];
    for (
      let i = 1 + pageGroupSize * startPage;
      i <= Math.min(pageGroupSize * (startPage + 1), totalPage);
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
    return pages;
  }

  return (
    <div>
      <button
        onClick={() => setStartPage(Math.max(startPage - 1, 0))}
        disabled={startPage === 0}
      >
        {'<'}
      </button>
      {showPages()}
      <button
        onClick={() =>
          setStartPage(
            Math.min(
              startPage + 1,
              Math.floor((totalPage - 1) / pageGroupSize),
            ),
          )
        }
        disabled={
          startPage >= Math.floor((totalPage - 1) / pageGroupSize)
        }
      >
        {'>'}
      </button>
    </div>
  );
}

export default Pagination;
