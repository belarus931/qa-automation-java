package apiclients;

import io.restassured.response.Response;
import models.ApiResponse;
import models.Comment;
import models.Post;
import models.User;
import utils.ApiEndpoints;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiClient {

    // ============= COMMENTS =============

    private <T> ApiResponse<List<T>> getAll(String endpoint, Class<T> clazz) {
        Response response = given().get(endpoint);
        List<T> data = response.jsonPath().getList("", clazz);
        return new ApiResponse<>(response.statusCode(), data, response.time());
    }
    private <T> ApiResponse<T> getById(String endpoint, int id, Class<T> clazz) {
        Response response = given()
                .pathParam("id", id)
                .get(endpoint);

        T data = response.as(clazz);
        return new ApiResponse<>(response.statusCode(), data, response.time());
    }

    private <T> ApiResponse<T> create(String endpoint, Object requestBody, Class<T> responseClass) {
        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .post(endpoint);

        T data = response.as(responseClass);
        return new ApiResponse<>(response.statusCode(), data, response.time());
    }
    private <T> ApiResponse<T> update(String endpoint, int id, Object requestBody, Class<T> responseClass) {
        Response response = given()
                .contentType("application/json")
                .pathParam("id", id)
                .body(requestBody)
                .put(endpoint);

        T data = response.as(responseClass);
        return new ApiResponse<>(response.statusCode(), data, response.time());
    }
    private <T> ApiResponse<T> delete (String endpoint, int id) {
        Response response = given()
                .contentType("application/json")
                .pathParam("id", id)
                .delete(endpoint);

        return new ApiResponse<>(response.statusCode(), null, response.time());
    }

    public ApiResponse<List<Comment>> getAllComments() {
        return getAll(ApiEndpoints.COMMENTS, Comment.class);
    }

    public ApiResponse<Comment> getCommentById(int id) {
        return getById(ApiEndpoints.COMMENT_BY_ID, id, Comment.class);
    }

    // ============= POSTS =============

    public ApiResponse<List<Post>> getAllPosts() {
        return getAll(ApiEndpoints.POSTS, Post.class);

    }

    public ApiResponse<Post> getPostById(int id) {
        return getById(ApiEndpoints.POST_BY_ID, id, Post.class);
    }

    // Метод для создания поста (POST)
    public ApiResponse<Post> createPost(Post newPost) {
        return create(ApiEndpoints.POSTS, newPost, Post.class);
    }

    // Метод для обновления поста (PUT)
    public ApiResponse<Post> updatePost(int id, Post updatedPost) {
      return update(ApiEndpoints.POST_BY_ID, id, updatedPost, Post.class);
    }

    // Метод для удаления поста (DELETE)
    public ApiResponse<Post> deletePost(int id) {
        return delete(ApiEndpoints.POST_BY_ID, id);
    }

    // ============= МЕТОДЫ ДЛЯ USERS =============
    public ApiResponse<List<User>> getAllUsers() {
        return getAll(ApiEndpoints.USERS, User.class);
    }

    public ApiResponse<User> getUserById(int id) {
        return getById (ApiEndpoints.USER_BY_ID, id, User.class);
    }

    public ApiResponse<User> updateUserById (int id, User updatedUser){
        return  update(ApiEndpoints.USER_BY_ID,id,updatedUser, User.class);

    }

}