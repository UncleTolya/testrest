package ru.tolymhlv.testrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domain.User;
import ru.tolymhlv.testrest.repo.UserRepo;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private User findUser(final String ip, final String userAgent) {
        List<User> foundUsers = userRepo.findByIpAndUserAgent(ip, userAgent); //it must be only one
        if (!foundUsers.isEmpty()) {
            return foundUsers.get(0);
        } else {
            return null;
        }
    }

    private User addUser(final User user) {
        return userRepo.save(user);
    }

    public User getUser(final String ip, final String userAgent) {
        User user = findUser(ip, userAgent);
        if (user != null) {
            return user;
        } else {
            return addUser(new User(ip, userAgent));
        }
    }
}
