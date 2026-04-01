package webtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.CheckboxesPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты чекбоксов")
public class CheckboxesTest extends BaseUiTest {

    @Test
    @Tag("regression")
    @DisplayName("Переключение первого чекбокса")
    void toggleFirstCheckbox() {
        CheckboxesPage page = new CheckboxesPage().open();

        // Изначальное состояние
        boolean initial = page.isFirstChecked();
        page.toggleFirst();
        assertNotEquals(initial, page.isFirstChecked(), "Состояние должно измениться");
    }

    @Test
    @Tag("regression")
    @DisplayName("Переключение последнего чекбокса")
    void toggleLastCheckbox() {
        CheckboxesPage page = new CheckboxesPage().open();
        boolean initial = page.isLastChecked();
        page.toggleLast();
        assertNotEquals(initial, page.isLastChecked(), "Состояние должно измениться");
    }
}