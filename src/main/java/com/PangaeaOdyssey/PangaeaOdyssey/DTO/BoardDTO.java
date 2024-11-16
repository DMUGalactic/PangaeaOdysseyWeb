package com.PangaeaOdyssey.PangaeaOdyssey.DTO;

import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Board;
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
                board.getAuthor() != null ? board.getAuthor().getNickname() : null, // 작성자가 없을 경우 처리
                board.getViews(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }
    public Board toEntity() {
        Board board = new Board();
        board.setTitle(this.title);
        board.setContent(this.content);
        board.setViews(this.views);

        // Author 설정은 필요한 경우 추가 로직 필요
        // 예: board.setAuthor(memberRepository.findByNickname(this.authorNickname).orElse(null));

        return board;
    }
}