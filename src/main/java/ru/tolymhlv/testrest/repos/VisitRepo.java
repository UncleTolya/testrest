package ru.tolymhlv.testrest.repos;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tolymhlv.testrest.domains.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepo extends JpaRepository<Visit, Long> {

    List<Visit> findAllByDateBetween(@NonNull final LocalDateTime fromDate, final @NonNull LocalDateTime toDate);
}
