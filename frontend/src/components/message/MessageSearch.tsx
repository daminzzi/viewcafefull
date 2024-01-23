import React, { useState } from 'react';

// 메세지(제목, 내용)검색창
interface MessageSearchProps {
  onSearch: (searchValue: string) => void;
}

function MessageSearch({ onSearch }: MessageSearchProps) {
  const [searchValue, setSearchValue] = useState('');

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchValue(e.target.value);
    onSearch(e.target.value);
  };

  return (
    <input
      placeholder="검색어를 입력하세요."
      value={searchValue}
      onChange={handleInputChange}
    />
  );
}

export default MessageSearch;
