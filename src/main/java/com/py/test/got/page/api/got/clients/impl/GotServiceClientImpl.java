package com.py.test.got.page.api.got.clients.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.py.test.got.page.api.got.dto.GetAllCharactersLocationResponseData;
import com.py.test.got.page.api.got.dto.GetCharactersPathResponse;
import com.py.test.got.page.api.got.clients.GotServiceClient;
import com.py.test.got.page.api.got.dto.GetLocationsByNameResponseBody;
import com.py.test.got.page.api.got.dto.House;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.net.URIBuilder;

@Named("GotServiceClientImpl")
@SessionScoped
public class GotServiceClientImpl implements GotServiceClient, Serializable {

    public static final String BASE_URL = "https://api.got.show/api/book";
    public static final String CHARACTER_LOCATIONS = "/characterlocations";
    public static final String HOUSES = "/houses";
    public static final String CHARACTER_PATHS = "/characterpaths";

    @Override
    public GetLocationsByNameResponseBody getCharacterLocationsByName(String name) {
        GetLocationsByNameResponseBody response = new GetLocationsByNameResponseBody();
        ObjectMapper mapper = new ObjectMapper();
        try ( CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            URIBuilder builder = new URIBuilder(BASE_URL.concat(CHARACTER_LOCATIONS)).appendPath(name);
            HttpGet httpGet = new HttpGet(builder.build());
            response = httpClient.execute(httpGet, httpResponse -> mapper.readValue(httpResponse.getEntity().getContent(), GetLocationsByNameResponseBody.class));
        } catch (Exception e) {

        }

        return response;
    }

    @Override
    public GetAllCharactersLocationResponseData getAllCharactersLocation() {
        GetAllCharactersLocationResponseData response = new GetAllCharactersLocationResponseData();
        ObjectMapper mapper = new ObjectMapper();
        try ( CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            URIBuilder builder = new URIBuilder(BASE_URL.concat(CHARACTER_LOCATIONS));
            HttpGet httpGet = new HttpGet(builder.build());
            response = httpClient.execute(httpGet, httpResponse -> mapper.readValue(httpResponse.getEntity().getContent(), GetAllCharactersLocationResponseData.class));
        } catch (Exception e) {

        }
        return response;
    }

    @Override
    public House[] getAllHouses() {
        House[] response = null;
        ObjectMapper mapper = new ObjectMapper();
        try ( CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            URIBuilder builder = new URIBuilder(BASE_URL.concat(HOUSES));
            HttpGet httpGet = new HttpGet(builder.build());
            response = httpClient.execute(httpGet, httpResponse -> mapper.readValue(httpResponse.getEntity().getContent(), House[].class));
        } catch (Exception e) {

        }
        return response;
    }

    @Override
    public GetCharactersPathResponse[] getCharactersPath() {
        GetCharactersPathResponse[] response = null;
        ObjectMapper mapper = new ObjectMapper();
        try ( CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            URIBuilder builder = new URIBuilder(BASE_URL.concat(CHARACTER_PATHS));
            HttpGet httpGet = new HttpGet(builder.build());
            response = httpClient.execute(httpGet, httpResponse -> mapper.readValue(httpResponse.getEntity().getContent(), GetCharactersPathResponse[].class));
        } catch (Exception e) {

        }
        return response;
    }
}
