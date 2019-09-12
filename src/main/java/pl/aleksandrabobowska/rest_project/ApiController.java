package pl.aleksandrabobowska.rest_project;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.aleksandrabobowska.rest_project.db.Response;
import pl.aleksandrabobowska.rest_project.db.entities.CharacterEntity;
import pl.aleksandrabobowska.rest_project.db.entities.FilmEntity;
import pl.aleksandrabobowska.rest_project.db.entities.Report;
import pl.aleksandrabobowska.rest_project.db.repository.CharacterRepository;
import pl.aleksandrabobowska.rest_project.db.repository.FilmRepository;
import pl.aleksandrabobowska.rest_project.db.repository.ReportRepository;
import pl.aleksandrabobowska.rest_project.model.Character;
import pl.aleksandrabobowska.rest_project.model.Planet;
import pl.aleksandrabobowska.rest_project.util.Mappings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static pl.aleksandrabobowska.rest_project.util.Mappings.BASE_URL;
import static pl.aleksandrabobowska.rest_project.util.UrlPaths.*;

@Slf4j
@RestController
@RequestMapping(Mappings.BASE_URL)
public class ApiController {

    private ReportRepository reportRepository;

    private FilmRepository filmRepository;

    private CharacterRepository characterRepository;

    @Autowired
    public ApiController(ReportRepository reportRepository,
                         FilmRepository filmRepository,
                         CharacterRepository characterRepository) {
        this.reportRepository = reportRepository;
        this.filmRepository = filmRepository;
        this.characterRepository = characterRepository;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> createReport(@PathVariable Long id,
                                               @RequestBody RequestObject requestObject) {
        log.info("PUT /{}/{}, content: {}", BASE_URL, id, requestObject.toString());
        Planet askedPlanet = getAskedPlanet(requestObject);
        List<Character> askedPeople = getAskedPeople(requestObject);
        List<Character> filtered = getFiltered(askedPeople, askedPlanet);
        List<CharacterEntity> characters = createCharactersList(filtered);
        Set<FilmEntity> films = createFilmSet(filtered);
        Report report = new Report(requestObject.getCharacterPhrase(),
                requestObject.getPlanetName(),
                films, characters,
                askedPlanet.getPlanetId(), askedPlanet.getPlanetName());
        report.setId(id);
        reportRepository.save(report);
        return ResponseEntity.ok().body(report);
    }

    @GetMapping
    public ResponseEntity<Response> findAllReports() {
        log.info("GET /{}", BASE_URL);
        List<Report> reportsList = new ArrayList<>();
        reportRepository.findAll().forEach(e -> reportsList.add(e));
        return ResponseEntity.ok()
                .body(Response.builder().reports(reportsList).build());
    }

    @GetMapping("/{id}")
    public Report findReportById(@PathVariable Long id) {
        log.info("GET /{}/{}", BASE_URL, id);
        Report report = reportRepository.findById(id).get();
        return report;
    }

    @DeleteMapping
    public void deleteAllReports() {
        log.info("GET /{}", BASE_URL);
        List<Report> reportsList = new ArrayList<>();
        reportRepository.findAll().forEach(e -> reportsList.add(e));
        reportRepository.deleteAll();
    }


    @DeleteMapping("/{id}")
    public void deleteReportById(@PathVariable Long id) {
        log.info("GET /{}/{}", BASE_URL, id);
        Report report = reportRepository.findById(id).get();
        reportRepository.delete(report);
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
                    Set<FilmEntity> filmList = new HashSet<>();
                    JSONArray results = person.getJSONArray("films");
                    for (int j = 0; j < results.length(); j++) {
                        FilmEntity film = getAskedFilm(results.get(j).toString());
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

    private FilmEntity getAskedFilm(String filmUrl) {
        JSONObject planetsJson = getJsonObject(FILMS_URL);

        while (nonNull(planetsJson.get("next"))) {
            JSONArray films = getObjects(planetsJson);
            for (int i = 0; i < films.length(); i++) {
                JSONObject film = films.getJSONObject(i);
                if (film.get("url").toString().equalsIgnoreCase(filmUrl)) {
                    String filmName = film.get("title").toString();
                    String filmURL = film.get("url").toString();
                    int filmId = Integer.parseInt(filmURL.substring(27, filmURL.length() - 1));
                    FilmEntity askedFilm = new FilmEntity(filmId, filmName);
                    return askedFilm;
                }
            }
            planetsJson = getJsonObject(planetsJson.getString("next"));
        }
        return null;
    }

    private List<Character> getFiltered(List<Character> list,
                                        Planet planet) {
        List<Character> filtered = list.stream().filter(c -> c.getHomeworld().
                equals(planet.getPlanetURL())).collect(Collectors.toList());
        return filtered;
    }

    private List<CharacterEntity> createCharactersList(List<Character> list) {
        List<CharacterEntity> characters = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CharacterEntity character = new CharacterEntity(
                    list.get(i).getCharacterId(), list.get(i).getCharacterName());
            characters.add(character);
        }
        return characters;
    }

    private Set<FilmEntity> createFilmSet(List<Character> list) {
        Set<FilmEntity> films = new HashSet<>();
        Set<FilmEntity> filmList = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            filmList = list.get(i).getFilmList();
            films.addAll(filmList);
        }
        return films;
    }

    private JSONArray getObjects(JSONObject jsonObject) {
        return jsonObject.getJSONArray("results");
    }

    private JSONObject getJsonObject(String link) {
        String content = Connect.getUrlContents(link + JSON_FORMAT_SUFIX);
        return new JSONObject(content);
    }
}

