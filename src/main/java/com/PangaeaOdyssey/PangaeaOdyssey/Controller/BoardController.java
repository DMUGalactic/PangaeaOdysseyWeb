package com.PangaeaOdyssey.PangaeaOdyssey.Controller;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.BoardDTO;
import com.PangaeaOdyssey.PangaeaOdyssey.DTO.SearchDTO;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Board;
import com.PangaeaOdyssey.PangaeaOdyssey.Service.BoardService;
import com.PangaeaOdyssey.PangaeaOdyssey.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    private final BoardService boardService;
    @Autowired
    private final JwtService jwtService;
    public BoardController(BoardService boardService, JwtService jwtService) {
        this.boardService = boardService;
        this.jwtService = jwtService;
    }

    @GetMapping("/")
    public ResponseEntity<List<BoardDTO>> getAllBoards() {

        List<BoardDTO> dtos = boardService.getAllBoards();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id) {
        BoardDTO dto = boardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO dto) {
        BoardDTO boardDTO = boardService.createBoard(dto);

        return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id,
                                                @RequestBody BoardDTO updatedBoardDTO) {
        BoardDTO boardDTO = boardService.updateBoard(id, updatedBoardDTO);
        return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
    }

    @DeleteMapping("/delete/{id}/{password}")
    public ResponseEntity<BoardDTO> deleteBoard(@PathVariable Long id,
                                                @PathVariable(required = false) String password,
                                                @RequestHeader("Authorization") String token) {
        BoardDTO boardDTO = boardService.deleteBoard(id, password, token);
        return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
    }
    @PostMapping("/search")
    public ResponseEntity<List<BoardDTO>> searchBoards(@RequestBody SearchDTO request) {
        String keyword = request.getKeyword();
        String type = request.getType();

        List<BoardDTO> searchResults;
        if ("title".equalsIgnoreCase(type)) {
            searchResults = boardService.searchByTitle(keyword);
        } else if ("content".equalsIgnoreCase(type)) {
            searchResults = boardService.searchByContent(keyword);
        } else {
            // 기본적으로 제목과 내용 모두에서 검색
            searchResults = boardService.searchByKeyword(keyword);
        }

        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}