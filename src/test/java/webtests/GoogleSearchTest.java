package webtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.GooglePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Google поиск")
public class GoogleSearchTest extends BaseUiTest {

    @Test
    @DisplayName("Поиск в Google с использованием Page Object")
    void userCanSearchInGoogle() {
        GooglePage googlePage = new GooglePage();

        googlePage
                .open()
                .searchFor("Selenide");

        assertTrue(googlePage.hasResults(), "Должны быть результаты поиска");
        System.out.println("Первый результат: " + googlePage.getFirstResultText());
    }
}