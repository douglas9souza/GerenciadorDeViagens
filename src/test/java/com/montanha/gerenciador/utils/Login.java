package com.montanha.gerenciador.utils;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class Login extends Base {

    private String loginAdmin = "{\n" +
            "  \"email\": \"admin@email.com\",\n" +
            "  \"senha\": \"654321\"\n" +
            "}";

    private String loginUser = "{\n" +
            "  \"email\": \"usuario@email.com\",\n" +
            "  \"senha\": \"123456\"\n" +
            "}";

    public String authenticationAdmin() {
        String tokenAdmin = given()
                .body(loginAdmin)
                .contentType(ContentType.JSON)
                .when()
                .post(buildUrl("/v1/auth"))
                .then()
                .extract()
                .path("data.token");

        return tokenAdmin;
    }

    public String authenticationUser() {
        String tokenUser = given()
                .body(loginUser)
                .contentType(ContentType.JSON)
                .when()
                .post(buildUrl("/v1/auth"))
                .then()
                .extract()
                .path("data.token");

        return tokenUser;
    }

}
