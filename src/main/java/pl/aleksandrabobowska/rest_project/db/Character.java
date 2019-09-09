package pl.aleksandrabobowska.rest_project.db;

import lombok.Data;

import java.util.List;

@Data
public class Character {
    private int characterId;
    private String characterName;
    private String characterURL;
    private String homeworld;
    private List<Film> filmList;

    public Character(int characterId, String characterName, String characterURL, String homeworld, List<Film> filmList) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterURL = characterURL;
        this.homeworld = homeworld;
        this.filmList = filmList;
    }
}
