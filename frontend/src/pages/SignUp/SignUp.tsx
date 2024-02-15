import React, { useState, useEffect, ChangeEvent } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import useUserStore from '../../stores/UserStore';
import api from '../../services/api';
import { Button, DisabledButton } from '../../components/common/Buttons';
import Input from '../../components/common/Input';
import * as S from './SignUp.styles';
import UserContainer from '../../components/common/UserContainer';
import getCheckId from '../../services/user/getCheckId';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/locale';
import { range } from 'lodash';
import { getYear, getMonth } from 'date-fns';

interface Form {
  name: string;
  phone1: string;
  phone2: string;
  phone3: string;
  birth: string;
}

function SignUp() {
  const navigate = useNavigate();
  const { isLogin, role, logout, setUser } = useUserStore();
  const [form, setForm] = useState<Form>({
    name: '',
    phone1: '',
    phone2: '',
    phone3: '',
    birth: '',
  });

  if (isLogin) {
    switch (role) {
      case 'Caregiver':
        navigate('/caregiver', { replace: true})
        return;
      case 'Guardian':
        navigate('/family', { replace: true})
        return;
      default:
        alert('접근 권한이 없습니다.');
        logout();
        navigate('', {replace: true});
        return;
    }
  }

  const [id, setId] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [passwordConfirm, setPasswordConfirm] = useState<string>('');
  const [vaildId, setValidId] = useState<boolean>(false);
  const [checkId, setCheckId] = useState<boolean>(false); // 아이디 중복 유무
  const [IdMsg, setIdMsg] = useState<string>('');
  const [validPw, setValidPw] = useState<boolean>(false);
  const [PwMsg, setPwMsg] = useState<string>('');
  const [PwConfirmMsg, setPwConfirmMsg] = useState<string>('');
  const location = useLocation();
  const kakaoEmail = location.state?.kakaoEmail;
  const [email, setEmail] = useState(kakaoEmail || '');

  const years = range(1990, getYear(new Date()) + 1, 1);
  const months = [
    '1월',
    '2월',
    '3월',
    '4월',
    '5월',
    '6월',
    '7월',
    '8월',
    '9월',
    '10월',
    '11월',
    '12월',
  ];
  useEffect(() => {
    setEmail(kakaoEmail || '');
  }, [kakaoEmail]);

  // id 유효성 검사
  function onChangeId(e: ChangeEvent<HTMLInputElement>) {
    const inputId = e.target.value;
    setId(inputId);
    const idRegex = /^[a-zA-Z][a-zA-Z0-9_-]{2,19}$/;

    if (id.length === 0) {
      setIdMsg('');
    } else if (!idRegex.test(id)) {
      setIdMsg('3~20사이 대소문자 또는 숫자만 입력해 주세요!');
      setValidId(false);
    } else {
      setIdMsg('사용가능한 아이디 입니다.');
      setIdMsg('');
      setValidId(true);
    }
  }

  // 두 password 일치 확인
  function onChangePasswordConfirm(e: ChangeEvent<HTMLInputElement>) {
    const inputPasswordConfirm = e.target.value;
    setPasswordConfirm(inputPasswordConfirm);
    if (inputPasswordConfirm.length === 0) {
      setPwConfirmMsg('');
    } else if (password !== inputPasswordConfirm) {
      setPwConfirmMsg('두 비밀번호가 일치하지 않습니다.');
      setValidPw(false);
    } else {
      setPwConfirmMsg('두 비밀번호가 일치합니다.');
      setValidPw(true);
    }
  }

  // password 유효성 검사
  function onChangePassword(e: ChangeEvent<HTMLInputElement>) {
    const inputPassword = e.target.value;
    setPassword(inputPassword);
    const pwRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[@#$%^&+=!])(?!.*\s).{8,20}$/;

    if (inputPassword.length === 0) {
      setPwMsg('');
      setValidPw(false);
    } else if (!pwRegex.test(inputPassword)) {
      setPwMsg('8~20사이로 숫자, 소문자, 특수문자를 포함해주세요!');
      setValidPw(false);
    } else {
      setPwMsg('사용가능한 비밀번호 입니다.');
      setValidPw(true);
    }

    if (password.length !== 0 && password !== passwordConfirm) {
      setPwConfirmMsg('두 비밀번호가 일치하지 않습니다.');
      setValidPw(false);
    } else if (password.length !== 0 && password === passwordConfirm) {
      setPwConfirmMsg('두 비밀번호가 일치합니다.');
      setValidPw(true);
    } else {
      setPwConfirmMsg('');
      setValidPw(false);
    }
  }

  // 회원가입 버튼 활성화 조건
  const submitRequirements =
    id &&
    password &&
    passwordConfirm &&
    vaildId &&
    validPw &&
    checkId &&
    form.name &&
    form.phone1 &&
    form.phone2 &&
    form.phone3 &&
    form.birth;

  // input태그의 이름, 사용자가 입력한 값을 실시간으로 각각 동적 할당
  function handleChange(e: ChangeEvent<HTMLInputElement>) {
    const { name, value } = e.target;
    let formattedValue = value;

    if (['phone1', 'phone2', 'phone3'].includes(name)) {
      formattedValue = value.replace(/\D/g, '');
    }

    setForm((prevForm) => ({
      ...prevForm,
      [name]: formattedValue,
    }));
  }

  // 아이디 중복 확인
  async function handleCheckId() {
    try {
      await getCheckId(id);
      alert('사용 가능한 아이디입니다.');
      setCheckId(true);
    } catch (error) {
      console.error(error);
      alert('이미 사용 중인 아이디입니다.');
      setCheckId(false);
    }
  }

  useEffect(() => {
    if (vaildId && checkId) {
      setIdMsg('사용 가능한 아이디입니다.');
    }
  }, [vaildId, checkId]);

  // 회원가입
  async function handleSignUp() {
    const phoneNumber = `${form.phone1}-${form.phone2}-${form.phone3}`;
    try {
      const response = await api.post('/users', {
        ...form,
        phoneNumber,
        id,
        password,
        email,
      });
      if (response.status === 201) {
        setUser(response.data);
        navigate('/login', { state: { pathType: 'app' } });
      } else {
        console.error(`오류: ${response.status}`);
      }
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <div>
      <S.GlobalStyle />
      <UserContainer
        $height="620px"
        $width="300px"
        $padding="12px"
        $alignItems="left"
      >
        <S.SignUpText>회원가입</S.SignUpText>
        <div>
          <S.Label>
            <div>아이디</div>
            <S.IDContainer>
              <Input
                $width="75%"
                type="text"
                name="id"
                placeholder="아이디를 입력해주세요."
                value={id}
                onChange={onChangeId}
                required
              />
              {vaildId && checkId ? <S.IdCheckIcon></S.IdCheckIcon> : null}
              <Button $width="30%" $padding="8px" onClick={handleCheckId}>
                중복확인
              </Button>
            </S.IDContainer>
          </S.Label>
          <div
            style={{ marginTop: '-7px' }}
            className={`message ${IdMsg && 'active'}`}
          >
            {IdMsg}
          </div>
          <S.Label>
            <div>비밀번호</div>
            <Input
              $width="98%"
              type="password"
              name="password"
              placeholder="비밀번호를 입력해주세요."
              value={password}
              onChange={onChangePassword}
              required
            />
            {validPw ? <S.PwCheckIcon></S.PwCheckIcon> : null}
          </S.Label>
          <div className={`message ${PwMsg && 'active'}`}>{PwMsg}</div>
          <S.Label>
            <div>비밀번호 확인</div>
            <Input
              $width="98%"
              type="password"
              name="passwordConfirm"
              placeholder="비밀번호를 한번 더 입력해주세요."
              value={passwordConfirm}
              onChange={onChangePasswordConfirm}
              required
            />
            {validPw ? <S.PwConfirmCheckIcon></S.PwConfirmCheckIcon> : null}
          </S.Label>
          <div className={`message ${PwConfirmMsg && 'active'}`}>
            {PwConfirmMsg}
          </div>
          <S.Label>
            <div>이름</div>
            <Input
              $width="98%"
              type="text"
              name="name"
              placeholder="이름을 입력해주세요."
              value={form.name}
              onChange={handleChange}
              required
            />
          </S.Label>
          <S.Label>
            <div>생년월일</div>
            <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                width: '100%',
              }}
            >
              <DatePicker
                customInput={<Input $width="98%" />}
                dateFormat="yyyy.MM.dd"
                shouldCloseOnSelect
                selected={form.birth ? new Date(form.birth) : null}
                onChange={(date: Date) =>
                  setForm({ ...form, birth: date.toISOString().split('T')[0] })
                }
                placeholderText="생년월일을 입력해주세요."
                locale={ko}
                renderCustomHeader={({
                  date,
                  changeYear,
                  changeMonth,
                  decreaseMonth,
                  increaseMonth,
                  prevMonthButtonDisabled,
                  nextMonthButtonDisabled,
                }) => (
                  <div
                    style={{
                      margin: 10,
                      display: 'flex',
                      justifyContent: 'center',
                    }}
                  >
                    <button
                      onClick={decreaseMonth}
                      disabled={prevMonthButtonDisabled}
                    >
                      {'<'}
                    </button>
                    <select
                      value={getYear(date)}
                      onChange={({ target: { value } }) =>
                        changeYear(parseInt(value))
                      }
                    >
                      {years.map((option) => (
                        <option key={option} value={option}>
                          {option}
                        </option>
                      ))}
                    </select>

                    <select
                      value={months[getMonth(date)]}
                      onChange={({ target: { value } }) =>
                        changeMonth(months.indexOf(value))
                      }
                    >
                      {months.map((option) => (
                        <option key={option} value={option}>
                          {option}
                        </option>
                      ))}
                    </select>

                    <button
                      onClick={increaseMonth}
                      disabled={nextMonthButtonDisabled}
                    >
                      {'>'}
                    </button>
                  </div>
                )}
              />
            </div>
          </S.Label>
          <S.Label>
            <div>전화번호</div>
            <S.PhoneContainer>
              <Input
                $width="60%"
                $textAlign="center"
                type="tel"
                name="phone1"
                value={form.phone1}
                onChange={handleChange}
                required
                maxLength={3}
              />
              -
              <Input
                $width="80%"
                $textAlign="center"
                type="tel"
                name="phone2"
                value={form.phone2}
                onChange={handleChange}
                required
                maxLength={4}
              />
              -
              <Input
                $width="80%"
                $textAlign="center"
                type="tel"
                name="phone3"
                value={form.phone3}
                onChange={handleChange}
                required
                maxLength={4}
              />
            </S.PhoneContainer>
          </S.Label>
          <br />
          {submitRequirements ? (
            <Button $width="100%" type="button" onClick={handleSignUp}>
              회원가입
            </Button>
          ) : (
            <DisabledButton $width="100%">회원가입</DisabledButton>
          )}
        </div>
      </UserContainer>
    </div>
  );
}

export default SignUp;
