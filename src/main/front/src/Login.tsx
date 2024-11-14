import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./css/Login.css";
import { useAuth } from "./context/AuthContext"; // useAuth import
import googleLogo from './images/google.png';
import naverLogo from './images/naver.png';

const Login = () => {
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string>("");

  const navigate = useNavigate();
  const { login } = useAuth(); // login 함수 가져오기

  const handleLogin = async () => {
    try {
      const response = await fetch("/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({
          email,
          password,
        }),
      });

      const accessToken = response.headers.get('Authorization');
      const refreshToken = response.headers.get('Authorization-Refresh');
      if (accessToken) {
        localStorage.setItem('accessToken', accessToken);
        console.log('Access Token:', accessToken);
      } else {
        console.error('Access token not found in the response');
      }
      if (refreshToken) {
        localStorage.setItem('refreshToken', refreshToken);
        console.log('Refresh Token:', refreshToken);
      } else {
        console.error('Refresh token not found in the response');
      }
      if (!response.ok) {
        throw new Error("로그인 실패");
      }

      // 로그인 성공 시 login 함수 호출 및 홈 화면으로 이동
      login();
      navigate("/");
    } catch (error) {
      console.error("로그인 실패:", error);
      setError("로그인 실패. 이메일 또는 비밀번호를 확인하세요.");
    }
  };

  // 소셜 로그인 팝업 창 열기
  const handleSocialLogin = (url: string) => {
    window.open(`http://localhost:8081${url}`, '_blank', 'width=500,height=600');
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h2>로그인</h2>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="이메일"
          className="login-input"
        />
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="비밀번호"
          className="login-input"
        />
        <button onClick={handleLogin} className="login-button">
          로그인
        </button>
        <p className="signup-link">
          아직 계정이 없으신가요? <a href="/sign-up">회원가입</a>
        </p>

        {/* Google 소셜 로그인 버튼 */}
        <img
          src={googleLogo}
          alt="Google Login"
          className="social-login-button"
          onClick={() => handleSocialLogin('/oauth2/authorization/google')}
          style={{ cursor: 'pointer' }}
        />

        {/* Naver 소셜 로그인 버튼 */}
        <img
          src={naverLogo}
          alt="Naver Login"
          className="social-login-button"
          onClick={() => handleSocialLogin('/oauth2/authorization/naver')}
          style={{ cursor: 'pointer' }}
        />

        {error && <p className="login-error">{error}</p>}
      </div>
    </div>
  );
};

export default Login;
