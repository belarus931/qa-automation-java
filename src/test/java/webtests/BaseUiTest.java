package webtests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseUiTest {

    @BeforeAll
    public static void setUp() throws IOException {
        // Загружаем настройки из файла
        Properties props = new Properties();
        try (InputStream input = BaseUiTest.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                System.err.println("Файл application.properties не найден, использую настройки по умолчанию");
            } else {
                props.load(input);
            }
        }

        // Устанавливаем значения в Selenide Configuration
        String baseUrl = props.getProperty("base.url", "https://duckduckgo.com");
        String browser = props.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(props.getProperty("headless", "false"));
        long timeout = Long.parseLong(props.getProperty("timeout", "10000"));

        Configuration.baseUrl = baseUrl;
        Configuration.browser = browser;
        Configuration.headless = headless;
        Configuration.timeout = timeout;

        System.out.println("🌐 Настройка браузера: " + Configuration.browser);
        System.out.println("🌐 Base URL: " + Configuration.baseUrl);
        System.out.println("🌐 Headless: " + Configuration.headless);
        System.out.println("⏱ Таймаут: " + Configuration.timeout);
    }

    @BeforeEach
    public void logTestStart(TestInfo testInfo) {
        System.out.println("\n▶️ [UI] Запуск теста: " + testInfo.getDisplayName());
    }
}