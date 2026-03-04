package apitests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.*;

public class BaseApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
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
