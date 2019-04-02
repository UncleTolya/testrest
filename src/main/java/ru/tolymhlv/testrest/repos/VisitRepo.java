package ru.tolymhlv.testrest.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.tolymhlv.testrest.domains.Visit;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface VisitRepo extends CrudRepository<Visit, Long> {

    List<Visit> findAllByDateBetween(LocalDateTime fromDate, LocalDateTime toDate);



}
