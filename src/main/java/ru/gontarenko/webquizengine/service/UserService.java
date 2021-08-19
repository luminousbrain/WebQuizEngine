package ru.gontarenko.webquizengine.service;

import ru.gontarenko.webquizengine.entity.User;

public interface UserService {
    User findByEmail(String email);

    boolean save(User user);
}
