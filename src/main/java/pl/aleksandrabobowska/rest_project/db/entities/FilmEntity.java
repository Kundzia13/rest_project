package pl.aleksandrabobowska.rest_project.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class FilmEntity {
    @Id
    @Column(name = "film_id")
    private int filmId;
    @Column(name = "film_name")
    private String filmName;

    @JsonIgnore
    @ManyToOne
    private Report report;

    public FilmEntity(int filmId, String filmName) {
        this.filmId = filmId;
        this.filmName = filmName;
    }
}
