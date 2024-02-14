import React, { useState, useEffect, useRef } from 'react';
import useHealthStore from '../../stores/HealthStore';
import useCareGiverMainStore from '../../stores/CaregiverMainStore';
import ContentsContainer from '../common/ContentsContainer';
import getMeal from '../../services/health/getMeal';
import postMeal from '../../services/health/postMeal';
import ImageFrame from '../common/ImageFrame';
import styled from 'styled-components';
import { main3, white } from '../../assets/styles/palettes';
import { RoundedButton } from '../../components/common/Buttons';

const ImgWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 60%;
  aspect-ratio: 4 / 3;
`;

const HiddenInput = styled.input`
  display: none;
`;

const InnerImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: contain;
`;

const BlankButton = styled.button`
  padding: 0;
  border: none;
  background: none;
  cursor: pointer;
  width: 100%;
  hegith: 100%;
  color: ${main3};
`;

const InputContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 90%;
  border: 2px solid ${main3};
  position: relative;
  aspect-ratio: 4 / 3;
  margin: 2vh 0;
  border-radius: 10px;
  overflow: hidden;
`;

function dateToString(date: Date): string {
  const year: number = date.getFullYear();
  const month: string = (date.getMonth() + 1).toString().padStart(2, '0');
  const day: string = date.getDate().toString().padStart(2, '0');

  return `${year}-${month}-${day}`;
}

function MainMeal() {
  const { morning, lunch, dinner, setMeal } = useCareGiverMainStore();
  const { selectedDate } = useHealthStore();
  const [time, setTime] = useState<string>('morning');
  const [imageSrc, setImageSrc] = useState('');
  const imageInput = useRef<HTMLInputElement>(null);

  function handleSubmit(e: React.MouseEvent) {
    e.preventDefault();
    if (selectedDate !== null && imageSrc) {
      const date = dateToString(selectedDate);
      const formData = new FormData();
      formData.append('date', date);
      formData.append('time', time);
      const files = (imageInput.current as HTMLInputElement).files as FileList;
      formData.append('image', files[0]);
      postMeal(formData);
    } else {
      alert('사진을 첨부해주세요.');
    }
  }

  async function mealGet() {
    if (selectedDate !== null) {
      const date = dateToString(selectedDate);
      const response = await getMeal(date);
      setMeal(response.images);
    }
  }

  function handleChange(e: React.ChangeEvent) {
    const targetFiles = (e.target as HTMLInputElement).files as FileList;
    const target = URL.createObjectURL(targetFiles[0]);
    setImageSrc(target);
  }

  function handleUpload() {
    imageInput?.current?.click();
  }

  function renderMeal() {
    switch (time) {
      case 'morning':
        if (morning !== null) {
          return (
            <ImageFrame
              src={morning}
              alt="morning"
              $size="100%"
              $aspectRatio="4 / 3"
            />
          );
        }
        break;

      case 'lunch':
        if (lunch !== null) {
          return (
            <ImageFrame
              src={lunch}
              alt="lunch"
              $size="100%"
              $aspectRatio="4 / 3"
            />
          );
        }
        break;

      case 'dinner':
        if (dinner !== null) {
          return (
            <ImageFrame
              src={dinner}
              alt="dinner"
              $size="100%"
              $aspectRatio="4 / 3"
            />
          );
        }
        break;

      default:
        break;
    }
    return (
      <InputContainer>
        <HiddenInput
          type="file"
          id="image"
          accept="image/*"
          ref={imageInput}
          onChange={(event) => {
            handleChange(event);
          }}
        />
        {imageSrc ? null : (
          <BlankButton onClick={() => handleUpload()}>
            <RoundedButton
              as="div"
              $bgColor={main3}
              $color={white}
              $fontSize="1rem"
              $borderRadius="2rem"
              $width="5rem"
              $padding="0.75rem 1rem"
              $margin="auto"
            >
              사진 첨부
            </RoundedButton>
          </BlankButton>
        )}
        {imageSrc && <InnerImage src={imageSrc} alt="preview" />}
      </InputContainer>
    );
  }

  useEffect(() => {
    mealGet();
  }, [selectedDate]);

  return (
    <ContentsContainer>
      <p>식단</p>
      <button onClick={(e) => {handleSubmit(e)}}>저장</button>
      <button
        onClick={() => {
          setTime('morning');
          setImageSrc('');
        }}
      >
        아침
      </button>
      <button
        onClick={() => {
          setTime('lunch');
          setImageSrc('');
        }}
      >
        점심
      </button>
      <button
        onClick={() => {
          setTime('dinner');
          setImageSrc('');
        }}
      >
        저녁
      </button>
      <ImgWrapper>{renderMeal()}</ImgWrapper>
    </ContentsContainer>
  );
}

export default MainMeal;
