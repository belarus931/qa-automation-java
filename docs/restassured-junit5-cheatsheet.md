## 📄 **Готовый конспект: RestAssured + JUnit 5**

Создай файл **`docs/restassured-junit5-cheatsheet.md`** и скопируй туда это содержимое:

---

```markdown
# 🚀 RestAssured + JUnit 5 — Полный конспект

**Дата создания:** 2026-03-05  
**Автор:** Дмитрий  
**Текущий этап:** W2 D9 (JUnit 5 Lifecycle + Parameterized Tests)  

---

## 📋 Содержание
1. [RestAssured — основы](#restassured--основы)
2. [RestAssured — проверки (Matchers)](#restassured--проверки-matchers)
3. [RestAssured — извлечение данных](#restassured--извлечение-данных)
4. [RestAssured — спецификации](#restassured--спецификации)
5. [JUnit 5 — жизненный цикл](#junit-5--жизненный-цикл)
6. [JUnit 5 — аннотации](#junit-5--аннотации)
7. [JUnit 5 — параметризованные тесты](#junit-5--параметризованные-тесты)
8. [JUnit 5 — Assertions](#junit-5--assertions)
9. [Hamcrest Matchers](#hamcrest-matchers)
10. [Примеры тестов](#примеры-тестов)
11. [Частые ошибки](#частые-ошибки)
12. [Полезные команды](#полезные-команды)

---

## 🟢 RestAssured — основы

### Зависимость в pom.xml
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.0</version>
    <scope>test</scope>
</dependency>
```

### Базовая структура теста
```java
given()              // ⚙️ Настройка запроса
    .header("Auth", "token")
    .param("id", 1)
    .body("{\"name\":\"John\"}")
.when()              // 🚀 Выполнение
    .get("/users/1")
.then()              // ✅ Проверка
    .statusCode(200)
    .body("name", equalTo("John"));
```

### HTTP методы
```java
.get("/posts")                    // GET
.get("/posts/{id}", 1)            // GET с path param
.post("/posts")                    // POST
.put("/posts/{id}", 1)             // PUT
.patch("/posts/{id}", 1)           // PATCH
.delete("/posts/{id}", 1)          // DELETE
.head("/posts")                    // HEAD
.options("/posts")                 // OPTIONS
```

### Параметры запроса
```java
// Path parameter
given().pathParam("id", 1).get("/posts/{id}")

// Query parameter
given().queryParam("userId", 1).get("/posts")

// Form parameter
given().formParam("name", "John").post("/users")

// Multiple params
given()
    .queryParam("page", 1)
    .queryParam("limit", 10)
    .get("/posts")
```

### Заголовки (Headers)
```java
// Один заголовок
given().header("Authorization", "Bearer token123")

// Несколько заголовков
given().headers(
    "Content-Type", "application/json",
    "Accept", "application/json"
)

// Content-Type сокращение
given().contentType(ContentType.JSON)
```

### Тело запроса
```java
// Строка JSON
.body("{\"title\":\"My Post\",\"body\":\"Content\"}")

// Объект (будет автоматически сериализован)
Post post = new Post("Title", "Content", 1);
.body(post)

// Map
Map<String, Object> map = new HashMap<>();
map.put("title", "My Post");
.body(map)
```

### Логирование
```java
// Логировать всё
given().log().all()

// Только запрос
given().log().uri().log().headers().log().body()

// Только ответ
.then().log().body()

// Если ошибка
.then().log().ifError()

// Глобальное логирование (в BaseApiTest)
RestAssured.filters(
    new RequestLoggingFilter(),
    new ResponseLoggingFilter()
);
```

---

## 🟢 RestAssured — проверки (Matchers)

### Проверка статус-кода
```java
.statusCode(200)
.statusCode(equalTo(200))
.statusCode(anyOf(is(200), is(201)))
.statusCode(allOf(greaterThan(199), lessThan(300)))
```

### Проверка полей ответа
```java
// Простые поля
.body("id", equalTo(1))
.body("name", equalTo("Leanne Graham"))
.body("username", notNullValue())
.body("email", containsString("@"))

// Вложенные поля
.body("address.city", equalTo("Gwenborough"))
.body("company.name", startsWith("Romaguera"))

// Проверка типов
.body("id", isA(Integer.class))
.body("name", isA(String.class))
```

