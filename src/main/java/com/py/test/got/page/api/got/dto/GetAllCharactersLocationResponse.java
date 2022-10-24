package com.py.test.got.page.api.got.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllCharactersLocationResponse {

    private List<String> locations;
    private String _id;
    private String name;
    private String slug;
    private Integer __v;
}
