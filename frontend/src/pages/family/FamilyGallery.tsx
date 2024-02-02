import React, { useState, useEffect } from 'react';
import PicByDate from '../../components/gallery/PicByDate';
import getGallery from '../../services/gallery/getGalleryInfo';
import Title from '../../components/common/Title';
import { ReactComponent as Spinner } from '../../assets/icons/spinner2.svg';
import useGalleryStore from '../../stores/GalleryStore';

function FamilyGallery() {
  const {
    galleryInfo,
    page,
    isCallable,
    isLoading,
    switchIsLoading,
    switchIsCallable,
    addPage,
    addInfo,
  } = useGalleryStore();
  const [target, setTarget] = useState<HTMLElement | null>(null); // api요청 타겟

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
    switchIsLoading();
    const response = getGallery(page);
    await new Promise((resolve) => setTimeout(resolve, 1500));
    if (response.data.length > 0) {
      addInfo(response.data);
    } else if (response.data.length <= 0) {
      switchIsCallable();
    }
    switchIsLoading();
    addPage();
  }

  // intersection시 사용되는 callback 함수
  const onIntersect: IntersectionObserverCallback = async function (
    [entry],
    observer,
  ) {
    if (entry.isIntersecting && !isLoading) {
      observer.disconnect();
      getInfo();
      console.log(entry);
    }
  };

  // 페이지가 바뀌면 intersection callback 함수 업데이트
  useEffect(() => {
    if (target && isCallable) {
      const observer = new IntersectionObserver(onIntersect, { threshold: 1 });
      observer.observe(target);
    }
  }, [page]);

  // 처음에 target이 null -> element 로 바뀔때 intersection
  useEffect(() => {
    if (target && isCallable) {
      const observer = new IntersectionObserver(onIntersect, { threshold: 1 });
      observer.observe(target);
    }
  }, [target]);

  return (
    <div>
      <Title icon="gallery" buttonContents='버튼'>갤러리</Title>
      {renderPictures(galleryInfo)}
      <div ref={setTarget}>
        {/* target이 보이면 api 요청 */}
        {isLoading && <Spinner width="20%" />}
      </div>
    </div>
  );
}

export default FamilyGallery;