### Проверка массивов
```java
// Размер массива
.body("size()", equalTo(100))
.body("size()", greaterThan(0))

// Проверка каждого элемента
.body("userId", everyItem(equalTo(1)))
.body("id", everyItem(greaterThan(0)))
.body("title", everyItem(not(emptyString())))

// Проверка первого элемента
.body("[0].id", equalTo(1))
.body("find { it.id == 1 }.title", notNullValue())
```

### Проверка наличия ключей
```java
.body("", hasKey("id"))
.body("", hasKey("title"))
.body("", allOf(hasKey("id"), hasKey("title")))
.body("", everyItem(hasKey("userId")))
```

### Комбинированные проверки
```java
// И (все условия)
.body("", allOf(
    hasKey("userId"),
    hasKey("id"),
    hasKey("title")
))

// ИЛИ (хотя бы одно)
.statusCode(anyOf(is(200), is(201)))

// Отрицание
.body("error", not(emptyString()))
```

### Проверка времени ответа
```java
.time(lessThan(2000L))          // меньше 2 секунд
.time(greaterThan(50L))          // больше 50 мс
.time(Matchers.lessThan(2L), TimeUnit.SECONDS)
```

---

## 🟢 RestAssured — извлечение данных

### Извлечение одного значения
```java
// Извлечь поле
int id = given()
    .get("/posts/1")
    .then()
    .extract()
    .path("id");

// Извлечь строку
String title = given()
    .get("/posts/1")
    .then()
    .extract()
    .jsonPath()
    .getString("title");
```

### Извлечение объекта
```java
// В объект
Post post = given()
    .get("/posts/1")
    .then()
    .statusCode(200)
    .extract()
    .as(Post.class);

assertEquals(1, post.getId());
assertEquals("Leanne Graham", post.getTitle());
```

### Извлечение списка объектов
```java
List<Post> posts = given()
    .get("/posts")
    .then()
    .extract()
    .jsonPath()
    .getList("", Post.class);

assertEquals(100, posts.size());
```

### Извлечение Response для сложных проверок
```java
Response response = given()
    .get("/posts/1")
    .then()
    .extract()
    .response();

int statusCode = response.statusCode();
String contentType = response.contentType();
Map<String, String> headers = response.headers();
String body = response.body().asString();
Post post = response.as(Post.class);
```

### JsonPath для сложных запросов
```java
// Groovy GPath — мощные запросы
List<Integer> userIds = given()
    .get("/posts")
    .then()
    .extract()
    .jsonPath()
    .getList("findAll { it.userId == 1 }.id");

// Максимальное значение
int maxId = given()
    .get("/posts")
    .then()
    .extract()
    .jsonPath()
    .getInt("max { it.id }.id");
```

---

## 🟢 RestAssured — спецификации

### RequestSpecification
```java
// Базовая спецификация запроса
RequestSpecification requestSpec = new RequestSpecBuilder()
    .setBaseUri("https://jsonplaceholder.typicode.com")
    .setContentType(ContentType.JSON)
    .addHeader("Accept", "application/json")
    .log(LogDetail.ALL)
    .build();

// Использование
given()
    .spec(requestSpec)
    .get("/posts")
```

### ResponseSpecification
```java
// Базовая спецификация ответа
ResponseSpecification responseSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .expectContentType(ContentType.JSON)
    .expectResponseTime(lessThan(3000L))
    .build();

// Использование
given()
    .get("/posts")
.then()
    .spec(responseSpec)
    .body("size()", greaterThan(0));
```

### Базовая спецификация для всех тестов
```java
// В BaseApiTest
@BeforeAll
public static void setup() {
    RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    RestAssured.filters(
        new RequestLoggingFilter(),
        new ResponseLoggingFilter()
    );
}
```

---

## 🟡 JUnit 5 — жизненный цикл

### Аннотации жизненного цикла

| Аннотация | Когда выполняется | Сколько раз | Для чего |
|-----------|-------------------|-------------|----------|
| `@BeforeAll` | **Перед всеми** тестами | 1 раз (статический) | Глобальная настройка |
| `@BeforeEach` | **Перед каждым** тестом | Перед каждым `@Test` | Подготовка данных |
| `@Test` | Сам тест | По вызову | Основная логика |
| `@AfterEach` | **После каждого** теста | После каждого `@Test` | Очистка |
| `@AfterAll` | **После всех** тестов | 1 раз (статический) | Закрытие ресурсов |

### Пример BaseApiTest
```java
public abstract class BaseApiTest {
    
    @BeforeAll
    public static void globalSetup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
    
    @BeforeEach
    public void beforeEachTest(TestInfo testInfo) {
        System.out.println("▶️ Запуск: " + testInfo.getDisplayName());
    }
    
    @AfterEach
    public void afterEachTest(TestInfo testInfo) {
        System.out.println("✅ Завершён: " + testInfo.getDisplayName());
    }
}
```

