package testdata;

import models.Post;
import models.Comment;
import models.User;

import java.util.concurrent.ThreadLocalRandom;

public class TestDataFactory {

    // ============= POSTS =============

    /**
     * Создаёт пост с дефолтными значениями
     */
    public static Post createDefaultPost() {
        return new Post(1, "Default Title", "Default Body");
    }

    /**
     * Создаёт пост с указанным userId
     */
    public static Post createPostForUser(int userId) {
        return new Post(userId, "Title for user " + userId, "Body for user " + userId);
    }

    /**
     * Создаёт пост со случайными данными
     */
    public static Post createRandomPost() {
        int randomUserId = ThreadLocalRandom.current().nextInt(1, 11);
        String randomTitle = "Random Title " + System.currentTimeMillis();
        String randomBody = "Random Body " + Math.random();
        return new Post(randomUserId, randomTitle, randomBody);
    }

    /**
     * Создаёт пост с кастомными значениями
     */
    public static Post createCustomPost(int userId, String title, String body) {
        return new Post(userId, title, body);
    }

    // ============= COMMENTS =============

    /**
     * Создаёт комментарий с дефолтными значениями
     */
    public static Comment createDefaultComment() {
        return new Comment(1, "Default Name", "default@email.com", "Default Body");
    }

    /**
     * Создаёт комментарий для указанного поста
     */
    public static Comment createCommentForPost(int postId) {
        return new Comment(postId,
                "Commenter " + postId,
                "user" + postId + "@email.com",
                "Comment body for post " + postId);
    }

    /**
     * Создаёт комментарий со случайными данными
     */
    public static Comment createRandomComment() {
        int randomPostId = ThreadLocalRandom.current().nextInt(1, 101);
        String randomName = "User" + System.currentTimeMillis();
        String randomEmail = "user" + System.currentTimeMillis() + "@test.com";
        String randomBody = "Random comment " + Math.random();
        return new Comment(randomPostId, randomName, randomEmail, randomBody);
    }

    /**
     * Создаёт комментарий с некорректным email (для негативных тестов)
     */
    public static Comment createCommentWithInvalidEmail() {
        return new Comment(1, "Invalid Email User", "invalid-email-no-at", "Body with invalid email");
    }

    /**
     * Создаёт комментарий с пустым body (для негативных тестов)
     */
    public static Comment createCommentWithEmptyBody() {
        return new Comment(1, "Empty Body User", "valid@email.com", "");
    }
    // ============= USERS =============
    /**
     * Создаёт дефолтного User
     */

    public static User createDefaultUser (){
        return new User("Default Name","defaultUserName", "default@email.test","+375297777777", "defaultWebSite,com");
    }

}