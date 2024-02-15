import React, { useEffect, useState, Fragment } from 'react';
import getMessage from '../../services/message/getMessage';
import postReadMessage from '../../services/message/postReadMessage';
import getReadMessage from '../../services/message/getReadMessage';
import MessageDetailModal from '../../components/message/MessageDetailModal';
import Pagination from '../../components/common/Pagination';
import MessageSimple from '../../components/message/MessageSimple';
import MessageReport from '../../components/message/MessageReport';
import useUserStore from '../../stores/UserStore';
import styled from 'styled-components';
import Title from '../../components/common/Title';
import Line from '../../components/common/Line';
import Input from '../../components/common/Input';
import { Button } from '../../components/common/Buttons';
import search from '../../assets/images/search.png';
import FlexRowContainer from '../../components/common/FlexRowContainer';
import FlexColContainer from '../../components/common/FlexColContainer';
import { ReactComponent as Spinner } from '../../assets/icons/spinner.svg';
import { black, failed, success, white } from '../../assets/styles/palettes';

// 보호자 - 받은 메세지 페이지별 조회

function FamilyMessage() {
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [keyword, setKeyword] = useState<string>();

  const [pageNum, setPageNum] = useState<number>(0);
  const [messages, setMessages] = useState<Message[]>();
  const [unreadMsgs, setUnreadMsgs] = useState<number>();
  const [sumMsgs, setSumMsgs] = useState<number>();
  const [selectedMessage, setSelectedMessage] = useState<Message | null>(null);
  const [checkedMessages, setCheckedMessages] = useState<Message[]>([]);
  const { user } = useUserStore();
  const pageGroupSize = 5;

  function parseContent(content: string): ReportMessage | string {
    try {
      const jsonContent = JSON.parse(content);
      return jsonContent;
    } catch (error) {
      // JSON 파싱에 실패하면 문자열 그대로 반환
      return content;
    }
  }

  function isReportMessage(obj: string | ReportMessage): obj is ReportMessage {
    return (
      typeof obj === 'object' &&
      'year' in obj &&
      'month' in obj &&
      'targetId' in obj &&
      'targetName' in obj
    );
  }

  useEffect(() => {
    if (selectedMessage === null) {
      const fetchData = async () => {
        if (user) {
          const res = await getMessage(currentPage, keyword);
          if (res) {
            const messageList: Message[] = [];
            res.messages.forEach((message) => {
              const parsedContent =
                typeof message.content === 'string'
                  ? parseContent(message.content)
                  : message.content;
              if (isReportMessage(parsedContent)) {
                messageList.push({ ...message, content: parsedContent });
              } else {
                messageList.push(message);
              }
            });
            setMessages(messageList);
            setPageNum(res.pageNum);
            setUnreadMsgs(res.unreadMsgs);
            setSumMsgs(res.sum);
          }
        }
      };
      fetchData();
    }
  }, [currentPage, keyword, sumMsgs, unreadMsgs]);

  // 메세지 데이터 로딩 중
  if (!messages) {
    return <Spinner width="20%" />;
  }

  // 메세지들 불러오기
  function renderMessages() {
    if (!messages) {
      return null;
    }
    const messageList = [];
    for (let i = 0; i < messages.length; i++) {
      const message = messages[i];
      const isChecked = checkedMessages.some((m) => m.id === message.id);
      if (message.from === 'hospital') {
        messageList.push(
          <Fragment key={message.id}>
            <FlexRowContainer
              $position="relative"
              $margin="10px 0 0 0"
              $justifyContent="stretch"
            >
              <MessageReport
                openModal={openModal}
                message={message}
                $isChecked={isChecked}
                handleCheckboxChange={handleCheckboxChange}
              />
            </FlexRowContainer>

            <hr />
          </Fragment>,
        );
        continue;
      }
      messageList.push(
        <Fragment key={message.id}>
          <FlexRowContainer
            $position="relative"
            $margin="10px 0 0 0"
            $justifyContent="stretch"
          >
            <MessageSimple
              openModal={openModal}
              message={message}
              $isChecked={isChecked}
              handleCheckboxChange={handleCheckboxChange}
            />
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
      setMessages((prev) => {
        if (!prev) return undefined;
        // 새로운 메시지 목록(updatedMessages)생성
        const updatedMessages = [];
        for (let i = 0; i < prev.length; i++) {
          const message = prev[i];
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
        return updatedMessages;
      });
      setUnreadMsgs((unreadMsgs ?? 0) - checkedMessages.length);
    } catch (error) {
      console.error('오류:', error);
    }
    // 체크함 초기화
    setCheckedMessages([]);
  }

  // 모달 클릭시 읽음확인 처리 + 단일상세조회
  async function openModal(message: Message) {
    const updatedMessage = await getReadMessage(message.id);
    if (updatedMessage) {
      let temp: Message = updatedMessage;
      if (updatedMessage.from === 'hospital') {
        const parsedContent =
          typeof updatedMessage.content === 'string'
            ? parseContent(updatedMessage.content)
            : message.content;
        if (isReportMessage(parsedContent)) {
          temp = { ...updatedMessage, content: parsedContent };
        } else {
          temp = updatedMessage;
        }
      }
      setSelectedMessage(temp);
      setMessages((prev) => {
        if (!prev) return undefined;
        // 새로운 메시지 목록(updatedMessages)생성
        const updatedMessages = [];
        for (let i = 0; i < prev.length; i++) {
          const eachMessage = prev[i];
          if (message.id === eachMessage.id) {
            // 현재 가리키고 있는 메세지가 선택된 메세지인 경우 읽음 처리
            updatedMessages.push({ ...eachMessage, isRead: true });
          } else {
            // 그대로 유지
            updatedMessages.push(eachMessage);
          }
        }
        // 이전 메세지 목록 기존 정보 유지하고, 메세지 목록 새롭게 업데이트해서 반환
        return updatedMessages;
      });
      if (!message.isRead) {
        setUnreadMsgs((unreadMsgs ?? 0) - 1);
      }
    }
  }
  // 상세보기 할 메세지 선택(더미테스트코드)
  // function openModal(message: Message) {
  //   setSelectedMessage(message);
  // }

  // 모달 닫히면 메세지 선택 해제
  function closeModal() {
    setSelectedMessage(null);
  }

  return (
    <div>
      <Title icon="message">메세지</Title>

      <FlexColContainer
        $position="absolute"
        $top="8vh"
        $alignItems="end"
        $justifyContent="stretch"
        $margin="2vh 0 0 0"
      >
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
        <ReadText>
          <UnReadMsgs>{unreadMsgs}</UnReadMsgs>
          <span>/{sumMsgs} 안 읽음</span>
        </ReadText>
        <Button
          $bgColor={success}
          $width="98px"
          $padding="9px"
          $color={white}
          onClick={handleReadClick}
        >
          선택 읽기
        </Button>
      </SubContainer>
      <Line $width="auto" $borderColor={black} />

      {renderMessages()}

      {selectedMessage && (
        <MessageDetailModal
          message={selectedMessage}
          onClose={closeModal}
          userId={user ? user.id : null}
          time={selectedMessage.time}
        />
      )}

      <Pagination
        pageGroupSize={pageGroupSize}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPage={pageNum}
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
  display: flex;
  justify-content: space-between;
`;

const SearchForm = styled.form`
  position: relative;
  width: 210px;
`;

const ReadText = styled.div`
  margin-top: 15px;
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
