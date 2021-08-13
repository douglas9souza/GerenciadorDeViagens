package com.montanha.gerenciador.utils;

public class Base {

    private static final String urlBase = "http://localhost:8089/api";

    public String buildUrl(String endPoint) {
        return urlBase + endPoint;
    }
}
