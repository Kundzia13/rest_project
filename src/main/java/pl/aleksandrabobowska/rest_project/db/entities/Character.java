package pl.aleksandrabobowska.rest_project.db.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "character_id")
    private int characterId;
    @Column(name = "character_name")
    private String characterName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Report report;

}
