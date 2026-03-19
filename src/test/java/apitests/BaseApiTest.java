package apitests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.*;
import utils.ApiEndpoints;

public class BaseApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ApiEndpoints.BASE_URL;
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }
    @BeforeEach
    public void beforeEachTest(TestInfo testInfo) {
        System.out.println("\n▶️ Запуск теста: " + testInfo.getDisplayName());
    }

    @AfterEach
    public void afterEachTest(TestInfo testInfo) {
        System.out.println("✅ Тест завершён: " + testInfo.getDisplayName() + "\n");
    }

    @AfterAll
    public static void globalTeardown() {
        System.out.println("🏁 Очистка ресурсов после всех тестов");
    }
}
