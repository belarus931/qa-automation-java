package apitests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("📊 Параметризованные тесты API")
public class ParameterizedTestsTest extends BaseApiTest {

    // ==================== 1. @ValueSource — простые значения ====================

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("GET /posts/{id} с разными ID")
    @Tag("smoke")
    public void testGetPostWithDifferentIds(int postId) {
        System.out.println("🔍 Тестируем пост с ID: " + postId);

        given()
                .pathParam("id", postId)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("userId", notNullValue())
                .body("title", not(emptyString()));
    }

    // ==================== 2. @ValueSource со строками ====================

    @ParameterizedTest
    @ValueSource(strings = {"posts", "comments", "albums"})
    @DisplayName("GET /{resource} — проверка доступности ресурсов")
    public void testAvailableResources(String resource) {
        System.out.println("🔍 Проверяем ресурс: " + resource);

        given()
                .pathParam("resource", resource)
                .when()
                .get("/{resource}")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    // ==================== 3. @CsvSource — несколько параметров ====================

    @ParameterizedTest
    @CsvSource({
            "1, 1, sunt aut facere repellat provident",
            "2, 1, qui est esse",
            "3, 1, ea molestias quasi exercitationem"
    })
    @DisplayName("GET /posts/{id} — проверка ID, userId и заголовка")
    public void testPostWithCsvSource(int id, int expectedUserId, String expectedTitleStart) {
        System.out.printf("🔍 ID: %d, ожидаемый userId: %d, заголовок начинается с: %s%n",
                id, expectedUserId, expectedTitleStart);

        given()
                .pathParam("id", id)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("userId", equalTo(expectedUserId))
                .body("title", startsWith(expectedTitleStart));
    }

    // ==================== 4. @CsvSource с разными типами ====================

    @ParameterizedTest
    @CsvSource({
            "1, true, 200",
            "99999, false, 404",
            "abc, false, 404"
    })
    @DisplayName("Разные сценарии: существующий/несуществующий пост")
    public void testVariousPostScenarios(String id, boolean shouldExist, int expectedStatus) {
        System.out.printf("🔍 ID: %s, должен существовать: %b, ожидаемый статус: %d%n",
                id, shouldExist, expectedStatus);

        Response response = given()
                .pathParam("id", id)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(expectedStatus)
                .extract()
                .response();

        if (shouldExist) {
            assertAll(
                    () -> assertEquals(id, response.jsonPath().getString("id")),
                    () -> assertNotNull(response.jsonPath().getString("title"))
            );
        }
    }

    // ==================== 5. @MethodSource — данные из метода ====================

    @ParameterizedTest
    @MethodSource("postIdsProvider")
    @DisplayName("GET /posts/{id} с данными из метода")
    public void testWithMethodSource(int postId) {
        System.out.println("🔍 MethodSource передал ID: " + postId);

        given()
                .pathParam("id", postId)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(postId));
    }

    static Stream<Integer> postIdsProvider() {
        System.out.println("📦 MethodSource генерирует данные...");
        return Stream.of(1, 3, 5, 7, 10, 15, 20);
    }

    // ==================== 6. @MethodSource с объектами ====================

    @ParameterizedTest
    @MethodSource("postDataProvider")
    @DisplayName("Тест с кастомными объектами данных")
    public void testWithCustomData(PostTestData testData) {
        System.out.printf("🔍 Тестируем: %s (userId: %d, ожидаемый статус: %d)%n",
                testData.description, testData.userId, testData.expectedStatus);

        given()
                .queryParam("userId", testData.userId)
                .when()
                .get("/posts")
                .then()
                .statusCode(testData.expectedStatus)
                .body("userId", everyItem(equalTo(testData.userId)));
    }

    static Stream<PostTestData> postDataProvider() {
        return Stream.of(
                new PostTestData("Первый пользователь", 1, 200),
                new PostTestData("Второй пользователь", 2, 200),
                new PostTestData("Несуществующий пользователь", 99999, 200) // API вернёт пустой массив
        );
    }

    // ==================== 7. @EnumSource — перечисления ====================

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    @DisplayName("GET /{resource} — все основные ресурсы")
    public void testAllResources(ResourceType resource) {
        System.out.println("🔍 Проверяем ресурс: " + resource.getEndpoint());

        given()
                .pathParam("resource", resource.getEndpoint())
                .when()
                .get("/{resource}")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @ParameterizedTest
    @EnumSource(value = ResourceType.class, names = {"POSTS", "COMMENTS"}, mode = EnumSource.Mode.INCLUDE)
    @DisplayName("GET — только определённые ресурсы")
    public void testSpecificResources(ResourceType resource) {
        System.out.println("🔍 Проверяем только важные ресурсы: " + resource);
        given().get("/" + resource.getEndpoint()).then().statusCode(200);
    }

    // ==================== 8. @NullSource и @EmptySource ====================

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    @DisplayName("Граничные значения для поиска")
    public void testSearchWithInvalidInput(String searchTerm) {
        System.out.println("🔍 Тестируем поиск с: '" + searchTerm + "'");

        given()
                .queryParam("q", searchTerm)
                .when()
                .get("/posts")
                .then()
                .statusCode(200); // API должен корректно обработать
    }

    // ==================== 9. @CsvFileSource — данные из файла ====================

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata/posts.csv", numLinesToSkip = 1)
    @DisplayName("GET /posts/{id} с данными из CSV-файла")
    public void testFromCsvFile(int id, int userId, String titlePrefix) {
        System.out.printf("🔍 Из CSV: ID=%d, userId=%d, title начинается с: %s%n",
                id, userId, titlePrefix);

        given()
                .pathParam("id", id)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("userId", equalTo(userId))
                .body("title", startsWith(titlePrefix));
    }

    // ==================== 10. Комбинация провайдеров ====================

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "test"})
    @DisplayName("Комбинация NullAndEmptySource + ValueSource")
    public void testCombinedSources(String param) {
        System.out.println("🔍 Тестируем с параметром: '" + param + "'");
        // Логика теста
    }


// ==================== ВСПОМОГАТЕЛЬНЫЕ КЛАССЫ ====================

    /**
     * Модель данных для тестов
     */
    static class PostTestData {
        String description;
        int userId;
        int expectedStatus;

        public PostTestData(String description, int userId, int expectedStatus) {
            this.description = description;
            this.userId = userId;
            this.expectedStatus = expectedStatus;
        }
    }

    /**
     * Enum с типами ресурсов
     */
    enum ResourceType {
        POSTS("posts"),
        COMMENTS("comments"),
        ALBUMS("albums"),
        PHOTOS("photos"),
        TODOS("todos"),
        USERS("users");

        private final String endpoint;

        ResourceType(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }
}