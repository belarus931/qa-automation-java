package utils;

public class ApiEndpoints {

    // Базовый URL (можно вынести из BaseApiTest)
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    // Посты
    public static final String POSTS = "/posts";
    public static final String POST_BY_ID = "/posts/{id}";

    // Комментарии
    public static final String COMMENTS = "/comments";
    public static final String COMMENT_BY_ID = "/comments/{id}";
    public static final String COMMENTS_BY_POST = "/comments?postId={postId}";

    // Пользователи
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/users/{id}";

    // Альбомы (на будущее)
    public static final String ALBUMS = "/albums";
    public static final String PHOTOS = "/photos";
    public static final String TODOS = "/todos";
}