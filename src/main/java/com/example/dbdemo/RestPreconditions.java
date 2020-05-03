package com.example.dbdemo;

public class RestPreconditions {
    public static <T> T checkFound(T resource) {
        if (resource == null) {
            throw new RuntimeException("Rest Resource not found");
        }
        return resource;
    }
}