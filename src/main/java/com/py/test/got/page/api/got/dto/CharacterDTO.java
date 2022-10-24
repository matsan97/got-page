package com.py.test.got.page.api.got.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDTO {
    private String characterName;
    private String CharacterId;
    private String slug;
    
}
