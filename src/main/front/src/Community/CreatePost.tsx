import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/CreatePost.css';

const CreatePost: React.FC = () => {
  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');

    if (!accessToken || !refreshToken) {
      console.error('토큰이 없습니다. 로그인 후 다시 시도하세요.');
      return;
    }

    try {
      const response = await fetch('/api/boards/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`
        },
        credentials: 'include',
         // 사용자 인증 정보를 포함하여 요청 보내기
        body: JSON.stringify({
          title,
          content,
          password, // 비밀번호 추가
        }),
      });

      if (response.ok) {
        navigate('/community'); // 게시글 작성 후 커뮤니티 목록으로 이동
      } else {
        console.error('게시글 작성에 실패했습니다.');
      }
    } catch (error) {
      console.error('게시글 작성 중 오류가 발생했습니다:', error);
    }
  };

  return (
    <div className="create-post-container">
      <h1>게시글 작성하기</h1>
      <form onSubmit={handleSubmit} className="create-post-form">
        <div className="form-group">
          <label htmlFor="title">제목</label>
          <input
            type="text"
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="content">내용</label>
          <textarea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          ></textarea>
        </div>
        <div className="form-group">
          <label htmlFor="password">비밀번호</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="create-post-button">
          작성 완료
        </button>
      </form>
      <button className="back-to-community-button" onClick={() => navigate('/community')}>
        목록으로 돌아가기
      </button>
    </div>
  );
};

export default CreatePost;
