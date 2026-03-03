package apitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.Matchers.*;

public class CreatePostTest extends BaseApiTest {

    @Test
    public void testCreatePost(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"title\":\"My Test Post\",\"body\":\"This is a test post created by API\",\"userId\":1}")
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .body("body", equalTo("This is a test post created by API"))
                .body("title", equalTo("My Test Post"));
    }
}
