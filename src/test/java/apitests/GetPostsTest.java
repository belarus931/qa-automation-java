package apitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetPostsTest extends BaseApiTest {

    @Test
    public void testGetAllPosts() {
        given()
                .log().all()
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .body("", isA(List.class))        // корень ответа — список
                .body("size()", greaterThan(2));
    }
}