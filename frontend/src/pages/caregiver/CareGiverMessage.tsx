import React, { useEffect, useState, Fragment } from 'react';
import getMessage, {
  Message,
  MessagesResponse,
} from '../../services/message/getMessage';
import { useNavigate } from 'react-router-dom';
import MessageDetailModal from '../../components/message/MessageDetailModal';
import Pagination from '../../components/common/Pagination';
import MessageSimple from '../../components/message/MessageSimple';
import useUserStore from '../../stores/UserStore';
import Title from '../../components/common/Title';
import { black, failed, success, white } from '../../assets/styles/palettes';
import styled from 'styled-components';
import Line from '../../components/common/Line';
import FlexRowContainer from '../../components/common/FlexRowContainer';
import { Button } from '../../components/common/Buttons';

// 간병인 - 보낸 메세지 페이지별 조회

function CareGiverMessage() {
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [messagesData, setMessagesData] = useState<MessagesResponse | null>(
    null,
  );
  const [selectedMessage, setSelectedMessage] = useState<Message | null>(null);
  const { user } = useUserStore();
  const navigate = useNavigate();
  const pageGroupSize = 5;

  useEffect(() => {
    async function fetchData() {
      const res = await getMessage(currentPage);
      setMessagesData(res);
    };
    fetchData();
  }, [currentPage, messagesData]);

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
      messageList.push(
        <Fragment key={message.id}>
          <FlexRowContainer
            $margin="10px 0 0 0"
            $padding="0 0 7px 0"
            $position="relative"
            $justifyContent="stretch"
            $alignItems="stretch"
          >
            <MessageSimple openModal={openModal} message={message} />
          </FlexRowContainer>

          <hr />
        </Fragment>,
      );
    }
    return messageList;
  }

  // 상세보기 할 메세지 선택(더미테스트코드)
  function openModal(message: Message) {
    setSelectedMessage(message);
  }

  // 모달 닫히면 메세지 선택 해제
  function closeModal() {
    setSelectedMessage(null);
  }

  return (
    <div>
      <Title icon="message">메세지</Title>
      <SubContainer>
        <ReadText>
          <UnReadMsgs>{messagesData.unreadMsgs}</UnReadMsgs>
          <span>/{messagesData.sum} 안 읽음</span>
        </ReadText>
        <Button
          $bgColor={success}
          $color={white}
          $width="100px"
          $padding="10px"
          onClick={() => navigate('/caregiver/message/send')}
        >
          메세지 작성
        </Button>
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

export default CareGiverMessage;

const UnReadMsgs = styled.span`
  color: ${failed};
`;

const ReadText = styled.div`
  margin-top: 15px;
`;

const SubContainer = styled.div`
  margin: 0.75rem;
  display: flex;
  justify-content: space-between;
`;
