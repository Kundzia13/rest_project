package pl.aleksandrabobowska.rest_project.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ManyToOne(cascade = CascadeType.ALL)
    private Report report;

    public FilmEntity(int filmId, String filmName) {
        this.filmId = filmId;
        this.filmName = filmName;
    }
}
