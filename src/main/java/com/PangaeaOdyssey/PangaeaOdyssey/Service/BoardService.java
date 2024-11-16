package com.PangaeaOdyssey.PangaeaOdyssey.Service;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.BoardDTO;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Board;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Member;
import com.PangaeaOdyssey.PangaeaOdyssey.Enum.Role;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.BoardRepository;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final JwtService jwtService;
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
    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        Board savedBoard = boardRepository.save(board);

        return BoardDTO.createBoardDTO(savedBoard);
    }

    @Transactional
    public BoardDTO updateBoard(Long id, String password, BoardDTO updatedBoardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        // 비밀번호 검증
        if (!board.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 맞지 않음");
        }
        board.patch(updatedBoardDTO);
        Board updated = boardRepository.save(board);

        BoardDTO boardDTO = BoardDTO.createBoardDTO(updated);
        return boardDTO;
    }
    /*
        public void deleteBoard(Long id) {
            boardRepository.deleteById(id);
        }
         */
    private boolean isAdmin(String nickname) {
        return memberRepository.findByNickname(nickname)
                .map(Member::getRole)
                .map(role -> role == Role.ADMIN)
                .orElse(false);
    }
}