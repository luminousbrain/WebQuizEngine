package ru.gontarenko.webquizengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.webquizengine.entity.User;
import ru.gontarenko.webquizengine.exception.UserException;
import ru.gontarenko.webquizengine.service.UserService;

import javax.validation.Valid;

@RestController
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        boolean save = userService.save(user);
        if (!save) {
            throw new UserException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
