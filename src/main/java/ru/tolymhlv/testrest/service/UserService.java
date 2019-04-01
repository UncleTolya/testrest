package ru.tolymhlv.testrest.service;

import ru.tolymhlv.testrest.domain.User;
import ru.tolymhlv.testrest.repo.UserRepo;

public class UserService {
    final private UserRepo userRepo;

    public UserService(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findUser(final String ip, final String userAgent) {
        return userRepo.findByIpAndUserAgent(ip, userAgent);
    }
}
