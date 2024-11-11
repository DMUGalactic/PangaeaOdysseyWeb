import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './css/SignUp.css';

const SignUp: React.FC = () => {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [confirmPassword, setConfirmPassword] = useState<string>('');
  const [nickname, setNickname] = useState<string>('');
  const [error, setError] = useState<string>('');
  const navigate = useNavigate();

  const handleSignUp = async () => {
    if (password !== confirmPassword) {
      setError('비밀번호가 일치하지 않습니다.');
      return;
    }

    try {
      const response = await fetch('http://localhost:8081/sign-up', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email,
          password,
          nickname,
        }),
      });

      if (response.ok) {
        console.log('회원가입 성공');
        navigate('/login'); // 회원가입 성공 시 로그인 페이지로 이동
      } else {
        const errorMessage = await response.text();
        setError(`회원가입 실패: ${errorMessage}`);
      }
    } catch (error) {
      console.error('회원가입 요청 중 오류 발생:', error);
      setError('회원가입 요청 중 오류가 발생했습니다. 다시 시도해 주세요.');
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-form">
        <h2>회원가입</h2>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="이메일"
          className="signup-input"
        />
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="비밀번호"
          className="signup-input"
        />
        <input
          type="password"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          placeholder="비밀번호 확인"
          className="signup-input"
        />
        <input
          type="text"
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
          placeholder="닉네임"
          className="signup-input"
        />
        <button onClick={handleSignUp} className="signup-button">
          회원가입
        </button>
        <p className="signup-login-link">
          이미 계정이 있으신가요? <a href="/login">로그인</a>
        </p>
        {error && <p className="signup-error">{error}</p>}
      </div>
    </div>
  );
};

export default SignUp;
