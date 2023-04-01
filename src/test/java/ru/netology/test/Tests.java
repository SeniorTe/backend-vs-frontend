package ru.netology.test;

import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLOutput;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

class Tests {

    @SneakyThrows
    public static String getAuthCode() {
        var codeSQLFromSelectCode = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            var request = runner.query(conn, codeSQLFromSelectCode, new ScalarHandler<>());
            return (String) request;
        }
    }

    JSONObject authBody = new JSONObject()
            .put("login", "vasya")
            .put("password", "qwerty123")
            ;

    JSONObject codeBody = new JSONObject()
            .put("login", "vasya")
            .put("code", getAuthCode())
            ;

    JSONObject transferBody = new JSONObject()
            .put("from", "5559000000000002")
            .put("to", "5559000000000001")
            .put("amount", "5000")
            ;

    String token;

    @Test
    void shouldReturnRest() {
        given()
                .baseUri("http://localhost:9999/api")
                .contentType("application/json")
                .body(authBody.toString())
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                ;

    }

//    @Test
//    void testAuth() {
//        given()
//                .baseUri("http://localhost:9999/api")
//                .contentType("application/json")
//                .body(codeBody.toString())
//                .when()
//                .post("/auth/verification")
//                .then()
//                .statusCode(200)
//        ;
//    }

    @Test
    void testCode() {
        Response response = given()
                .baseUri("http://localhost:9999/api")
                .contentType("application/json")
                .body(codeBody.toString())
                .when()
                .post("/auth/verification")
                .then()
                .statusCode(200)
                .extract()
                .response()
        ;
        token = response.path("token");
        System.out.println(token);
    }

//    @Test
//    void testCards() {
//        Response response = given()
//                .header("Authorization","Bearer Token" + token)
//                .baseUri("http://localhost:9999/api")
//                .contentType("application/json")
//                .when()
//                .get("/cards")
//                .then()
//                .statusCode(200)
//                .extract()
//                .response()
//                ;
//        String cards = response.path("balance")
//        System.out.println(cards);
//    }

    @Test
    void testTransfer() {
        given()
                .header("Authorization", "Bearer Token" + token)
                .baseUri("http://localhost:9999/api")
                .contentType("application/json")
                .body(transferBody.toString())
                .when()
                .post("/transfer")
                .then()
                .statusCode(200)
        ;
    }

}