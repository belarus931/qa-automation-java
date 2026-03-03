package apitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UpdatePostTest extends BaseApiTest {

    @Test
    public void testUpdatePost(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"title\":\"My Post Update\",\"body\":\"This is a test post created by API\",\"userId\":1}")
                .when()
                .put("/posts/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("body", equalTo("This is a test post created by API"))
                .body("title", equalTo("My Post Update"));
        }
}