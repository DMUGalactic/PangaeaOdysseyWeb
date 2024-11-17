import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../css/CreatePost.css';

const EditPost: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const navigate = useNavigate();

  useEffect(() => {
    // 수정할 게시글 정보 불러오기
    const fetchPost = async () => {
      try {
        const response = await fetch(`http://localhost:8081/api/boards/${id}`);
        if (response.ok) {
          const data = await response.json();
          setTitle(data.title);
          setContent(data.content);
        } else {
          console.error('게시글을 불러오지 못했습니다.');
        }
      } catch (error) {
        console.error('게시글 불러오는 중 오류가 발생했습니다:', error);
      }
    };

    fetchPost();
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem('accessToken');
      const response = await fetch(`http://localhost:8081/api/boards/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({
          title,
          content,
          password, // 본인 확인을 위해 비밀번호도 함께 전송
        }),
      });

      if (response.ok) {
        navigate('/community'); // 게시글 수정 후 커뮤니티 목록으로 이동
      } else {
        console.error('게시글 수정에 실패했습니다.');
      }
    } catch (error) {
      console.error('게시글 수정 중 오류가 발생했습니다:', error);
    }
  };

  return (
    <div className="create-post-container">
      <h1>게시글 수정하기</h1>
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
          수정 완료
        </button>
      </form>
      <button className="back-to-community-button" onClick={() => navigate('/community')}>
        목록으로 돌아가기
      </button>
    </div>
  );
};

export default EditPost;
