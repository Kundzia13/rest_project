package pl.aleksandrabobowska.rest_project.db.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int id;
    @Column(name = "query_criteria_character_phrase")
    private String queryCharacterPhrase;
    @Column(name = "query_criteria_planet_name")
    private String queryPlanetName;
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    @Column(name = "films")
    private List<Film> filmList = new ArrayList<>();
    @Column(name = "characters")
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<Character> characterList= new ArrayList<>();
    @Column(name = "planet_id")
    private int planetId;
    @Column(name = "planet_name")
    private String planetName;

    public Report(String queryCharacterPhrase, String queryPlanetName,
                  List<Film> filmList, List<Character> characterList, int planetId, String planetName) {
        this.queryCharacterPhrase = queryCharacterPhrase;
        this.queryPlanetName = queryPlanetName;
        this.filmList = filmList;
        this.characterList = characterList;
        this.planetId = planetId;
        this.planetName = planetName;
    }
}

