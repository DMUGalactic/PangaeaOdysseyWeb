package com.PangaeaOdyssey.PangaeaOdyssey.DTO;

import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Board;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String authorNickname;
    private int views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자
    public BoardDTO(Long id, String title, String content, String authorNickname, int views, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorNickname = authorNickname;
        this.views = views;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public static BoardDTO createBoardDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getAuthor().getNickname(),
                board.getViews(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }
    public Board toEntity(Member author) {
        Board board = new Board();
        board.setTitle(this.title);
        board.setContent(this.content);
        board.setViews(this.views);
        board.setAuthor(author); // Member 객체를 설정
        return board;
    }
}