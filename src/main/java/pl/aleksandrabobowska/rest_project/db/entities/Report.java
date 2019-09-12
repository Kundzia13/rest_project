package pl.aleksandrabobowska.rest_project.db.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Report {
    @Id
    @Column(name = "report_id")
    private Long id;
    @Column(name = "query_criteria_character_phrase")
    private String queryCharacterPhrase;
    @Column(name = "query_criteria_planet_name")
    private String queryPlanetName;
    @Column(name = "films")
    @OneToMany(cascade=CascadeType.ALL)
//    @JoinColumn(name="report_id")
    private Set<FilmEntity> films = new HashSet<>();
    @Column(name = "characters")
    @OneToMany(cascade=CascadeType.ALL)
//    @JoinColumn(name="report_id")
    private Set<CharacterEntity> characters = new HashSet<>();
    @Column(name = "planet_id")
    private int planetId;
    @Column(name = "planet_name")
    private String planetName;

    public Report(String queryCharacterPhrase, String queryPlanetName,
                  Set<FilmEntity> filmList, Set<CharacterEntity> characterList, int planetId, String planetName) {
        this.queryCharacterPhrase = queryCharacterPhrase;
        this.queryPlanetName = queryPlanetName;
        this.films = filmList;
        this.characters = characterList;
        this.planetId = planetId;
        this.planetName = planetName;
    }
}

