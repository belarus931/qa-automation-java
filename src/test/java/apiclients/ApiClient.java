package apiclients;

import io.restassured.response.Response;
import models.ApiResponse;
import models.Comment;
import models.Post;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiClient {

    // ============= COMMENTS =============

    private <T> ApiResponse<List<T>> getAll(String endpoint, Class<T> clazz) {
        Response response = given().get(endpoint);
        List<T> data = response.jsonPath().getList("", clazz);
        return new ApiResponse<>(response.statusCode(), data, response.time());
    }

    public ApiResponse<List<Comment>> getAllComments() {
        return getAll("/comments", Comment.class);
    }

    public ApiResponse<Comment> getCommentById(int id) {
        Response response = given()
                .pathParam("id", id)
                .get("/comments/{id}");

        // Для одного объекта используем .as() вместо jsonPath()
        Comment comment = response.as(Comment.class);

        return new ApiResponse<>(
                response.statusCode(),
                comment,
                response.time()
        );
    }

    // ============= POSTS =============

    public ApiResponse<List<Post>> getAllPosts() {
        return getAll("/posts", Post.class);

    }

    public ApiResponse<Post> getPostById(int id) {
        Response response = given()
                .pathParam("id", id)
                .get("/posts/{id}");

        Post post = response.as(Post.class);

        return new ApiResponse<>(
                response.statusCode(),
                post,
                response.time()
        );
    }

    // Метод для создания поста (POST)
    public ApiResponse<Post> createPost(Post newPost) {
        Response response = given()
                .contentType("application/json")
                .body(newPost)
                .post("/posts");

        Post createdPost = response.as(Post.class);

        return new ApiResponse<>(
                response.statusCode(),
                createdPost,
                response.time()
        );
    }

    // Метод для обновления поста (PUT)
    public ApiResponse<Post> updatePost(int id, Post updatedPost) {
        Response response = given()
                .contentType("application/json")
                .pathParam("id", id)
                .body(updatedPost)
                .put("/posts/{id}");

        Post post = response.as(Post.class);

        return new ApiResponse<>(
                response.statusCode(),
                post,
                response.time()
        );
    }

    // Метод для удаления поста (DELETE)
    public ApiResponse<Void> deletePost(int id) {
        Response response = given()
                .pathParam("id", id)
                .delete("/posts/{id}");

        return new ApiResponse<>(
                response.statusCode(),
                null,
                response.time()
        );
    }

    // ============= МЕТОДЫ ДЛЯ USERS =============
    // TODO: добавить методы для users
}