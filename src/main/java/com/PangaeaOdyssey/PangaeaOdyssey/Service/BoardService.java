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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public BoardDTO updateBoard(Long id, BoardDTO updatedBoardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        // 비밀번호 검증
        if (!board.getPassword().equals(board.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않음");
        }
        board.patch(updatedBoardDTO);
        Board updated = boardRepository.save(board);

        BoardDTO boardDTO = BoardDTO.createBoardDTO(updated);
        return boardDTO;
    }

    @Transactional
    public BoardDTO deleteBoard(Long id, String password, String token) {
        String email = jwtService.extractEmail(token.replace("Bearer ", ""))
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));

        Board target = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패!"));

        // 현재 사용자의 정보를 조회하여 관리자 여부 확인
        boolean isAdmin = memberRepository.findByEmail(email)
                .map(Member::getRole)
                .map(role -> role == Role.ADMIN) // Role은 실제로 사용하고 있는 Role enum 클래스의 ADMIN 값
                .orElse(false);

        // 관리자 권한이 없는 경우에만 비밀번호 검증
        if (!isAdmin && !target.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 맞지 않음");
        }
        boardRepository.deleteById(id);
        BoardDTO dto = BoardDTO.createBoardDTO(target);
        return dto;
    }
    public List<BoardDTO> searchByKeyword(String keyword) {
        List<Board> boards = boardRepository.searchByKeyword(keyword);
        return boards.stream().map(BoardDTO::createBoardDTO).collect(Collectors.toList());
    }
    public List<BoardDTO> searchByTitle(String keyword) {
        List<Board> boards = boardRepository.searchByTitle(keyword);
        return boards.stream().map(BoardDTO::createBoardDTO).collect(Collectors.toList());
    }

    public List<BoardDTO> searchByContent(String keyword) {
        List<Board> boards = boardRepository.searchByContent(keyword);
        return boards.stream().map(BoardDTO::createBoardDTO).collect(Collectors.toList());
    }
}