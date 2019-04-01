package ru.tolymhlv.testrest.repos;

import org.springframework.data.repository.CrudRepository;
import ru.tolymhlv.testrest.domains.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepo extends CrudRepository<Visit, Long> {
    List<Visit> findByDateBetween(LocalDateTime from, LocalDateTime to);

}
