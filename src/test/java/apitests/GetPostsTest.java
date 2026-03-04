package apitests;

import org.junit.jupiter.api.*;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Тесты для получения постов")
public class GetPostsTest extends BaseApiTest {

    @Test
    @DisplayName("GET /posts — получение всех постов")
    @Tag("smoke")
    public void testGetAllPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("", isA(List.class))
                .body("size()", equalTo(100))
                .body("userId", everyItem(isA(Integer.class)))
                .body("id", everyItem(isA(Integer.class)))
                .body("title", everyItem(not(emptyString())));
    }

    @Test
    @DisplayName("GET /posts/1 — получение поста с ID=1")
    @Tag("smoke")
    public void testGetPostById() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", isA(Integer.class))
                .body("title", not(emptyString()));
    }

    @Test
    @DisplayName("GET /posts — проверка, что все ID уникальны")
    @Tag("regression")
    public void testAllIdsAreUnique() {
        List<Integer> ids =
                given()
                        .when()
                        .get("/posts")
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getList("id", Integer.class);

        long uniqueCount = ids.stream().distinct().count();
        Assertions.assertEquals(ids.size(), uniqueCount, "Найдены дубликаты ID!");
    }

    @Test
    @DisplayName("GET /posts — проверка структуры ответа")
    @Tag("regression")
    public void testResponseStructure() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200).body("", everyItem(allOf(hasKey("userId"), hasKey("id"), hasKey("title"), hasKey("body"))));
    }
}