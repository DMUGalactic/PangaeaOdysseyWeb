import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../css/PostDetail.css';

interface PostDetailDTO {
  id: number;
  title: string;
  content: string;
  authorNickname: string;
  views: number;
  createdAt: string;
}

const PostDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // URL에서 게시글 ID를 가져옴
  const [post, setPost] = useState<PostDetailDTO | null>(null);
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
          credentials: 'include'
        });

        if (response.status === 401) {
          // 인증 실패 시 로그인 페이지로 이동
          navigate('/login');
        } else if (response.ok) {
          const data = await response.json();
          setPost(data);
        } else {
          console.error('게시글 정보를 불러오지 못했습니다.');
        }
      } catch (error) {
        console.error('게시글 요청 중 오류가 발생했습니다:', error);
      }
    };

    fetchPost();
  }, [id, navigate]);

  const handleDelete = async () => {
    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');

    if (!accessToken || !refreshToken) {
      console.error('토큰이 없습니다. 로그인 후 다시 시도하세요.');
      return;
    }

    const userPassword = prompt('게시글을 삭제하려면 비밀번호를 입력하세요.');
    if (!userPassword) {
      alert('비밀번호를 입력하세요.');
      return;
    }

    try {
      const response = await fetch(`/api/boards/delete/${id}/${userPassword}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`,
        },
        credentials: 'include'
      });

      if (response.ok) {
        console.log('게시글이 성공적으로 삭제되었습니다.');
        navigate('/community'); // 게시글 삭제 후 커뮤니티 목록으로 이동
      } else {
        console.error('게시글 삭제에 실패했습니다.');
      }
    } catch (error) {
      alert('비밀번호가 일치하지 않습니다. 다시 시도해주세요.');
      console.error('게시글 삭제 중 오류가 발생했습니다:', error);
    }
  };

  return (
    <div className="post-detail-container">
      {post ? (
        <>
          <h1>{post.title}</h1>
          <p><strong>작성자:</strong> {post.authorNickname}</p>
          <p><strong>조회수:</strong> {post.views}</p>
          <p><strong>작성일:</strong> {new Date(post.createdAt).toLocaleDateString()}</p>
          <div className="post-content">
            <p>{post.content}</p>
          </div>
          <div className="button-wrapper">
            <button className="back-to-community-button" onClick={() => navigate('/community')}>
              돌아가기
            </button>
            <button className="edit-post-button" onClick={() => navigate(`/edit-post/${post.id}`)}>
              수정하기
            </button>
            <button className="delete-post-button" onClick={handleDelete}>
              삭제하기
            </button>
          </div>
        </>
      ) : (
        <p>게시글을 불러오는 중...</p>
      )}
    </div>
  );
};

export default PostDetail;
