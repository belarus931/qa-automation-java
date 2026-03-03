package apitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FirstApiTest extends BaseApiTest {

    @Test
    public void testGetPostById() {
        given()
                .log().all()  // логируем запрос (очень полезно для дебага)
                .when()
                .get("/posts/1")
                .then()
                .log().all()  // логируем ответ
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(emptyString()));
    }
}