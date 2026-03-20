package webtests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public class BaseUiTest {

    @BeforeAll
    public static void setUp() {
        // Настройки браузера
        Configuration.browser = "chrome";
        Configuration.headless = false;  // true для CI/CD
        Configuration.timeout = 10000;   // таймаут 10 секунд
        Configuration.baseUrl = "https://www.google.com";

        System.out.println("🌐 Настройка браузера: " + Configuration.browser);
    }

    @BeforeEach
    public void logTestStart(TestInfo testInfo) {
        System.out.println("\n▶️ [UI] Запуск теста: " + testInfo.getDisplayName());
    }
}