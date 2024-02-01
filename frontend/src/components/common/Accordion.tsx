import React, { useState } from 'react';
import { ReactComponent as ChevronDownIcon } from '../../assets/icons/chevron-down.svg';
import { ReactComponent as ChevronUpIcon } from '../../assets/icons/chevron-up.svg';
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
}

function Accordion({ title, subTitle, suffix, content }: AccordionProps) {
  const [isOpen, setIsOpen] = useState(false);

  const toggleAccordion = () => {
    setIsOpen(!isOpen);
  };

  return (
    <FlexColContainer
      $justifyContent="center"
      $border="2px solid"
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
        <ChevronDownButton onClick={toggleAccordion}>
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
        </ChevronDownButton>
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

const ChevronDownButton = styled.div`
  cursor: pointer;
`;
