package com.py.test.got.page.api.got.clients;

import com.py.test.got.page.api.got.dto.GetAllCharactersLocationResponseData;
import com.py.test.got.page.api.got.dto.GetCharactersPathResponse;
import com.py.test.got.page.api.got.dto.GetLocationsByNameResponseBody;
import com.py.test.got.page.api.got.dto.House;

public interface GotServiceClient {

    public GetLocationsByNameResponseBody getCharacterLocationsByName(String name);
    public GetAllCharactersLocationResponseData getAllCharactersLocation();
    public House[] getAllHouses();
    public GetCharactersPathResponse[] getCharactersPath();

}
