package com.py.test.got.page.api.got.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class House {

    private List<String> titles;
    private List<String> overlords;
    private List<String> ancestralWeapon;
    private String _id;
    private String name;
    private String image;
    private String coatOfArms;
    private String words;
    private String currentLord;
    private String seat;
    private String region;
    private String founded;
    private String founder;
    private String cadetBranch;
    private String heir;
    private String isExtinct;
    private String createdAt;
    private String updatedAt;
    private String __v;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }
}
