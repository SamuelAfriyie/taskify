package com.example.taskify.core.failures;

public abstract class Failure {
    private final String RES_CODE;
    private final String RES_MSG;

    public Failure(String RES_CODE, String RES_MSG) {
        this.RES_CODE = RES_CODE;
        this.RES_MSG = RES_MSG;
    }

    public String getRES_CODE() {
        return RES_CODE;
    }

    public String getRES_MSG() {
        return RES_MSG;
    }
}
