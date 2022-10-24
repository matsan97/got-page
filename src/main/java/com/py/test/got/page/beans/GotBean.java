package com.py.test.got.page.beans;

import com.py.test.got.page.api.got.dto.GetLocationsByNameResponseBody;
import com.py.test.got.page.api.got.dto.CharacterDTO;
import com.py.test.got.page.api.got.clients.impl.GotServiceClientImpl;
import com.py.test.got.page.api.got.dto.GetAllCharactersLocationResponse;
import com.py.test.got.page.api.got.dto.GetCharactersPathResponse;
import com.py.test.got.page.api.got.dto.House;
import com.py.test.got.page.api.got.dto.Path;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;
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
    //evaluo el campo "alive" como String porque tuve problemas al deserializar como "boolean" con jackson
    public static final String ALIVE = "true";

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
        formatName();
        GetLocationsByNameResponseBody locations = this.gotServiceClient.getCharacterLocationsByName(this.characterName);
        this.characterLocations = locations.getLocations();
        this.character.setCharacterId(locations.get_id());
        this.character.setSlug(locations.getSlug());
        this.characterName = "";
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

    //formatea entrada "name" a formato que acepta el recurso GET
    public void formatName() {
        String[] fullName = this.characterName.split(" ");
        String name = fullName[0].toUpperCase().charAt(0) + fullName[0].substring(1, fullName[0].length()).toLowerCase();
        String lastName = fullName[1].toUpperCase().charAt(0) + fullName[1].substring(1, fullName[1].length()).toLowerCase();
        this.characterName = name + " " + lastName;
    }

    // primero se ordena la lista de Path que se selecciono desde el front
    // como el string con el nombre no es un elemento recurrente en la lista de listas
    // pero si el string existe siempre esta en el indice(2) se evalua si path2.get(2) existe
    // finalmente se concatena el estado del personaje en ese camino (alive)
    public void sortPaths() {
        this.pathsToDisplay = new ArrayList();
        List<Path> sortedPathList = this.selectedCharacterPath.getPath().stream().sorted(Comparator.comparingInt(Path::getFrom)).collect(Collectors.toList());
        buildSortedPathList(sortedPathList);
    }

    public void buildSortedPathList(List<Path> sortedPathList) {
        for (Path path : sortedPathList) {
            for (List<String> path2 : path.getPath()) {
                if (Objects.nonNull(path2.get(2))) {
                    addCharacterStateInPath(path2.get(2), path);
                }
            }
        }
    }

    public void addCharacterStateInPath(String place, Path path) {
        if (ALIVE.equals(path.getAlive())) {
            this.pathsToDisplay.add(place.concat(" - Vivo"));
        } else {
            this.pathsToDisplay.add(place.concat(" - Muerto"));
        }
    }

    public void formatearFechas() {
        for (House house : houses) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date tempCreatedAt = formatter.parse(house.getCreatedAt());
                LocalDate ldCreatedAt = tempCreatedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                house.setCreatedAt(ldCreatedAt.toString());
                Date tempUpdatedAt = formatter.parse(house.getUpdatedAt());
                LocalDate ldUpdatedAt = tempUpdatedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                house.setUpdatedAt(ldUpdatedAt.toString());
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
