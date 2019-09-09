package pl.aleksandrabobowska.rest_project.db;

import lombok.Data;

import java.util.List;

@Data
public class Character {
    private int characterId;
    private String characterName;
    private String characterURL;
    private List<Film> filmList;

    public Character(int characterId, String characterName, String characterURL) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterURL = characterURL;
    }
}
