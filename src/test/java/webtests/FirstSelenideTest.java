package webtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Google поиск")
public class FirstSelenideTest extends BaseUiTest {

    @Test
    @DisplayName("Поиск в Google")
    void userCanSearchInGoogle() {
        open("/");

        $(By.name("q")).setValue("Selenide").pressEnter();

        // Ждём загрузки результатов
        $("h3").shouldBe(visible);

        // Проверяем, что есть хотя бы один результат
        $$("h3").shouldHave(sizeGreaterThan(0));
    }
}