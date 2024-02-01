import React, { useState, useEffect } from 'react';
import PicByDate from '../../components/gallery/PicByDate';
import getGallery from '../../services/gallery/getGalleryInfo';
import Title from '../../components/common/Title';
import { ReactComponent as Spinner } from '../../assets/icons/spinner2.svg';

function FamilyGallery() {
  const [galleryInfo, setGalleryInfo] = useState<Array<Data>>([]);     // 사진 정보
  const [page, setPage] = useState(1);                                 // 다음에 읽을 페이지
  const [isLoading, setIsLoading] = useState<boolean>(false);          // 로딩 중 상태
  const [target, setTarget] = useState<HTMLElement | null>(null);      // api요청 타겟
  const [isCallable, setIsCallable] = useState<boolean>(true);         // 더 가져올 이미지가 있는지

  // 날짜 별 사진 렌더링 함수
  function renderPictures(galleryInfo: Array<Data>) {
    const result: Array<JSX.Element> = [];
    galleryInfo.forEach((info, index) => {
      result.push(<PicByDate key={index} galleryInfo={info} />);
    });

    return result;
  }

  // 사진 정보 가져오는 함수
  async function getInfo() {
    setIsLoading(true);
    const response = getGallery(page);
    await new Promise((resolve) => setTimeout(resolve, 1500));
    setGalleryInfo((prev) => {
      if (response.data.length > 0) {
        return prev.concat(response.data);
      }
      setIsCallable(false);
      return prev.concat([]);
    });
    setIsLoading(false);
    setPage((prev) => prev + 1);
    console.log(page)
  }

   // intersection시 사용되는 callback 함수
  const onIntersect: IntersectionObserverCallback = async function (
    [entry],
    observer,
  ) {
    if (entry.isIntersecting && !isLoading) {
      observer.disconnect();
      await getInfo();
      console.log(entry)
    }
  };

  // 페이지가 바뀌면 intersection callback 함수 업데이트
  useEffect(() => {
    if (target && isCallable) {
      const observer = new IntersectionObserver(onIntersect, { threshold: 1 });
      observer.observe(target);
    }
  }, [page])

  // 처음에 target이 null -> element 로 바뀔때 intersection 
  useEffect(() => {
    if (target) {
      const observer = new IntersectionObserver(onIntersect, { threshold: 1 });
      observer.observe(target);
    }
  }, [target]);

  return (
    <div>
      <Title icon="gallery">갤러리</Title>
      {renderPictures(galleryInfo)}
      <div ref={setTarget}>               {/* target이 보이면 api 요청 */}
        {isLoading && <Spinner width="20%" />}
      </div>
    </div>
  );
}

export default FamilyGallery;
