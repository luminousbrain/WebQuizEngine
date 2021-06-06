package ru.gontarenko.webquizengine.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "user already exists")
public class UserException extends RuntimeException{
}
