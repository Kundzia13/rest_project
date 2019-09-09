package pl.aleksandrabobowska.rest_project;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestObject {

    @JsonAlias("query_criteria_character_phrase")
    private String characterPhrase;
    @JsonAlias("query_criteria_planet_name")
    private String planetName;
}
