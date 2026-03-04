package apitests;

import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetPostsTest extends BaseApiTest {

    @Test
    public void testGetAllPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                // Проверка структуры ответа
                .body("", isA(List.class))
                .body("size()", equalTo(100))
                // Проверка типов данных
                .body("userId", everyItem(isA(Integer.class)))
                .body("id", everyItem(isA(Integer.class)))
                .body("title", everyItem(isA(String.class)))
                .body("body", everyItem(isA(String.class)))
                // Проверка наличия обязательных полей
                .body("userId", everyItem(notNullValue()))
                .body("id", everyItem(notNullValue()))
                .body("title", everyItem(not(emptyString())))
                .body("body", everyItem(not(emptyString())));
    }

    @Test
    public void testFirstPostStructure() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", isA(Integer.class))
                .body("title", startsWith("sunt"))  // более специфичная проверка
                .body("body", containsString("quia"));
    }
}