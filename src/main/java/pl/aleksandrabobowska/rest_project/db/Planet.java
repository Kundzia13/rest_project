package pl.aleksandrabobowska.rest_project.db;

import lombok.Data;

@Data
public class Planet {
    private int planetId; // (int) planetURL.substring(29,30);
    private String planetName;
    private String planetURL;

    public Planet(int planetId, String planetName, String planetURL) {
        this.planetId = planetId;
        this.planetName = planetName;
        this.planetURL = planetURL;
    }
}
