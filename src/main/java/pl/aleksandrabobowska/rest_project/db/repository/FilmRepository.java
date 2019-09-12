package pl.aleksandrabobowska.rest_project.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.aleksandrabobowska.rest_project.db.entities.FilmEntity;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
    List<FilmEntity> findAll();
}
