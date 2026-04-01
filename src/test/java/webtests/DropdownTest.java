package webtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.DropdownPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты выпадающего списка")
public class DropdownTest extends BaseUiTest {

    @Test
    @Tag("regression")
    @DisplayName("Выбор Option 1")
    void selectOption1() {
        DropdownPage page = new DropdownPage().open();
        page.selectOptionByText("Option 1");
        assertEquals("Option 1", page.getSelectedOptionText());
    }

    @Test
    @Tag("regression")
    @DisplayName("Выбор Option 2")
    void selectOption2() {
        DropdownPage page = new DropdownPage().open();
        page.selectOptionByValue("2");
        assertEquals("Option 2", page.getSelectedOptionText());
    }
}