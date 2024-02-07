import React from 'react';
import ImageFrame from '../common/ImageFrame';
import styled from 'styled-components';
import { ReactComponent as PillFill } from '../../assets/icons/pillFill.svg';
import { white, success, gray } from '../../assets/styles/palettes';
// import FlexColContainer from '../common/FlexColContainer';
import FlexRowContainer from '../common/FlexRowContainer';
import { RoundedButton } from '../common/Buttons';

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
`;

type Props = {
  time: string;
  src: string;
  isMedicine: boolean;
};

function MealMedicineImage({ time, src, isMedicine }: Props) {
  const color = isMedicine ? success : gray;
  return (
    <Container>
      <RoundedButton $width="20%" $height="1.5rem">
        {time}
      </RoundedButton>
      <Wrapper>
        <ImageFrame src={src} alt={time} $size="100%"></ImageFrame>
        <MedicineDiv>
          <PillFill color={color} />
        </MedicineDiv>
      </Wrapper>
    </Container>
  );
}

export default MealMedicineImage;
