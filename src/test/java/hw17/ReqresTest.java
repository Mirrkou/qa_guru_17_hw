package hw17;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void getListUsersStatus200() {
        get("/api/users?page=2")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteStatus204() {
        delete("/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void successfulLoginStatus200() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                      "\"password\": \"cityslicka\"}")
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void postStatus201() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"poteryashka\", \"job\": \"rabota ne volk\"}")
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", is("poteryashka"));
    }

    @Test
    void getListUsersStatus404() {
        get("/ap/users?page=2")
                .then()
                .statusCode(404);
    }
}