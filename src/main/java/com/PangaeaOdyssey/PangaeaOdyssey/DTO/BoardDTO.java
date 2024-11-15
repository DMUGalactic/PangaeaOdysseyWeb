package com.PangaeaOdyssey.PangaeaOdyssey.DTO;

import java.time.LocalDateTime;

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

    // Getter와 Setter 필요 시 추가 (Lombok 사용 가능)
    public BoardDTO convertToDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getAuthor().getNickname(), // Member 객체에서 닉네임 추출
                board.getViews(),
                board.getCreatedAt(), // BaseEntity에서 상속받은 createdAt
                board.getUpdatedAt()  // BaseEntity에서 상속받은 updatedAt
        );
    }
}