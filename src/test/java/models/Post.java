package models;

public class Post {
    // 1. ПОЛЯ — точно как в JSON
    private int userId;
    private int id;
    private String title;
    private String body;

    // 2. КОНСТРУКТОР ПО УМОЛЧАНИЮ (обязательно!)
    // Джексону нужен пустой конструктор, чтобы создать объект, а потом заполнить поля
    public Post() {
    }

    // 3. КОНСТРУКТОР ДЛЯ СОЗДАНИЯ (без id, потому что его генерирует сервер)
    public Post(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    // 4. ГЕТТЕРЫ И СЕТТЕРЫ (обязательно!)
    // Джексон через них заполняет поля и читает их

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}