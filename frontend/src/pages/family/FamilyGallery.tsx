import React, { useState, useEffect } from 'react';
import PicByDate from '../../components/gallery/PicByDate';
import getGallery from '../../services/gallery/getGalleryInfo';
import Title from '../../components/common/Title';

function FamilyGallery() {
  const [galleryInfo, setGalleryInfo] = useState<Array<Data>>([]);
  // const [page, setPage] = useState(0);

  useEffect(() => {
    const response = getGallery(1);
    if (response !== null) {
      setGalleryInfo(response.data);
      console.log(response);
    }
  });

  function renderPictures(galleryInfo: Array<Data> | null) {
    if (galleryInfo === null) {
      return null;
    }

    const result: Array<JSX.Element> = [];
    galleryInfo.forEach((info, index) => {
      result.push(<PicByDate key={index} galleryInfo={info} />);
    });

    return result;
  }

  return (
    <div>
      <Title icon="gallery">갤러리</Title>
      {renderPictures(galleryInfo)}
    </div>
  );
}

export default FamilyGallery;
