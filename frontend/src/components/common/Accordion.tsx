import React, { useState } from 'react';
import { ReactComponent as ChevronDownIcon } from '../../assets/icons/chevronDown.svg';
import { ReactComponent as ChevronUpIcon } from '../../assets/icons/chevronUp.svg';
import styled from 'styled-components';
import FlexColContainer from './FlexColContainer';
import FlexRowContainer from './FlexRowContainer';
import { white, main3 } from '../../assets/styles/palettes';

/*
아코디언 컴포넌트 사용시
for문으로 부르는 content에
Line.ts을 각각의 객체 맨 위에 넣어주면 좋습니다.(FamilyMessage.tsx 참고)
*/

interface AccordionProps {
  title: string;
  subTitle?: string;
  content: React.ReactNode;
  suffix?: string;
  imgUrl?: string;
}

function Accordion({ title, subTitle, suffix, content, imgUrl }: AccordionProps) {
  const [isOpen, setIsOpen] = useState(false);

  const toggleAccordion = () => {
    setIsOpen(!isOpen);
  };

  return (
    <FlexColContainer
      $justifyContent="center"
      $border="4px solid"
      $borderColor={main3}
      $margin="10px"
      $borderRadius="30px"
      $alignItems="stretch"
      $width="auto"
      $backgroundColor={white}
    >
      <FlexRowContainer
        $justifyContent="space-between"
        $padding="15px"
        $width="auto"
      >
        <FlexRowContainer $gap="7px" $padding="5px" $justifyContent="stretch">
          <img src={imgUrl} width={'35px'} height={'35px'} />
          <div>
            <SubTitle>{subTitle}</SubTitle>
            <FlexRowContainer
              onClick={toggleAccordion}
              $justifyContent="stretch"
              $gap="5px"
            >
              {title} <SubTitle>{suffix}</SubTitle>
            </FlexRowContainer>
          </div>
        </FlexRowContainer>
        <ChevronButton onClick={toggleAccordion}>
          {isOpen ? (
            <ChevronUpIcon
              className="chevron-up-icon"
              width="30px"
              height="30px"
            />
          ) : (
            <ChevronDownIcon
              className="chevron-down-icon"
              width="30px"
              height="30px"
            />
          )}
        </ChevronButton>
      </FlexRowContainer>
      {isOpen && content}
    </FlexColContainer>
  );
}

export default Accordion;

const SubTitle = styled.div`
  font-size: 10px;
  align-self: center;
`;

const ChevronButton = styled.div`
  cursor: pointer;
`;
