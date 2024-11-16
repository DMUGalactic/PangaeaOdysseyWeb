package com.PangaeaOdyssey.PangaeaOdyssey.Service;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.BoardDTO;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Board;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;

    public List<BoardDTO> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDTO> dtos = new ArrayList<>();
        for(int i = 0; i<boards.size(); i++){
            Board b = boards.get(i);
            BoardDTO dto = BoardDTO.createBoardDTO(b);
            dtos.add(dto);
        }
        return dtos;
    }

    public BoardDTO getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + id));
        return BoardDTO.createBoardDTO(board); // DTO로 변환
    }
    @Transactional
    public static BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        boardRepository.save(board);
        return BoardDTO.createBoardDTO(board);
    }

    public Board updateBoard(Long id, Board updatedBoard) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(updatedBoard.getTitle());
                    board.setContent(updatedBoard.getContent());
                    board.setViews(updatedBoard.getViews()); // 필요 시 다른 필드도 추가
                    return boardRepository.save(board);
                }).orElseThrow(() -> new RuntimeException("Board not found"));
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
    private BoardDTO convertToDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getAuthor().getNickname(), // 작성자 닉네임 가져오기
                board.getViews(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }
}