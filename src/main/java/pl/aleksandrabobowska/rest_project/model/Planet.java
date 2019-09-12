package pl.aleksandrabobowska.rest_project.model;

import lombok.Data;

@Data
public class Planet {
    private int planetId;
    private String planetName;
    private String planetURL;

    public Planet(int planetId, String planetName, String planetURL) {
        this.planetId = planetId;
        this.planetName = planetName;
        this.planetURL = planetURL;
    }
}
