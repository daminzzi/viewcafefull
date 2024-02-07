import React, { useEffect, useState, Fragment } from 'react';
import getMessage from '../../services/message/getMessage';
import postReadMessage from '../../services/message/postReadMessage';
import MessageDetailModal from '../../components/message/MessageDetailModal';
import Pagination from '../../components/common/Pagination';
import getReadMessage from '../../services/message/getReadMessage';
import MessageSimple from '../../components/message/MessageSimple';
import useUserStore from '../../stores/UserStore';
import { ShowCheckBox, HiddenCheckBox } from '../../components/common/CheckBox';
import Title from '../../components/common/Title';
import { Button } from '../../components/common/Buttons';
import { black, failed, success, white } from '../../assets/styles/palettes';
import styled from 'styled-components';
import Line from '../../components/common/Line';
import Input from '../../components/common/Input';
import search from '../../assets/images/search.png';
import FlexRowContainer from '../../components/common/FlexRowContainer';
import FlexColContainer from '../../components/common/FlexColContainer';

// 보호자 - 받은 메세지 페이지별 조회

function FamilyMessage() {
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [keyword, setKeyword] = useState<string>();
  const [messagesData, setMessagesData] = useState<MessagesResponse | null>(
    null,
  );
  const [selectedMessage, setSelectedMessage] = useState<Message | null>(null);
  const [checkedMessages, setCheckedMessages] = useState<Message[]>([]);
  const { user } = useUserStore();
  const pageGroupSize = 5;

  useEffect(() => {
    if (selectedMessage === null) {
      const fetchData = async () => {
        if (user) {
          const res = await getMessage(currentPage, keyword);
          setMessagesData(res);
        }
      };
      fetchData();
    }
  }, [currentPage, keyword, selectedMessage]);

  // 메세지 데이터 로딩 중
  if (!messagesData) {
    return <div>Now Loading...</div>;
  }

  // 메세지들 불러오기
  function renderMessages() {
    if (!messagesData) {
      return null;
    }
    const messageList = [];
    for (let i = 0; i < messagesData.messages.length; i++) {
      const message = messagesData.messages[i];
      const isChecked = checkedMessages.some((m) => m.id === message.id);
      messageList.push(
        <Fragment key={message.id}>
          <FlexRowContainer
            $margin="10px 0 0 0"
            $padding="0 0 7px 0"
            $position="relative"
            $justifyContent="stretch"
            $alignItems="stretch"
          >
            <SubContainer>
              <HiddenCheckBox
                id={`message-checkbox-${message.id}`}
                checked={isChecked}
                onChange={(e) => handleCheckboxChange(e, message)}
              />
              <ShowCheckBox
                htmlFor={`message-checkbox-${message.id}`}
                isChecked={isChecked}
              />
            </SubContainer>
            <MessageSimple openModal={openModal} message={message} />
          </FlexRowContainer>

          <hr />
        </Fragment>,
      );
    }
    return messageList;
  }

  // 메세지 검색
  function handleSearch(e: React.FormEvent) {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const input = form.elements.namedItem('keyword') as HTMLInputElement;
    setKeyword(input.value);
    setCurrentPage(1);
  }

  // 체크박스의 상태가 변경될 때
  function handleCheckboxChange(
    e: React.ChangeEvent<HTMLInputElement>,
    message: Message,
  ) {
    if (e.target.checked) {
      // 새로 체크된다면 목록에 추가
      setCheckedMessages([...checkedMessages, message]);
    } else {
      // 체크가 해제된다면 목록에서 제거
      // 체크 해제된 message를 제외한 새 배열을 생성
      setCheckedMessages(checkedMessages.filter((m) => m.id !== message.id));
    }
  }

  // 선택 읽기 버튼 눌렀을 때
  async function handleReadClick() {
    // 체크된 메시지 각각에 대해 postReadMessage 호출
    const promises = [];
    for (let i = 0; i < checkedMessages.length; i++) {
      promises.push(postReadMessage(checkedMessages[i].id));
    }

    try {
      // 모든 메세지의 읽음 처리가 완료될 때까지 대기
      await Promise.all(promises);
      setMessagesData((prev) => {
        if (!prev) return null;
        // 새로운 메시지 목록(updatedMessages)생성
        const updatedMessages = [];
        for (let i = 0; i < prev.messages.length; i++) {
          const message = prev.messages[i];
          if (
            checkedMessages.some(
              (checkedMessage) => checkedMessage.id === message.id,
            )
          ) {
            // 현재 가리키고 있는 메세지가 체크된 메세지인 경우 읽음 처리
            updatedMessages.push({ ...message, isRead: true });
          } else {
            // 그대로 유지
            updatedMessages.push(message);
          }
        }
        // 이전 메세지 목록 기존 정보 유지하고, 메세지 목록 새롭게 업데이트해서 반환
        return { ...prev, messages: updatedMessages };
      });
    } catch (error) {
      console.error('오류:', error);
    }
    // 체크함 초기화
    setCheckedMessages([]);
  }

  // 상세보기 할 메세지 선택(더미테스트코드)
  // function openModal(message: Message) {
  //   setSelectedMessage(message);
  // }

  // 모달 클릭시 읽음확인 처리 + 단일상세조회
  async function openModal(message: Message) {
    const updatedMessage = await getReadMessage(message.id);
    if (updatedMessage) {
      setSelectedMessage(updatedMessage);
    }
  }

  // 모달 닫히면 메세지 선택 해제
  function closeModal() {
    setSelectedMessage(null);
  }

  return (
    <div>
      <Title icon="message">메세지</Title>
      <Button
        $bgColor={success}
        $width="98px"
        $padding="9px"
        $color={white}
        onClick={handleReadClick}
      >
        선택 읽기
      </Button>
      <FlexColContainer $alignItems="end" $justifyContent="stretch">
        <SearchForm onSubmit={handleSearch}>
          <Input
            $width="195px"
            type="text"
            name="keyword"
            placeholder="검색어를 입력하세요."
          />
          <SearchButton type="submit"></SearchButton>
        </SearchForm>
      </FlexColContainer>

      <SubContainer>
        <UnReadMsgs>{messagesData.unreadMsgs}</UnReadMsgs>
        <span>/{messagesData.sum} 안 읽음</span>
      </SubContainer>
      <Line $borderColor={black} />

      {renderMessages()}

      {selectedMessage && (
        <MessageDetailModal
          message={selectedMessage}
          onClose={closeModal}
          userId={user ? user.id : null}
        />
      )}

      <Pagination
        pageGroupSize={pageGroupSize}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPage={messagesData.pageNum}
      />
    </div>
  );
}

export default FamilyMessage;

const UnReadMsgs = styled.span`
  color: ${failed};
`;

const SubContainer = styled.div`
  margin: 0.75rem;
`;

const SearchForm = styled.form`
  position: relative;
  width: 210px;
`;

const SearchButton = styled.button`
  position: absolute;
  margin-right: 13px;
  right: 0;
  top: 0;
  width: 30px;
  height: 100%;
  background-image: url(${search});
  background-repeat: no-repeat;
  background-position: center;
  background-size: 70%;
  border: none;
  background-color: transparent;
  cursor: pointer;
`;
