package ru.tolymhlv.testrest.repo;

import org.springframework.data.repository.CrudRepository;
import ru.tolymhlv.testrest.domain.Visit;

public interface VisitRepo extends CrudRepository<Visit, Long> {

}
