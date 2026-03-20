package apitests;

import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JsonReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты с данными из JSON-файлов")
public class JsonDataTest extends BaseApiTest {

    @Test
    @DisplayName("Чтение пользователя из JSON-файла")
    public void testReadUserFromJson() {
        User user = JsonReader.readFromResources("testdata/user.json", User.class);

        assertAll("Проверка данных из JSON",
                () -> assertEquals(999, user.getId()),
                () -> assertEquals("Test User", user.getName()),
                () -> assertEquals("test@example.com", user.getEmail())
        );
    }

    @Test
    @DisplayName("Чтение списка пользователей из JSON-файла")
    public void testReadUsersListFromJson() {
        List<User> users = JsonReader.readListFromFile(
                "src/test/resources/testdata/users.json",
                User.class
        );

        assertEquals(2, users.size());
        assertEquals("Test User 1", users.get(0).getName());
        assertEquals("Test User 2", users.get(1).getName());
    }
}