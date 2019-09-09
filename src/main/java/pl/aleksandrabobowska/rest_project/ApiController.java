package pl.aleksandrabobowska.rest_project;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;
import static pl.aleksandrabobowska.rest_project.Mappings.BASE_URL;
import static pl.aleksandrabobowska.rest_project.UrlPaths.JSON_FORMAT_SUFIX;
import static pl.aleksandrabobowska.rest_project.UrlPaths.PLANETS_URL;

@Slf4j
@RestController
@RequestMapping(Mappings.BASE_URL)
public class ApiController {

    @PutMapping("/{id}")
    public void createReport(@PathVariable String id,
                             @RequestBody RequestObject requestObject) {
        log.info("PUT /{}/{}, content: {}", BASE_URL, id, requestObject.toString());
        JSONObject askedPlanet = getAskedPlanet(requestObject);
        System.out.println(askedPlanet);
    }

    private JSONObject getAskedPlanet(RequestObject requestObject) {
        JSONObject planetsJson = getJsonObject(PLANETS_URL);


        while (nonNull(planetsJson.get("next"))) {
            JSONArray planets = getObjects(planetsJson);
            for (int i = 0; i < planets.length(); i++) {
                JSONObject planet = planets.getJSONObject(i);
                if (planet.get("name").toString().equalsIgnoreCase(requestObject.getPlanetName())) {
                    return planet;
                }
            }
            planetsJson = getJsonObject(planetsJson.getString("next"));
        }
        return null;
    }

    private JSONArray getObjects(JSONObject planetsJson) {
        return planetsJson.getJSONArray("results");
    }

    private JSONObject getJsonObject(String link) {
        String content = Connect.getUrlContents(link + JSON_FORMAT_SUFIX);
        return new JSONObject(content);
    }
}

