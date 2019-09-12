package pl.aleksandrabobowska.rest_project.model;

import lombok.Data;

@Data
public class Film {
    private int filmId;
    private String filmName;
    private String filmURL;

    public Film(int filmId, String filmName, String filmURL) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.filmURL = filmURL;
    }

    public Film(int filmId, String filmName) {
        this.filmId = filmId;
        this.filmName = filmName;
    }
}