---

## 🟡 JUnit 5 — аннотации

### Основные аннотации

| Аннотация | Назначение | Пример |
|-----------|------------|--------|
| `@Test` | Обозначает тестовый метод | `@Test public void testGetPosts()` |
| `@DisplayName` | Человекочитаемое имя | `@DisplayName("GET /posts — все посты")` |
| `@Tag` | Группировка тестов | `@Tag("smoke")`, `@Tag("regression")` |
| `@Disabled` | Отключить тест | `@Disabled("Будет реализовано позже")` |
| `@Nested` | Вложенные тесты | Группировка по эндпоинтам |
| `@TestFactory` | Динамические тесты | Создание тестов на лету |

### Пример использования
```java
@DisplayName("Тесты для постов")
@Tag("api")
public class PostsTest extends BaseApiTest {
    
    @Test
    @DisplayName("✅ GET /posts — успешный запрос")
    @Tag("smoke")
    public void testGetPosts() {
        // тест
    }
    
    @Test
    @DisplayName("⚠️ GET /posts/999 — несуществующий пост")
    @Tag("negative")
    public void testGetNonExistentPost() {
        // тест
    }
    
    @Test
    @Disabled("Будет реализовано в W3")
    public void testNotReady() {
        // этот тест не запустится
    }
    
    @Nested
    @DisplayName("Вложенные тесты для конкретного поста")
    class SinglePostTests {
        
        @Test
        @DisplayName("GET /posts/1")
        public void testGetPost() {
            // тест
        }
    }
}
```

---

## 🟡 JUnit 5 — параметризованные тесты

### Зависимость
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>
```

### @ValueSource — простые значения
```java
@ParameterizedTest
@ValueSource(ints = {1, 2, 3, 4, 5})
@DisplayName("GET /posts/{id} с разными ID")
public void testWithValueSource(int postId) {
    given()
        .pathParam("id", postId)
    .when()
        .get("/posts/{id}")
    .then()
        .statusCode(200);
}

// Другие типы
@ValueSource(strings = {"posts", "comments", "albums"})
@ValueSource(doubles = {1.5, 2.3, 5.7})
@ValueSource(booleans = {true, false})
```

### @CsvSource — табличные данные
```java
@ParameterizedTest
@CsvSource({
    "1, 1, sunt aut",
    "2, 1, qui est esse",
    "3, 1, ea molestias"
})
@DisplayName("GET /posts/{id} с CSV данными")
public void testWithCsvSource(int id, int userId, String titleStart) {
    given()
        .pathParam("id", id)
    .when()
        .get("/posts/{id}")
    .then()
        .body("id", equalTo(id))
        .body("userId", equalTo(userId))
        .body("title", startsWith(titleStart));
}
```

### @CsvFileSource — данные из файла
```java
@ParameterizedTest
@CsvFileSource(resources = "/testdata/posts.csv", numLinesToSkip = 1)
public void testFromCsvFile(int id, int userId, String titlePrefix) {
    given()
        .pathParam("id", id)
    .when()
        .get("/posts/{id}")
    .then()
        .statusCode(200)
        .body("id", equalTo(id))
        .body("userId", equalTo(userId))
        .body("title", startsWith(titlePrefix));
}
```

**Файл `src/test/resources/testdata/posts.csv`:**
```csv
id,userId,titlePrefix
1,1,sunt
2,1,qui
3,1,ea
```

### @MethodSource — данные из метода
```java
@ParameterizedTest
@MethodSource("postIdsProvider")
public void testWithMethodSource(int postId) {
    given().get("/posts/{id}", postId).then().statusCode(200);
}

static Stream<Integer> postIdsProvider() {
    return Stream.of(1, 3, 5, 7, 9);
}
```

### Со сложными объектами
```java
@ParameterizedTest
@MethodSource("postDataProvider")
public void testWithCustomData(PostTestData data) {
    given()
        .queryParam("userId", data.userId)
    .when()
        .get("/posts")
    .then()
        .statusCode(data.expectedStatus);
}

static Stream<PostTestData> postDataProvider() {
    return Stream.of(
        new PostTestData("Пользователь 1", 1, 200),
        new PostTestData("Пользователь 2", 2, 200)
    );
}

// Вспомогательный класс
class PostTestData {
    String description;
    int userId;
    int expectedStatus;
    
