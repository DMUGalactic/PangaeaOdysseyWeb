import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import '../css/Community.css';

interface BoardDTO {
  id: number;
  title: string;
  content: string;
  authorNickname: string;
  views: number;
  createdAt: string;
}

const Community: React.FC = () => {
  const [boards, setBoards] = useState<BoardDTO[]>([]);

  useEffect(() => {
    // API 요청을 통해 게시글 목록 가져오기
    const fetchBoards = async () => {
      try {
        const response = await fetch('/api/boards/');
        if (response.ok) {
          const data = await response.json();
          setBoards(data);
        } else {
          console.error('게시글 목록을 불러오지 못했습니다.');
        }
      } catch (error) {
        console.error('게시글 목록 요청 중 오류가 발생했습니다:', error);
      }
    };

    fetchBoards();
  }, []);

  return (
    <>
      <div className="community-container">
        <h1>커뮤니티 게시글 목록</h1>
        <div className="board-list">
          <table className="board-table">
            <thead>
              <tr>
                <th>Id</th>
                <th>제목</th>
                <th>글쓴이</th>
                <th>조회수</th>
                <th>작성일</th>
              </tr>
            </thead>
            <tbody>
              {boards.map((board) => (
                <tr key={board.id} className="board-item">
                  <td>{board.id}</td>
                  <td><Link to={`/community/${board.id}`}>{board.title}</Link></td>
                  <td>{board.authorNickname}</td>
                  <td>{board.views}</td>
                  <td>{new Date(board.createdAt).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <div className="create-board-button-container">
        <Link to="/create-post" className="create-board-button">
          게시글 작성하기
        </Link>
      </div>
    </>
  );
};

export default Community;
