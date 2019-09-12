package pl.aleksandrabobowska.rest_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aleksandrabobowska.rest_project.db.entities.FilmEntity;

import java.util.Set;

@Data
@NoArgsConstructor
public class Character {
    private int characterId;
    private String characterName;
    private String characterURL;
    private String homeworld;
    private Set<FilmEntity> filmList;

    public Character(int characterId, String characterName, String characterURL, String homeworld, Set<FilmEntity> filmList) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterURL = characterURL;
        this.homeworld = homeworld;
        this.filmList = filmList;
    }
}
