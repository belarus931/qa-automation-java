package apitests;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import models.Comment;
import models.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для модели Comment")
public class CommentModelTest extends BaseApiTest {

    @Test
    @DisplayName("GET /comments — получаем все комментарии")
    public void testGetAllComments(){
        List<Comment> comments = given()
                .get("/comments")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Comment>>() {});

        comments.forEach(comment -> {
            assertTrue(comment.getEmail().contains("@"),"Проблема с email: " + comment.getEmail());
            assertTrue(comment.getBody() != null && !comment.getBody().isEmpty(), "Проблема с комментарием: " + comment.getBody());
        });
    }

}
