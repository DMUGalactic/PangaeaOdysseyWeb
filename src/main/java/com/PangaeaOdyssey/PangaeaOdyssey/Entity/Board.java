package com.PangaeaOdyssey.PangaeaOdyssey.Entity;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "board") // 정확한 테이블 이름 지정
@Getter
@Setter
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String password; // 새로 추가된 비밀번호 필드

    private int views = 0;

    public void patch(BoardDTO dto) {
        if(dto.getTitle() != null){
            this.title = dto.getTitle();
        }
        if(dto.getContent() != null){
            this.content = dto.getContent();
        }
        if(this.password.equals(dto.getPassword())||dto.getPassword() != null){
            this.password = dto.getPassword();
        }
    }
}
