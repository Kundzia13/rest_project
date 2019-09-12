package pl.aleksandrabobowska.rest_project.db.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "film_id")
    private int filmId;
    @Column(name = "film_name")
    private String filmName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Report report;
}