    PostTestData(String description, int userId, int expectedStatus) {
        this.description = description;
        this.userId = userId;
        this.expectedStatus = expectedStatus;
    }
}
```

### @EnumSource — перечисления
```java
enum ResourceType {
    POSTS("posts"), COMMENTS("comments"), USERS("users");
    String endpoint; ResourceType(String s) { endpoint = s; }
}

@ParameterizedTest
@EnumSource(ResourceType.class)
public void testAllResources(ResourceType resource) {
    given().get("/" + resource.endpoint).then().statusCode(200);
}

// Только конкретные
@EnumSource(value = ResourceType.class, names = {"POSTS", "COMMENTS"})

// Исключить
@EnumSource(value = ResourceType.class, mode = Mode.EXCLUDE, names = {"USERS"})
```

### Null и Empty
```java
@ParameterizedTest
@NullSource                    // null
@EmptySource                   // пустая строка ""
@NullAndEmptySource            // и null, и ""
@ValueSource(strings = {" ", "\t", "\n"})
public void testEdgeCases(String input) {
    given()
        .queryParam("q", input)
    .when()
        .get("/posts")
    .then()
        .statusCode(200);
}
```

---

## 🟡 JUnit 5 — Assertions

### Базовые assertions
```java
assertEquals(expected, actual);
assertNotEquals(unexpected, actual);
assertTrue(condition);
assertFalse(condition);
assertNull(object);
assertNotNull(object);
assertSame(expected, actual);
assertNotSame(unexpected, actual);
```

### Групповые проверки (assertAll)
```java
Response response = given()
    .get("/posts/1")
    .then()
    .extract()
    .response();

assertAll("Проверка ответа",
    () -> assertEquals(200, response.statusCode()),
    () -> assertNotNull(response.jsonPath().getString("title")),
    () -> assertTrue(response.jsonPath().getInt("id") > 0),
    () -> assertFalse(response.jsonPath().getString("body").isEmpty())
);
```

### Проверка исключений
```java
assertThrows(IllegalArgumentException.class, () -> {
    throw new IllegalArgumentException("Ошибка");
});

assertDoesNotThrow(() -> {
    // код не должен выбрасывать исключение
});
```

---

## 🟢 Hamcrest Matchers

### Импорт
```java
import static org.hamcrest.Matchers.*;
```

### Основные матчеры
| Матчер | Проверяет |
|--------|-----------|
| `equalTo(value)` | равенство |
| `is(value)` | то же, что equalTo |
| `not(value)` | отрицание |
| `nullValue()` | null |
| `notNullValue()` | не null |
| `instanceOf(Class)` | тип объекта |

### Строковые матчеры
| Матчер | Проверяет |
|--------|-----------|
| `containsString("text")` | содержит подстроку |
| `startsWith("prefix")` | начинается с |
| `endsWith("suffix")` | заканчивается на |
| `emptyString()` | пустая строка |
| `emptyOrNullString()` | null или пустая |

### Числовые матчеры
| Матчер | Проверяет |
|--------|-----------|
| `greaterThan(value)` | больше |
| `greaterThanOrEqualTo(value)` | больше или равно |
| `lessThan(value)` | меньше |
| `lessThanOrEqualTo(value)` | меньше или равно |
| `closeTo(value, delta)` | близко к значению |

### Коллекции
| Матчер | Проверяет |
|--------|-----------|
| `hasItem(value)` | содержит элемент |
| `hasItems(values...)` | содержит несколько элементов |
| `hasSize(size)` | размер коллекции |
| `empty()` | пустая коллекция |
| `everyItem(matcher)` | каждый элемент соответствует |

### Мапы
| Матчер | Проверяет |
|--------|-----------|
| `hasKey(key)` | содержит ключ |
| `hasValue(value)` | содержит значение |
| `hasEntry(key, value)` | содержит пару |

### Комбинаторы
```java
// И
allOf(matcher1, matcher2, matcher3)

// ИЛИ
anyOf(matcher1, matcher2)

// Отрицание
not(matcher)

