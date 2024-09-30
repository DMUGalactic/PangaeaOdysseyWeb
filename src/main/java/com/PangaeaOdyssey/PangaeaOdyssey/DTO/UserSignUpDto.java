package com.PangaeaOdyssey.PangaeaOdyssey.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {
    private String id;
    private String email;
    private String password;
    private String nickname;
}
