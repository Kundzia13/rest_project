package pl.aleksandrabobowska.rest_project.db.entities;

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

    @ManyToOne(cascade = CascadeType.ALL)
    private Report report;

    public FilmEntity(int filmId, String filmName) {
        this.filmId = filmId;
        this.filmName = filmName;
    }
}
