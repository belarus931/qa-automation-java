package apitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

public class QueryParamsTest extends BaseApiTest {

    @Test
    public void testFilterPostsByUserId(){
        given()
                .log().all()
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .body("userId", everyItem(equalTo(1)));
    }
}
