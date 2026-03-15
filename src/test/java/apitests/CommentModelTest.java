package apitests;

import apiclients.ApiClient;
import models.ApiResponse;
import models.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для модели Comment")
public class CommentModelTest extends BaseApiTest {

    ApiClient apiClient = new ApiClient();

    @Test
    @DisplayName("GET /comments — получаем все комментарии")
    public void testGetAllComments() {
        // 1. Один вызов — всё сразу
        ApiResponse<List<Comment>> response = apiClient.getAllComments();

        // 2. Проверяем статус
        assertEquals(200, response.getStatusCode());
        assertTrue(response.isSuccessful());

        // 3. Проверяем время (опционально)
        assertTrue(response.getResponseTime() < 3000,
                "Слишком медленно: " + response.getResponseTime() + "ms");

        // 4. Получаем данные и проверяем
        List<Comment> comments = response.getData();

        comments.forEach(comment -> {
            assertTrue(comment.getEmail().contains("@"),
                    "Проблема с email: " + comment.getEmail());
            assertTrue(comment.getBody() != null && !comment.getBody().isEmpty(),
                    "Проблема с комментарием: " + comment.getBody());
        });
    }

    @Test
    @DisplayName("GET /comments/1 — получаем один комментарий")
    public void testGetCommentById() {
        ApiResponse<Comment> response = apiClient.getCommentById(1);

        assertEquals(200, response.getStatusCode());

        Comment comment = response.getData();
        assertEquals(1, comment.getId());
        assertTrue(comment.getEmail().contains("@"));
    }

    @Test
    @DisplayName("GET /comments/999 — несуществующий комментарий")
    public void testGetNonExistentComment() {
        ApiResponse<Comment> response = apiClient.getCommentById(999);

        assertEquals(404, response.getStatusCode());
        assertFalse(response.isSuccessful());
    }


}