package com.py.test.got.page.api.got.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetCharactersPathResponse {
    private String _id;
    private String name;
    private String __v;
    private List<Path> path;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }
    
    
}
