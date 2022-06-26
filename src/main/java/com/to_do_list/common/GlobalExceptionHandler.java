package com.to_do_list.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class}) //Configure advising classes
@ResponseBody //Returning JSON data
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle SQL exception
     * @param exception
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public Result<String> sqlExceptionHandler(SQLException exception) {
        log.error(exception.getMessage());
        //When user didn't enter anything on adding assignment form
        if (exception.getMessage().contains("default value")) {
            return Result.error("Missing essential information!");
        }
        return Result.error("Unknown SQL exception");
    }
}
