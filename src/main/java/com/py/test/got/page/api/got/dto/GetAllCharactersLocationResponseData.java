package com.py.test.got.page.api.got.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllCharactersLocationResponseData {

    private List<GetAllCharactersLocationResponse> data;
    private String message;

}
