package pl.aleksandrabobowska.rest_project.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(name = "film_id")
    private int filmId;
    @Column(name = "film_name")
    private String filmName;
    @Column(name = "character_id")
    private int characterId;
    @Column(name = "character_name")
    private String characterName;
    @Column(name = "planet_id")
    private int planetId;
    @Column(name = "planet_name")
    private String planetName;

}
