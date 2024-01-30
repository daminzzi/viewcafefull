import React from 'react';
import ImageFrame from '../common/ImageFrame';
import FlexRowContainer from '../common/FlexRowContainer';
import styled from 'styled-components';

type Props = {
  galleryInfo: Data;
};

const ImagesContainer = styled(FlexRowContainer)`
  flex-wrap: wrap;
  justify-content: start;
  gap: 30px 5%;
`;

function PicByDate({ galleryInfo: { date, thumnail } }: Props) {
  function renderPicture() {
    const result = [];
    for (let i = 0; i < thumnail.length; i++) {
      result.push(
        <ImageFrame
          key={i}
          src={thumnail[i]}
          alt={`${date}${i}`}
          $size="30%"
        />,
      );
    }

    return <ImagesContainer>{result}</ImagesContainer>;
  }

  return (
    <div>
      <p>{date}</p>
      {renderPicture()}
      <hr />
    </div>
  );
}

export default PicByDate;
