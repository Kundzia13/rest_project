package pl.aleksandrabobowska.rest_project;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.aleksandrabobowska.rest_project.db.repository.ReportRepository;
import pl.aleksandrabobowska.rest_project.model.Character;
import pl.aleksandrabobowska.rest_project.model.Film;
import pl.aleksandrabobowska.rest_project.model.Planet;
import pl.aleksandrabobowska.rest_project.util.Mappings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static pl.aleksandrabobowska.rest_project.util.Mappings.BASE_URL;
import static pl.aleksandrabobowska.rest_project.util.UrlPaths.*;

@Slf4j
@RestController
@RequestMapping(Mappings.BASE_URL)
public class ApiController {

    private ReportRepository reportRepository;

    @Autowired
    public ApiController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @PutMapping("/{id}")
    public void createReport(@PathVariable String id,
                                               @RequestBody RequestObject requestObject) {
        log.info("PUT /{}/{}, content: {}", BASE_URL, id, requestObject.toString());
        Planet askedPlanet = getAskedPlanet(requestObject);
        List<Character> askedPeople = getAskedPeople(requestObject);
        List<Character> filtered = askedPeople.stream().filter(c -> c.getHomeworld().
                equals(askedPlanet.getPlanetURL())).collect(Collectors.toList());
//        List<Report> reports = new ArrayList<>();
//        for (int i = 0; i < filtered.size(); i++) {
//            Report report = new Report(requestObject.getCharacterPhrase(), requestObject.getPlanetName(),
//                    filtered.get(0).getFilmList(),
//                    filtered.get(0).getCharacterId(), filtered.get(0).getCharacterName(),
//                    askedPlanet.getPlanetId(), askedPlanet.getPlanetName());
//            reports.add(report);
//        }

//        reportRepository.save(report);
//        return ResponseEntity.ok().body(report);
    }



    private List<Character> getAskedPeople(RequestObject requestObject) {
        List<Character> characterList = new ArrayList<>();
        JSONObject peopleJson = getJsonObject(PEOPLE_URL);

        while (peopleJson.get("next").toString() != "null") {
            JSONArray people = getObjects(peopleJson);
            for (int i = 0; i < people.length(); i++) {
                JSONObject person = people.getJSONObject(i);
                if (person.get("name").toString().toLowerCase().contains(requestObject.getCharacterPhrase().toLowerCase())) {
                    String characterName = person.get("name").toString();
                    String characterURL = person.get("url").toString();
                    int characterId = Integer.parseInt(characterURL.substring(28, characterURL.length() - 1));
                    String homeworld = person.get("homeworld").toString();
                    List<Film> filmList = new ArrayList<>();
                    JSONArray results = person.getJSONArray("films");
                    for (int j = 0; j < results.length(); j++) {
                        Film film = getAskedFilm(results.get(j).toString());
                        filmList.add(film);
                    }
                    Character character = new Character(characterId, characterName, characterURL, homeworld, filmList);
                    characterList.add(character);
                }
            }
            peopleJson = getJsonObject(peopleJson.get("next").toString());
        }
        return characterList;
    }

    private Planet getAskedPlanet(RequestObject requestObject) {
        JSONObject planetsJson = getJsonObject(PLANETS_URL);

        while (nonNull(planetsJson.get("next"))) {
            JSONArray planets = getObjects(planetsJson);
            for (int i = 0; i < planets.length(); i++) {
                JSONObject planet = planets.getJSONObject(i);
                if (planet.get("name").toString().equalsIgnoreCase(requestObject.getPlanetName())) {
                    String planetName = planet.get("name").toString();
                    String planetURL = planet.get("url").toString();
                    int planetId = Integer.parseInt(planetURL.substring(29, planetURL.length() - 1));
                    Planet askedPlanet = new Planet(planetId, planetName, planetURL);
                    return askedPlanet;
                }
            }
            planetsJson = getJsonObject(planetsJson.getString("next"));
        }
        return null;
    }

    private Film getAskedFilm(String filmUrl) {
        JSONObject planetsJson = getJsonObject(FILMS_URL);

        while (nonNull(planetsJson.get("next"))) {
            JSONArray films = getObjects(planetsJson);
            for (int i = 0; i < films.length(); i++) {
                JSONObject film = films.getJSONObject(i);
                if (film.get("url").toString().equalsIgnoreCase(filmUrl)) {
                    String filmName = film.get("title").toString();
                    String filmURL = film.get("url").toString();
                    int filmId = Integer.parseInt(filmURL.substring(27, filmURL.length() - 1));
                    Film askedFilm = new Film(filmId, filmName);
                    return askedFilm;
                }
            }
            planetsJson = getJsonObject(planetsJson.getString("next"));
        }
        return null;
    }

    private JSONArray getObjects(JSONObject jsonObject) {
        return jsonObject.getJSONArray("results");
    }

    private JSONObject getJsonObject(String link) {
        String content = Connect.getUrlContents(link + JSON_FORMAT_SUFIX);
        return new JSONObject(content);
    }
}

