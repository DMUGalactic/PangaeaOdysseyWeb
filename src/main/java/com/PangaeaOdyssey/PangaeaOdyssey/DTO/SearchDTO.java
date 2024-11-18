package com.PangaeaOdyssey.PangaeaOdyssey.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchDTO {
    private String keyword;
    private String type; // "title" or "content"
}
