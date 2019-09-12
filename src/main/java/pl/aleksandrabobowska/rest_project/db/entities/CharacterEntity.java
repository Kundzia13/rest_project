package pl.aleksandrabobowska.rest_project.db.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class CharacterEntity {
    @Id
    @Column(name = "character_id")
    private int characterId;
    @Column(name = "character_name")
    private String characterName;
@JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Report report;

    public CharacterEntity(int characterId, String characterName) {
        this.characterId = characterId;
        this.characterName = characterName;
    }
}
