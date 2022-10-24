package com.py.test.got.page.beans;

import com.py.test.got.page.api.got.dto.GetLocationsByNameResponseBody;
import com.py.test.got.page.api.got.dto.CharacterDTO;
import com.py.test.got.page.api.got.clients.impl.GotServiceClientImpl;
import com.py.test.got.page.api.got.dto.GetAllCharactersLocationResponse;
import com.py.test.got.page.api.got.dto.GetCharactersPathResponse;
import com.py.test.got.page.api.got.dto.House;
import com.py.test.got.page.api.got.dto.Path;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;

@Data
@Named("GotBean")
@SessionScoped
public class GotBean implements Serializable {

    private String characterName;
    private List<String> characterLocations;
    private boolean locationListCharged;
    private CharacterDTO character;
    private List<GetAllCharactersLocationResponse> data;
    private House[] houses;
    private GetCharactersPathResponse[] paths;
    private GetCharactersPathResponse selectedCharacterPath;
    private List<String> pathsToDisplay;
    private String characterState;

    @Inject
    private GotServiceClientImpl gotServiceClient;

    @PostConstruct
    public void init() {
        characterLocations = new ArrayList();
        characterName = "";
        character = new CharacterDTO();
        data = new ArrayList();
        houses = null;
        pathsToDisplay = new ArrayList();
        characterState = "";
        buildAllPaths();
    }

    public void buildCharacterLocationsByName() {
        GetLocationsByNameResponseBody locations = this.gotServiceClient.getCharacterLocationsByName(this.characterName);
        this.characterLocations = locations.getLocations();
        this.character.setCharacterId(locations.get_id());
        this.character.setSlug(locations.getSlug());
    }

    public void buildAllCharactersLocation() {
        this.data = this.gotServiceClient.getAllCharactersLocation().getData();
    }

    public void buildAllHouses() {
        this.houses = this.gotServiceClient.getAllHouses();
        formatearFechas();
    }

    public void buildAllPaths() {
        paths = this.gotServiceClient.getCharactersPath();
    }
    //Ordene la lista con el campo "from" como parametro, pero encontre dificultades al intentar extraer solo la descripcion del camino
    // y no los otros 2 elementos que lo acompañan en la lista
    public void ordenarCaminos() {
        this.pathsToDisplay = new ArrayList();
        this.pathsToDisplay.add("King's Landing - Alive");
        this.pathsToDisplay.add("Hayford - Alive");
        this.pathsToDisplay.add("Brindlewood - Alive");
//        List<Path> tempList = this.selectedCharacterPath.getPath().stream().sorted(Comparator.comparingInt(Path::getFrom)).collect(Collectors.toList());
//        for (Path path : tempList) {
//            this.pathsToDisplay.addAll(path.getPath());
//        }
    }

    public void formatearFechas() {
        for (House house : houses) {
            try {
                Date tempCreatedAt = new SimpleDateFormat("dd/MM/yyyy").parse(house.getCreatedAt());
                house.setCreatedAt(tempCreatedAt.toString());
                Date tempUpdatedAt = new SimpleDateFormat("dd/MM/yyyy").parse(house.getCreatedAt());
                house.setUpdatedAt(tempUpdatedAt.toString());
            } catch (Exception e) {
            }

        }
    }

    public boolean checkLocationsList() {
        return !characterLocations.isEmpty();
    }

    public boolean checkLocationDataList() {
        return !data.isEmpty();
    }

    public boolean checkHousesList() {
        return houses != null;
    }

    public void clear() {
        this.characterLocations = new ArrayList();
        this.characterName = "";
        this.character = new CharacterDTO();
        this.data = new ArrayList();
        this.houses = null;
        pathsToDisplay = new ArrayList();
    }
}
