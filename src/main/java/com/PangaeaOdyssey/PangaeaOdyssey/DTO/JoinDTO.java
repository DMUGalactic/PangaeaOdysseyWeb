package com.PangaeaOdyssey.PangaeaOdyssey.DTO;

import com.PangaeaOdyssey.PangaeaOdyssey.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {
    private String id;
    private String password;
    private String nickname;
    private String email;
    private String filename;
    private String role;
}
