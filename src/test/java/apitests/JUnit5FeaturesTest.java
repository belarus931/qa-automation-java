package apitests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Демонстрация возможностей JUnit 5")
public class JUnit5FeaturesTest extends BaseApiTest {

    @Test
    @DisplayName("🔥 Тест с кастомным именем")
    @Tag("demo")
    public void testWithCustomName() {
        Assertions.assertTrue(true);
    }

    @Test
    @Disabled("Пока не реализовано")
    @DisplayName("⏸ Отключённый тест")
    public void disabledTest() {
        // Этот тест не будет выполняться
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("📊 Параметризованный тест для проверки ID")
    public void testMultiplePosts(int postId) {
        given()
                .when()
                .get("/posts/" + postId)
                .then()
                .statusCode(200)
                .body("id", equalTo(postId));
    }

    @Test
    @DisplayName("⏱ Проверка времени ответа")
    public void testResponseTime() {
        given()
                .when()
                .get("/posts")
                .then()
                .time(org.hamcrest.Matchers.lessThan(2000L)); // < 2 секунды
    }
}