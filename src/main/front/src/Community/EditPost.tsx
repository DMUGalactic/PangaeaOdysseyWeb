import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../css/EditPost.css';

const EditPost: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const navigate = useNavigate();

  useEffect(() => {
    // 게시글 상세 정보 가져오기
    const fetchPost = async () => {
      const accessToken = localStorage.getItem('accessToken');
      const refreshToken = localStorage.getItem('refreshToken');

      if (!accessToken || !refreshToken) {
        console.error('토큰이 없습니다. 로그인 후 다시 시도하세요.');
        return;
      }

      try {
        const response = await fetch(`/api/boards/${id}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${accessToken}`,
          },
          credentials: 'include',
        });

        if (response.status === 401) {
          // 인증 실패 시 로그인 페이지로 이동
          navigate('/login');
        } else if (response.ok) {
          const data = await response.json();
          setTitle(data.title);
          setContent(data.content);
        } else {
          console.error('게시글 정보를 불러오지 못했습니다.');
        }
      } catch (error) {
        console.error('게시글 요청 중 오류가 발생했습니다:', error);
      }
    };

    fetchPost();
  }, [id, navigate]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');

    if (!accessToken || !refreshToken) {
      console.error('토큰이 없습니다. 로그인 후 다시 시도하세요.');
      return;
    }

    try {
      const response = await fetch(`/api/boards/update/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`,
        },
        credentials: 'include',
        body: JSON.stringify({
          title,
          content,
          password
        }),
      });

      if (response.ok) {
        console.log('게시글이 성공적으로 수정되었습니다.');
        navigate(`/community/${id}`);
      } else {
        const errorData = await response.json(); // 오류 응답을 JSON으로 파싱합니다.
        console.error('게시글 수정에 실패했습니다.', errorData);
      }
    } catch (error) {
      console.error('게시글 수정 중 오류가 발생했습니다:', error);
    }
  };

  return (
    <div className="edit-post-container">
      <h1>게시글 수정하기</h1>
      <form onSubmit={handleSubmit} className="edit-post-form">
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
        <button type="submit" className="edit-post-button">
          수정 완료
        </button>
      </form>
      <button className="back-to-community-button" onClick={() => navigate(`/community/${id}`)}>
        돌아가기
      </button>
    </div>
  );
};

export default EditPost;