// И то и другое
both(matcher1).and(matcher2)
```

---

## 📝 Примеры тестов

### Простой GET-тест
```java
@Test
@DisplayName("GET /posts/1 — получение поста")
public void testGetPost() {
    given()
        .log().uri()
    .when()
        .get("/posts/1")
    .then()
        .log().body()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("userId", notNullValue())
        .body("title", not(emptyString()));
}
```

### POST-тест с созданием
```java
@Test
@DisplayName("POST /posts — создание нового поста")
public void testCreatePost() {
    String requestBody = """
        {
            "title": "Test Post",
            "body": "This is a test",
            "userId": 1
        }
        """;
    
    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
    .when()
        .post("/posts")
    .then()
        .statusCode(201)
        .body("title", equalTo("Test Post"))
        .body("id", notNullValue());
}
```

### Тест с query параметрами
```java
@Test
@DisplayName("GET /posts?userId=1 — фильтрация по userId")
public void testFilterByUserId() {
    given()
        .queryParam("userId", 1)
    .when()
        .get("/posts")
    .then()
        .statusCode(200)
        .body("userId", everyItem(equalTo(1)));
}
```

### Негативный тест
```java
@Test
@DisplayName("GET /posts/99999 — несуществующий пост")
public void testNonExistentPost() {
    given()
    .when()
        .get("/posts/99999")
    .then()
        .statusCode(404);
}
```

### Параметризованный тест
```java
@ParameterizedTest
@ValueSource(ints = {1, 2, 3, 4, 5})
@DisplayName("GET /posts/{id} — проверка существующих постов")
public void testExistingPosts(int postId) {
    given()
        .pathParam("id", postId)
    .when()
        .get("/posts/{id}")
    .then()
        .statusCode(200)
        .body("id", equalTo(postId));
}
```

### Тест с извлечением данных
```java
@Test
@DisplayName("Извлечение данных в объект")
public void testExtractToObject() {
    Post post = given()
        .get("/posts/1")
    .then()
        .statusCode(200)
        .extract()
        .as(Post.class);
    
    assertAll("Проверка объекта",
        () -> assertEquals(1, post.getId()),
        () -> assertNotNull(post.getTitle()),
        () -> assertNotNull(post.getBody())
    );
}
```

---

## ❌ Частые ошибки

| Ошибка | Причина | Решение |
|--------|---------|---------|
| `No ParameterResolver registered` | Забыл `@ParameterizedTest` | Добавить аннотацию |
| `ArrayIndexOutOfBoundsException` | CSV колонок меньше параметров | Проверить количество колонок |
| `Cannot convert...` | Несоответствие типов | Проверить типы данных |
| `File not found` | Неправильный путь к CSV | Путь должен начинаться с `/` |
| `MethodSource must be static` | Метод-провайдер не статический | Добавить `static` |
| `You must provide at least one argument` | Нет данных в провайдере | Проверить источник |
| `No tests found` | Неправильное имя метода | Проверить `@Test` аннотации |
| `java.net.ConnectException` | Недоступен сервер | Проверить baseURI и сеть |
| `AssertionError` | Несовпадение ожидаемого/фактического | Проверить логи ответа |

---

## 💻 Полезные команды

### Maven
```bash
# Все тесты
mvn clean test

# Один класс
mvn -Dtest=GetPostsTest test

# Один метод
mvn -Dtest=GetPostsTest#testGetAllPosts test

# По тегам
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
mvn test -DexcludedGroups=slow

# Параметризованные тесты
mvn -Dtest=ParameterizedTestsTest test
```

### Git
```bash
# Добавить файл
git add docs/restassured-junit5-cheatsheet.md

# Закоммитить
git commit -m "W2 D9: add RestAssured+JUnit5 cheatsheet"

# Отправить
git push
```

---

## 📚 Полезные ссылки

- [RestAssured Documentation](https://github.com/rest-assured/rest-assured/wiki/Usage)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Hamcrest Matchers](http://hamcrest.org/JavaHamcrest/javadoc/2.2/org/hamcrest/Matchers.html)
- [JSONPlaceholder API](https://jsonplaceholder.typicode.com)

---

**Шпаргалка создана: 2026-03-05**  
**Автор: Дмитрий**  
**Версия: 1.0**
```

---

## ✅ **Как сохранить**

```powershell
# Создай папку docs если ещё нет
mkdir docs

# Создай файл и скопируй туда содержимое выше
New-Item docs\restassured-junit5-cheatsheet.md -ItemType File

# Затем открой в любом редакторе и вставь текст
notepad docs\restassured-junit5-cheatsheet.md
```

---

## 🎯 **Что у тебя теперь есть**

- ✅ **W1 D4:** `docs/notes-http.md`
- ✅ **W2 D8-D9:** `docs/restassured-junit5-cheatsheet.md`

Теперь вся теория **в одном месте**, удобно искать (Ctrl+F), и красиво отображается на GitHub! 📚

Если хочешь, завтра после W2 D10 добавим туда же раздел про **JSON mapping (POJO)**.