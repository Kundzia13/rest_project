package pl.aleksandrabobowska.rest_project.db;

import lombok.Builder;
import lombok.Getter;
import pl.aleksandrabobowska.rest_project.db.entities.Report;

import java.util.List;

@Getter
@Builder
public class Response {
    private List<Report> reports;
}
