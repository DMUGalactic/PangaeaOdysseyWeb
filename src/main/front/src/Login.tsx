import { useState } from "react";
import "./css/Login.css";
const Login = () => {
  const [email, setEmail] = useState("");       // email 상태값
  const [password, setPassword] = useState(""); // password 상태값
  const [error, setError] = useState("");       // 오류 메시지 상태값

  const handleLogin = async () => {
    try {
      const response = await fetch("/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include", // 쿠키 등 인증 정보 포함
        body: JSON.stringify({
          email,        // email 상태값을 JSON으로 변환
          password,     // password 상태값을 JSON으로 변환
        }),
      });
  // 여기서 응답 상태 코드 확인
  console.log("응답 상태 코드:", response.status);
  const data = await response.json(); // 응답 데이터 처리
  console.log("응답 데이터:", data);
  if (!response.ok || data.success === false) {  // 응답 데이터 형식에 따른 검증
    throw new Error(data.message || "로그인 실패.");
    }
    console.log("로그인 성공:", data);
      // 로그인 성공 후 처리
     } catch (error) {
        console.error("로그인 실패:", error);
        setError("로그인 실패. 이메일 또는 비밀번호를 확인하세요.");
      }
  };

  return (
    <div>
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}  // 이메일 입력값 상태로 저장
        placeholder="이메일"
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}  // 비밀번호 입력값 상태로 저장
        placeholder="비밀번호"
      />
      <button onClick={handleLogin}>로그인</button>  {/* 폼 태그 대신 버튼 클릭으로 로그인 처리 */}
      {error && <p style={{ color: "red" }}>{error}</p>}  {/* 오류 메시지 출력 */}
    </div>
  );
};

export default Login;