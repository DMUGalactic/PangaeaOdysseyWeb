package com.PangaeaOdyssey.PangaeaOdyssey.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_nickname", referencedColumnName = "nickname")
    private Member author;

    private int views = 0;
}
