package com.PangaeaOdyssey.PangaeaOdyssey.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER")
    ;
    private final String key;
}