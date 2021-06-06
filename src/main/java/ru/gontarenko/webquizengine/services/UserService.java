package ru.gontarenko.webquizengine.services;

import ru.gontarenko.webquizengine.entities.User;

public interface UserService {
    User findByEmail(String email);
    boolean save(User user);
}
