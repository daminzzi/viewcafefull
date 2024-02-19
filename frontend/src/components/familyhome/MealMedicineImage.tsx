import React from 'react';
import ImageFrame from '../common/ImageFrame';
import styled from 'styled-components';
import { ReactComponent as PillFill } from '../../assets/icons/pillFill.svg';
import { white, success, gray } from '../../assets/styles/palettes';
import FlexRowContainer from '../common/FlexRowContainer';
import { RoundedButton } from '../common/Buttons';
import { useNavigate } from 'react-router';

const Container = styled(FlexRowContainer)`
  width: 90%;
`;

const MedicineDiv = styled.div`
  width: 20%;
  height: 20%;
  position: absolute;
  bottom: 3%;
  right: 3%;
  background-color: ${white};
  border-radius: 5px;
`;

const Wrapper = styled.div`
  width: 45%;
  position: relative;
  cursor: pointer;
`;

type Props = {
  time: string;
  src: string;
  isMedicine: boolean;
};

function MealMedicineImage({ time, src, isMedicine }: Props) {
  const color = isMedicine ? success : gray;
  const navigate = useNavigate();

  function handleClick(image: string): void {
    navigate(`/gallery/detail`, { state: { src: image } });
  }

  return (
    <Container>
      <RoundedButton $width="20%" $height="1.5rem">
        {time}
      </RoundedButton>
      <Wrapper
        onClick={() => {
          handleClick(src);
        }}
      >
        <ImageFrame src={src} alt={time} $size="100%" $aspectRatio="4/3" />
        <MedicineDiv>
          <PillFill color={color} />
        </MedicineDiv>
      </Wrapper>
    </Container>
  );
}

export default MealMedicineImage;
