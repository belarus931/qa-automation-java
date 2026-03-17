package apitests;

import apiclients.ApiClient;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import models.ApiResponse;
import models.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testdata.TestDataFactory;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты с использованием модели Post")
public class PostModelTest extends BaseApiTest {

    ApiClient apiClient = new ApiClient();
    private Post testPost;

    @BeforeEach
    public void setUp() {
        System.out.println("  📝 [Child] Создаём тестовый пост для теста");
        ApiResponse<Post> response = apiClient.createPost(
                TestDataFactory.createDefaultPost()
        );
        testPost = response.getData();
        assertNotNull(testPost.getId(), "Тестовый пост должен быть создан");
        System.out.println("  ✅ Тестовый пост создан с ID: " + testPost.getId());
    }
    @AfterEach
    public void tearDown() {
        System.out.println("  🧹 [Child] Очистка после теста");
        if (testPost != null && testPost.getId() > 0) {
            // Можно добавить удаление тестового поста, если нужно
            // apiClient.deletePost(testPost.getId());
            System.out.println("  ✅ Тестовый пост с ID " + testPost.getId() + " будет удалён");
        }
    }

    // ==================== 1. ОТПРАВКА ОБЪЕКТА ====================

    @Test
    @DisplayName("POST /posts — создание поста через объект")
    public void testCreatePostWithObject() {
        // Создаём объект
        Post newPost = TestDataFactory.createCustomPost(1, "Мой заголовок", "Моё содержание");
        ApiResponse<Post> newlyCreatedPost = apiClient.createPost(newPost);

        // Проверяем через JUnit assertions
        assertAll("Проверка созданного поста",
                () -> assertNotNull(newlyCreatedPost.getData().getId() > 0, "ID должен быть положительным числом"),
                () -> assertEquals(newPost.getTitle(), newlyCreatedPost.getData().getTitle()),
                () -> assertEquals(newPost.getBody(), newlyCreatedPost.getData().getBody()),
                () -> assertEquals(newPost.getUserId(), newlyCreatedPost.getData().getUserId())
        );

        System.out.println("Созданный пост: " + newlyCreatedPost);
    }
    @Test
    @DisplayName("POST /posts — создание поста со случайными данными")
    public void testCreateRandomPost() {
        Post randomPost = TestDataFactory.createRandomPost();

        ApiResponse<Post> response = apiClient.createPost(randomPost);

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.getData().getId());
    }

    // ==================== 2. ПОЛУЧЕНИЕ ОДНОГО ОБЪЕКТА ====================

    @Test
    @DisplayName("GET /posts/1 — получение объекта")
    public void testGetPostAsObject() {
        ApiResponse<Post> response = apiClient.getPostById(1);

        Post post = response.getData();
        assertEquals(1, post.getId());
        assertEquals(1, post.getUserId());
        assertNotNull(post.getBody());
        assertNotNull(post.getTitle());

        System.out.println("Полученный пост: " + post);
    }

    // ==================== 3. ПОЛУЧЕНИЕ СПИСКА ОБЪЕКТОВ ====================

    @Test
    @DisplayName("GET /posts — получение списка объектов")
    public void testGetPostsAsList() {
        ApiResponse<List<Post>> posts = apiClient.getAllPosts();
        int count = posts.getData().size();
        assertEquals(100, count, "Должно быть 100 постов");
        Post firstPost = posts.getData().getFirst();
        assertNotNull(firstPost);
        assertNotNull(firstPost.getTitle());
        assertEquals(1, firstPost.getId());

        System.out.println("Первый пост в списке: " + firstPost.getBody());
    }

    // ==================== 4. ОБНОВЛЕНИЕ ЧЕРЕЗ ОБЪЕКТ ====================

    @Test
    @DisplayName("PUT /posts/1 — обновление через объект")
    public void testUpdatePostWithObject() {

        Post updatedPost = new Post (1, "Обновлённый заголовок", "Обновлённое содержание");
        ApiResponse <Post> updatedPostResponse = apiClient.updatePost(1, updatedPost);

        assertEquals(updatedPost.getTitle(), updatedPostResponse.getData().getTitle());
        assertEquals(updatedPost.getBody(), updatedPostResponse.getData().getBody());
    }

    // ==================== 5. РАБОТА С RESPONSE ====================

    @Test
    @DisplayName("Извлечение Response и преобразование")
    public void testResponseToObject() {
        ApiResponse<Post> response = apiClient.getPostById(1);
        System.out.println(response);
    }

    // ==================== 6. ПОИСК ПО УСЛОВИЮ ====================

    @Test
    @DisplayName("Поиск постов пользователя через объекты")
    public void testFindPostsByUserId() {
        ApiResponse<List<Post>> posts = apiClient.getAllPosts();

        // Используем Stream API для фильтрации
        List<Post> userPosts = posts.getData().stream()
                .filter(post -> post.getUserId() == 1)
                .toList();

        assertEquals(10, userPosts.size(), "У пользователя 1 должно быть 10 постов");

        // Проверим, что все найденные посты действительно принадлежат userId=1
        userPosts.forEach(post ->
                assertEquals(1, post.getUserId())
        );
    }
}