package apitests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class NegativeTests extends BaseApiTest {

    // 1. Несуществующий ресурс
    @Test
    public void testNonExistentPost() {
        given()
                .when()
                .get("/posts/999999")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));  // пустой объект
    }

    // 2. Неверный эндпоинт
    @Test
    public void testInvalidEndpoint() {
        given()
                .when()
                .get("/invalid-endpoint")
                .then()
                .statusCode(404);
    }

    // 3. POST с неполными данными
    @Test
    public void testCreatePostWithMissingFields() {
        given()
                .contentType("application/json")
                .body("{\"title\":\"Missing fields\"}")  // нет userId и body
                .when()
                .post("/posts")
                .then()
                .statusCode(201)  // jsonplaceholder всё равно создаёт
                .body("id", notNullValue());  // но id генерируется
    }

    // 4. POST с пустым телом
    @Test
    public void testCreatePostWithEmptyBody() {
        given()
                .contentType("application/json")
                .body("")
                .when()
                .post("/posts")
                .then()
                .statusCode(201);  // Bad Request
    }

    // 5. Неверный метод (GET вместо DELETE)
    @Test
    public void testWrongMethod() {
        given()
                .when()
                .get("/posts/1")  // используем GET, хотя должны DELETE
                .then()
                .statusCode(200);  // это успех, потому что GET работает всегда
        // Это показывает, что API не строгое
    }

    // 6. PUT на несуществующий ресурс
    @Test
    public void testUpdateNonExistentPost() {
        given()
                .contentType("application/json")
                .body("{\"title\":\"Updated\"}")
                .when()
                .put("/posts/999999")
                .then()
                .statusCode(500);  // jsonplaceholder возвращает 500
    }
}