import React, { ChangeEvent, useState } from 'react';
import postConnection from '../../services/connect/postConnection';
import useUserStore from '../../stores/UserStore';
import { useNavigate } from 'react-router-dom';

function ConnectRegister() {
  const navigate = useNavigate();
  const { user } = useUserStore();
  const [form, setForm] = useState({
    nursingHome: '',
    targetCode: '',
    targetName: '',
    relationship: '',
  });
  const [otherRelationship, setOtherRelationship] = useState<string>('');

  function handleChange(e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) {
    const { name, value } = e.target;
    setForm((prevForm) => ({
      ...prevForm,
      [name]: value,
    }));
  }

  function handleSubmit() {
    // 로그인이 되어있지 않은 상태
    if (user === null) {
      console.log('로그인이 필요합니다.', form, otherRelationship);
      navigate('/family/');
      return;
    }

    const body = {
      domainId: user.id,
      nursingHome: form.nursingHome,
      targetCode: form.targetCode,
      targetName: form.targetName,
      relationship: form.relationship,
    };
    // otherRelationship의 값이 존재하면 relationship에 할당
    if (body.relationship === 'etc' && otherRelationship) {
      body.relationship = otherRelationship;
    }

    postConnection(body);
    console.log(body);
    navigate('/family/');
  }

  return (
    <div>
      <h1>연결 신청</h1>
      <form>
        <button type="button" onClick={handleSubmit}>
          신청
        </button>
        <label>
          요양원명
          <input
            type="text"
            name="nursingHome"
            placeholder="요양원명"
            value={form.nursingHome}
            onChange={handleChange}
          />
        </label>
        <label>
          입소자코드
          <input
            type="text"
            name="targetCode"
            placeholder="입소자코드"
            value={form.targetCode}
            onChange={handleChange}
          />
        </label>
        <label>
          입소자명
          <input
            type="text"
            name="targetName"
            placeholder="입소자명"
            value={form.targetName}
            onChange={handleChange}
          />
        </label>
        <label>
          입소자와의 관계
          <select
            name="relationship"
            value={form.relationship}
            onChange={handleChange}
          >
            <option value="son">아들</option>
            <option value="daughter">딸</option>
            <option value="grandson">손자</option>
            <option value="granddaughter">손녀</option>
            <option value="etc">기타</option>
          </select>
        </label>
        {form.relationship === 'etc' && (
          <label>
            기타 관계
            <input
              type="text"
              name="otherRelationship"
              value={otherRelationship}
              onChange={(e) => setOtherRelationship(e.target.value)}
            />
          </label>
        )}
      </form>
    </div>
  );
}

export default ConnectRegister;
