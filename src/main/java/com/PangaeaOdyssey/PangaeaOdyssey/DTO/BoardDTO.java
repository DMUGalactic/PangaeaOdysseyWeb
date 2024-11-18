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
    private double views;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자
    public BoardDTO(Long id, String title, String content, double views, LocalDateTime createdAt, LocalDateTime updatedAt, String password) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.password = password;
    }
    public static BoardDTO createBoardDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getViews(),
                board.getCreatedAt(),
                board.getUpdatedAt(),
                board.getPassword()
        );
    }
    public Board toEntity() {
        Board board = new Board();
        board.setTitle(this.title);
        board.setContent(this.content);
        board.setViews(this.views);
        board.setPassword(this.password);
        return board;
    }
}