package ru.tolymhlv.testrest.repo;

import org.springframework.data.repository.CrudRepository;
import ru.tolymhlv.testrest.domain.User;
import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    List<User> findByIpAndUserAgent();
}
