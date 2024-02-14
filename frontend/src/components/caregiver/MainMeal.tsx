import React, { useState, useEffect, useRef } from 'react';
import useHealthStore from '../../stores/HealthStore';
import useCareGiverMainStore from '../../stores/CaregiverMainStore';
import ContentsContainer from '../common/ContentsContainer';
import getMeal from '../../services/health/getMeal';
import postMeal from '../../services/health/postMeal';
import ImageFrame from '../common/ImageFrame';
import styled, { css } from 'styled-components';
import {
  main3,
  white,
  gray,
  success,
  deep,
} from '../../assets/styles/palettes';
import { Button, RoundedButton } from '../../components/common/Buttons';
import Title from '../common/Title';
import FlexRowContainer from '../common/FlexRowContainer';

const SaveButton = styled(Button)<{ $actived: string | null }>`
  background-color: ${(props) => (props.$actived ? success : gray)};
  width: auto;
  padding: 0.5rem 1.5rem;
  bgcolor: ${success};
  color: ${white};
  fontsize: 1rem;
  cursor: ${(props) => (props.$actived ? 'pointer' : 'default')};
`;

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
  object-fit: cover;
`;

const BlankButton = styled.button`
  padding: 0;
  border: none;
  background: none;
  cursor: pointer;
  width: 100%;
  height: 100%;
  color: ${main3};
`;

const InputContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  box-shadow: 0 0 0 0.1rem ${deep};
  position: relative;
  aspect-ratio: 4 / 3;
  border-radius: 10px;
  overflow: hidden;
`;

const TabButton = styled(RoundedButton)<{ $isSelected: boolean }>`
  color: ${deep};
  font-size: 0.9rem;
  width: 4rem;
  height: 1.75rem;
  border-radius: 2rem;
  background-color: ${white};
  box-shadow: 0.1rem 0.1rem 0.2rem ${deep};
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;

  ${(props) =>
    props.$isSelected &&
    css`
      box-shadow: 0 0 0 0.1rem ${deep};
    `}
`;

function dateToString(date: Date): string {
  const year: number = date.getFullYear();
  const month: string = (date.getMonth() + 1).toString().padStart(2, '0');
  const day: string = date.getDate().toString().padStart(2, '0');

  return `${year}-${month}-${day}`;
}

function dateToString2(date: Date): string {
  const year: number = date.getFullYear();
  const month: string = (date.getMonth() + 1).toString().padStart(2, '0');
  const day: string = date.getDate().toString().padStart(2, '0');

  return `${year}${month}${day}`;
}

function isSelected(curr: string, state: string) {
  return curr === state;
}

function MainMeal() {
  const { morning, lunch, dinner, setMeal } = useCareGiverMainStore();
  const { selectedDate } = useHealthStore();
  const [time, setTime] = useState<string>('아침');
  const [imageSrc, setImageSrc] = useState('');
  const imageInput = useRef<HTMLInputElement>(null);

  async function handleSubmit(e: React.MouseEvent) {
    e.preventDefault();
    if (selectedDate !== null && imageSrc) {
      const date = dateToString2(selectedDate);
      const formData = new FormData();
      formData.append('day', date);
      formData.append('time', time);
      const files = (imageInput.current as HTMLInputElement).files as FileList;
      formData.append('image', files[0]);
      await postMeal(formData);
      mealGet();
      setImageSrc('');
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
      case '아침':
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

      case '점심':
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

      case '저녁':
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
    if (imageInput?.current?.value) {
      imageInput.current.value = '';
    }
    setImageSrc('');
    mealGet();
  }, [selectedDate]);

  return (
    <ContentsContainer>
      <HiddenInput
        type="file"
        id="image"
        accept="image/*"
        ref={imageInput}
        onChange={(event) => {
          handleChange(event);
        }}
      />
      <Title icon="meal">식단</Title>
      <FlexRowContainer $justifyContent="space-evenly" $margin="0 0 2vh 0">
        <TabButton
          $isSelected={isSelected(time, '아침')}
          onClick={() => {
            setTime('아침');
          }}
        >
          아침
        </TabButton>
        <TabButton
          $isSelected={isSelected(time, '점심')}
          onClick={() => {
            setTime('점심');
          }}
        >
          점심
        </TabButton>
        <TabButton
          $isSelected={isSelected(time, '저녁')}
          onClick={() => {
            setTime('저녁');
          }}
        >
          저녁
        </TabButton>
      </FlexRowContainer>
      <ImgWrapper>{renderMeal()}</ImgWrapper>
      <FlexRowContainer $justifyContent="end" $width="90%">
        <SaveButton
          $actived={imageSrc}
          $margin="2vh 0 1vh 0"
          onClick={(e) => {
            if (imageSrc) {
              handleSubmit(e);
            }
          }}
        >
          저장
        </SaveButton>
      </FlexRowContainer>
    </ContentsContainer>
  );
}

export default MainMeal;
