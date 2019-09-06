package pl.aleksandrabobowska.rest_project;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiController.BASE_URL)
public class ApiController {

    public static final String BASE_URL = "report";

    @PutMapping("/{id}")
    public void createReport(@PathVariable String id,
                             @RequestBody JSONObject requestObject) {
        log.info("PUT /{}/{}, content: {}", BASE_URL, id, requestObject.toString());
        String queryCharacter = requestObject.get("query_criteria_character_phrase").toString();
        System.out.println(queryCharacter);
        String queryPlanet = requestObject.get("query_criteria_planet_name").toString();
        System.out.println(queryPlanet);
    }
}
