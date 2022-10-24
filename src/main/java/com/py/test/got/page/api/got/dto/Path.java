package com.py.test.got.page.api.got.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Path {
    private Integer from;
    private Integer to;
    private String alive;
    private List<List<String>> path;
}
