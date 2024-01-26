import React from 'react';
// import styled from 'styled-components';
import { ReactComponent as ChevronDown } from '../../assets/icons/chevron-down.svg';
import FlexRowContainer from '../common/FlexRowContainer';

// const CommentDiv = styled(FlexRowContainer)`
// `;

function Comment() {
  return (
    <FlexRowContainer $justifyContent="space-between" $width="90%">
      <span>코멘트</span>
      <ChevronDown />
    </FlexRowContainer>
  );
}

export default Comment;
