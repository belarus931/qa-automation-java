package webtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.SearchPage;
import pages.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Расширенные тесты Google поиска")
public class AdvancedSearchTest extends BaseUiTest {

    @Test
    @DisplayName("Поиск и проверка результатов")
    void userCanSearchAndCheckResults() {
        SearchPage searchPage = new SearchPage();

        SearchResultsPage results = searchPage
                .open()
                .searchFor("Selenium testing");

        results.waitForResults();

        // Проверки
        assertTrue(results.hasResults(), "Должны быть результаты");
        assertTrue(results.getResultsCount() > 0, "Количество результатов > 0");

        String firstResult = results.getFirstResultText();
        System.out.println("Первый результат: " + firstResult);

        // Проверяем, что первый результат содержит слово "Selenium"
        assertTrue(firstResult.toLowerCase().contains("selenium"),
                "Результат должен содержать Selenium");
    }

    @Test
    @DisplayName("Проверка всех результатов")
    void allResultsShouldContainSearchTerm() {
        SearchResultsPage results = new SearchPage()
                .open()
                .searchFor("Java programming");

        results.waitForResults();

        // Получаем все тексты результатов
        java.util.List<String> allTexts = results.getAllResultsText();

        System.out.println("Найдено результатов: " + allTexts.size());

        // Проверяем, что хотя бы один результат содержит "Java"
        boolean hasJava = allTexts.stream()
                .anyMatch(text -> text.toLowerCase().contains("java"));

        assertTrue(hasJava, "Хотя бы один результат должен содержать Java");

        // Выводим первые 5 результатов
        allTexts.stream().limit(5).forEach(System.out::println);
    }

    @Test
    @DisplayName("Поиск конкретного результата")
    void userCanFindSpecificResult() {
        SearchResultsPage results = new SearchPage()
                .open()
                .searchFor("Selenium");

        results.waitForResults();

        // Проверяем, что есть результат с текстом "selenide.org"
        boolean hasSeleniumIo = results.hasResultContaining("selenium.io");
        assertTrue(hasSeleniumIo, "Должен быть результат с selenium.dev");
    }
}