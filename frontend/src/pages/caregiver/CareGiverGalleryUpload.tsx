import React, { useState, useRef } from 'react';
import useGalleryStore from '../../stores/GalleryStore';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Title from '../../components/common/Title';
import {
  main3,
  success,
  white,
  black,
  gray,
} from '../../assets/styles/palettes';
import FlexColContainer from '../../components/common/FlexColContainer';
import FlexRowContainer from '../../components/common/FlexRowContainer';
import { RoundedButton } from '../../components/common/Buttons';
import postGalleryUpload from '../../services/gallery/postGalleryUpload';

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

const StyledLabel = styled.label`
  width: 90%;
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

function CareGiverGalleryUpload() {
  const imageInput = useRef<HTMLInputElement>(null);
  const [imageSrc, setImageSrc] = useState('');
  const navigate = useNavigate();
  const { reset } = useGalleryStore();

  function handleSubmit(e: React.MouseEvent) {
    e.preventDefault();
    if (imageSrc) {
      const formData = new FormData();
      const files = (imageInput.current as HTMLInputElement).files as FileList;
      formData.append('image', files[0]);
      postGalleryUpload(formData);
      reset();
      navigate('/caregiver/gallery');
    } else {
      alert('사진을 첨부해주세요.');
    }
  }

  function handleUpload() {
    imageInput?.current?.click();
  }

  function handleClear() {
    if (imageInput?.current?.value) {
      imageInput.current.value = '';
      setImageSrc('');
    }
  }

  function handleChange(e: React.ChangeEvent) {
    const targetFiles = (e.target as HTMLInputElement).files as FileList;
    const target = URL.createObjectURL(targetFiles[0]);
    setImageSrc(target);
  }

  function handleClick() {
    navigate('/caregiver/gallery');
  }

  return (
    <div>
      <Title icon="gallery" buttonContents="돌아가기" handleClick={handleClick}>
        갤러리
      </Title>
      <FlexColContainer>
        <StyledLabel htmlFor="image">사진 첨부</StyledLabel>
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
        <FlexRowContainer>
          <RoundedButton
            $bgColor={white}
            $color={black}
            $fontSize="1rem"
            $border={`2px solid ${gray}`}
            $borderRadius="2rem"
            $width="auto"
            $padding="0.5rem 1.5rem"
            onClick={() => {
              handleClear();
            }}
          >
            취소
          </RoundedButton>
          <RoundedButton
            $bgColor={success}
            $color={white}
            $fontSize="1rem"
            $borderRadius="2rem"
            $width="auto"
            $padding="0.5rem 1.5rem"
            onClick={(event) => {
              handleSubmit(event);
            }}
          >
            저장
          </RoundedButton>
        </FlexRowContainer>
      </FlexColContainer>
    </div>
  );
}

export default CareGiverGalleryUpload;
