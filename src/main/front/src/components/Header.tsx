import React, { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import "../css/Header.css";

const Header: React.FC = () => {
  const { isLoggedIn, logout } = useAuth();
  const navigate = useNavigate();
  const [scrolled, setScrolled] = useState<boolean>(false);

  const handleLogout = async (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault();
    const token = localStorage.getItem("accessToken");
    if (token) {
      try {
        const response = await fetch("http://localhost:8081/custom-logout", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          credentials: "include",
        });

        if (response.ok) {
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          logout();
          console.log("로그아웃 성공");
          navigate("/");
        } else {
          console.error("로그아웃 실패:", response.status);
        }
      } catch (error) {
        console.error("로그아웃 요청 중 오류 발생:", error);
      }
    } else {
      console.error("Access token not found.");
    }
  };

  const handleScroll = () => {
    if (window.scrollY > 50) {
      setScrolled(true);
    } else {
      setScrolled(false);
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <header className={`header ${scrolled ? "scrolled" : ""}`}>
      <nav>
        <Link to="/" className="logo">
          pangaeaOdyssey
        </Link>
        <ul className="nav-links">
          <li>
            <Link to="/company">기업정보</Link>
          </li>
          <li>
            <Link to="/info">게임정보</Link>
          </li>
          <li>
            <Link to="/download">다운로드</Link>
          </li>
          {!isLoggedIn ? (
            <li>
              <Link to="/login">로그인</Link>
            </li>
          ) : (
            <li>
              <a href="#" onClick={handleLogout} className="logout-button">
                로그아웃
              </a>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
