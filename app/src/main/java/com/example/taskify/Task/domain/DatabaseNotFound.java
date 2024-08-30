package com.example.taskify.Task.domain;


import com.example.taskify.core.failures.Failure;

public class DatabaseNotFound extends Failure {

    public DatabaseNotFound(String ERROR_CODE, String ERROR_MSG) {
        super(ERROR_CODE, ERROR_MSG);
    }
}
