import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./context/AuthContext";

const AuthRedirect = () => {
  const navigate = useNavigate();
  const { login } = useAuth();

  useEffect(() => {
    // URL에서 토큰을 가져오기
    const params = new URLSearchParams(window.location.search);
    const accessToken = params.get('accessToken');
    const refreshToken = params.get('refreshToken');

    if (accessToken && refreshToken) {
      // 로컬스토리지에 토큰 저장
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);

      // 로그인 상태 업데이트
      login();  // 여기서 로그인 상태를 업데이트

      // 홈 화면으로 이동
      navigate('/');
    }
  }, [login, navigate]);

  return null;
};

export default AuthRedirect;
