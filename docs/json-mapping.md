## 📚 **КОНСПЕКТ: JSON Mapping и работа с моделями**

### 📅 **Дата:** 2026-03-09
### 🎯 **Тема:** Преобразование JSON в Java-объекты (POJO) и обратно

---

## 1️⃣ **Что такое POJO и зачем это нужно**

**POJO** = Plain Old Java Object — обычный Java-класс, который отражает структуру JSON.

**Было (работа со строками):**
```java
.body("{\"title\":\"My Post\",\"body\":\"Content\"}")
.body("title", equalTo("My Post"))
```

**Стало (работа с объектами):**
```java
Post post = new Post("My Post", "Content");
.body(post)
assertEquals("My Post", post.getTitle());
```

**Плюсы:**
- ✅ Компилятор проверяет типы
- ✅ IDE подсказывает методы
- ✅ Нет ошибок в кавычках и запятых
- ✅ Код чище и понятнее

---

## 2️⃣ **Правила создания модели (POJO)**

```java
public class Comment {
    // 1. Поля — имена КАК В JSON
    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;

    // 2. Конструктор по умолчанию (ОБЯЗАТЕЛЬНО!)
    public Comment() {}

    // 3. Геттеры и сеттеры для ВСЕХ полей (ОБЯЗАТЕЛЬНО!)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    // ... остальные геттеры/сеттеры
}
```

**Важно:** Jackson (библиотека для JSON) использует **геттеры и сеттеры**, а не сами поля!

---

## 3️⃣ **Jackson — как это работает под капотом**

### **Сериализация (Java → JSON)**
```java
Post post = new Post(1, "Title", "Body");
given().body(post)...
```
1. Jackson смотрит на **геттеры**
2. Вызывает `post.getTitle()`, `post.getBody()`
3. Создаёт JSON: `{"title":"Title","body":"Body"}`

### **Десериализация (JSON → Java)**
```java
Post post = response.as(Post.class);
```
1. Создаёт пустой объект через конструктор по умолчанию
2. Смотрит на JSON: `{"id":1,"title":"sunt..."}`
3. Для каждого ключа ищет **сеттер** с таким же именем
4. Вызывает `setId(1)`, `setTitle("sunt...")`

---

## 4️⃣ **Типичная ошибка и её решение**

**Ошибка:**
```
Unrecognized field "id" (class models.Comment), not marked as ignorable
```

**Причина:** Джексон не нашёл **геттер или сеттер** для поля `id`

**Решение:** Добавить геттер и сеттер:
```java
public int getId() { return id; }
public void setId(int id) { this.id = id; }
```

---

## 5️⃣ **Получение одного объекта**

```java
@Test
public void testGetOneComment() {
    Comment comment = given()
        .get("/comments/1")
    .then()
        .statusCode(200)
        .extract()
        .as(Comment.class);
    
    assertEquals(1, comment.getId());
}
```

---

## 6️⃣ **Получение списка объектов**

### **Способ 1: Через TypeRef (рекомендуется)**
```java
import io.restassured.common.mapper.TypeRef;

List<Comment> comments = given()
    .get("/comments")
    .then()
    .statusCode(200)
    .extract()
    .as(new TypeRef<List<Comment>>() {});
```

### **Способ 2: Через jsonPath().getList()**
```java
List<Comment> comments = given()
    .get("/comments")
    .then()
    .extract()
    .jsonPath()
    .getList("", Comment.class);
```

---

## 7️⃣ **Проверка всех элементов через forEach**

```java
comments.forEach(comment -> {
    assertTrue(comment.getEmail().contains("@"), 
        "Проблема с email: " + comment.getEmail());
    assertTrue(comment.getBody() != null && !comment.getBody().isEmpty(), 
        "Проблема с комментарием: " + comment.getBody());
});
```

**Почему это хорошо:**
- Проверяем **все** элементы
- Видим **конкретный** проблемный элемент
- Защита от NullPointerException

---

## 8️⃣ **Логирование в RestAssured**

### **Где можно логировать:**

| Место | Синтаксис | Когда использовать |
|-------|-----------|-------------------|
| В запросе | `given().log().all()` | Отладка нового теста |
| В ответе | `then().log().all()` | Видеть, что пришло |
| При ошибке | `then().log().ifError()` | Только статус 4xx/5xx |
| При падении | `then().log().ifValidationFails()` | Только если проверки не пройдены |

### **Правильная комбинация для стабильного теста:**
```java
.then()
.log().ifError()
.log().ifValidationFails()
.statusCode(200)
```

---

## 9️⃣ **Вложенные объекты (пример)**

**JSON:**
```json
{
  "id": 1,
  "name": "Leanne Graham",
  "address": {
    "street": "Kulas Light",
    "geo": {
      "lat": "-37.3159",
      "lng": "81.1496"
    }
  }
}
```

**Модели:**
```java
public class Geo {
    private String lat;
    private String lng;
    // геттеры/сеттеры
}

public class Address {
    private String street;
    private Geo geo;
    // геттеры/сеттеры
}

public class User {
    private int id;
    private String name;
    private Address address;
    // геттеры/сеттеры
}
```

**Использование:**
```java
User user = response.as(User.class);
String lat = user.getAddress().getGeo().getLat();
```

---

## 🔟 **Памятка: частые ошибки и решения**

| Ошибка | Причина | Решение |
|--------|---------|---------|
| `Unrecognized field` | Нет геттера/сеттера для поля | Добавить геттер/сеттер |
| `Cannot deserialize` | Нет конструктора по умолчанию | Добавить `public ClassName() {}` |
| `NullPointerException` | Поле null, а мы вызываем метод | Проверить `!= null` |
| `No ParameterResolver` | Забыли `@Test` | Добавить аннотацию |

---

## 🎯 **Главные выводы дня**

1. **Модели = Java-отражение JSON** — имена полей должны совпадать
2. **Геттеры и сеттеры обязательны** — без них Jackson не видит поля
3. **Конструктор по умолчанию обязателен** — для создания пустого объекта
4. **TypeRef для списков** — правильный способ получить `List<T>`
5. **forEach для проверки всех элементов** — современно и чисто
6. **Информативные сообщения в assert** — чтобы сразу понять, что упало
7. **Логирование при ошибках** — `.ifError()` и `.ifValidationFails()`

---