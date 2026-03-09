package apitests;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import models.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты с использованием модели Post")
public class PostModelTest extends BaseApiTest {

    // ==================== 1. ОТПРАВКА ОБЪЕКТА ====================

    @Test
    @DisplayName("POST /posts — создание поста через объект")
    public void testCreatePostWithObject() {
        // Создаём объект
        Post newPost = new Post(1, "Мой заголовок", "Моё содержание");

        // Отправляем объект — Jackson сам превратит его в JSON
        Post createdPost = given()
                .contentType("application/json")
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract()
                .as(Post.class);  // JSON → объект

        // Проверяем через JUnit assertions
        assertAll("Проверка созданного поста",
                () -> assertNotNull(createdPost.getId(), "ID должен быть сгенерирован"),
                () -> assertEquals(newPost.getTitle(), createdPost.getTitle()),
                () -> assertEquals(newPost.getBody(), createdPost.getBody()),
                () -> assertEquals(newPost.getUserId(), createdPost.getUserId())
        );

        System.out.println("Созданный пост: " + createdPost);
    }

    // ==================== 2. ПОЛУЧЕНИЕ ОДНОГО ОБЪЕКТА ====================

    @Test
    @DisplayName("GET /posts/1 — получение объекта")
    public void testGetPostAsObject() {
        Post post = given()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Post.class);

        assertAll("Проверка полученного поста",
                () -> assertEquals(1, post.getId()),
                () -> assertEquals(1, post.getUserId()),
                () -> assertNotNull(post.getTitle()),
                () -> assertNotNull(post.getBody())
        );

        System.out.println("Полученный пост: " + post);
    }

    // ==================== 3. ПОЛУЧЕНИЕ СПИСКА ОБЪЕКТОВ ====================

    @Test
    @DisplayName("GET /posts — получение списка объектов")
    public void testGetPostsAsList() {
        // Способ 1: через TypeRef (рекомендуется)
        List<Post> posts = given()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Post>>() {});

        // Способ 2: через jsonPath (тоже работает)
        // List<Post> posts = given().get("/posts").jsonPath().getList("", Post.class);

        assertAll("Проверка списка",
                () -> assertEquals(100, posts.size(), "Должно быть 100 постов"),
                () -> assertNotNull(posts.get(0).getTitle()),
                () -> assertTrue(posts.get(0).getId() > 0)
        );

        System.out.println("Первый пост в списке: " + posts.get(0));
    }

    // ==================== 4. ОБНОВЛЕНИЕ ЧЕРЕЗ ОБЪЕКТ ====================

    @Test
    @DisplayName("PUT /posts/1 — обновление через объект")
    public void testUpdatePostWithObject() {
        // Создаём объект с обновлёнными данными
        Post updatedPost = new Post( 1, "Обновлённый заголовок", "Обновлённое содержание");

        Post result = given()
                .contentType("application/json")
                .body(updatedPost)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Post.class);

        assertEquals(updatedPost.getTitle(), result.getTitle());
        assertEquals(updatedPost.getBody(), result.getBody());
    }

    // ==================== 5. РАБОТА С RESPONSE ====================

    @Test
    @DisplayName("Извлечение Response и преобразование")
    public void testResponseToObject() {
        Response response = given()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Извлекаем из Response
        Post post = response.as(Post.class);
        String contentType = response.contentType();
        long time = response.time();

        System.out.println("Content-Type: " + contentType);
        System.out.println("Время ответа: " + time + " ms");
        System.out.println("Пост: " + post);
    }

    // ==================== 6. ПОИСК ПО УСЛОВИЮ ====================

    @Test
    @DisplayName("Поиск постов пользователя через объекты")
    public void testFindPostsByUserId() {
        List<Post> allPosts = given()
                .get("/posts")
                .then()
                .extract()
                .jsonPath()
                .getList("", Post.class);

        // Используем Stream API для фильтрации
        List<Post> userPosts = allPosts.stream()
                .filter(post -> post.getUserId() == 1)
                .toList();

        assertEquals(10, userPosts.size(), "У пользователя 1 должно быть 10 постов");

        // Проверим, что все найденные посты действительно принадлежат userId=1
        userPosts.forEach(post ->
                assertEquals(1, post.getUserId())
        );
    }
}