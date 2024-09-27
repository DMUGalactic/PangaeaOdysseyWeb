package com.PangaeaOdyssey.PangaeaOdyssey.Entity;

import com.PangaeaOdyssey.PangaeaOdyssey.Authority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String nickname;

    @Column
    private String filename;

    @Enumerated(EnumType.STRING)
    private Authority role;

    @Builder
    public Member(String id, String email, String password, String nickname, Authority role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}
