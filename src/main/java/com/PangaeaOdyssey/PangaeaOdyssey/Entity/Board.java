package com.PangaeaOdyssey.PangaeaOdyssey.Entity;

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
}
