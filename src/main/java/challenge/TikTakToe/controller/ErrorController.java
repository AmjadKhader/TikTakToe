package challenge.TikTakToe.controller;

import challenge.TikTakToe.exception.InvalidMoveException;
import challenge.TikTakToe.exception.InvalidPlayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidMoveException.class)
    public String handleInvalidMoveException(InvalidMoveException e) {
        logger.error("Move is not acceptable ...", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPlayerException.class)
    public String handleInvalidPlayerException(InvalidPlayerException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e) {
        return "Game Id is incorrect";
    }

}
