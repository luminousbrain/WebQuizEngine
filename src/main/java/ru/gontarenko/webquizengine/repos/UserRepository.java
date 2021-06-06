package ru.gontarenko.webquizengine.repos;

import org.springframework.data.repository.CrudRepository;
import ru.gontarenko.webquizengine.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
