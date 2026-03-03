# HTTP Basics — конспект

## 📌 Методы HTTP
- **GET** — получить данные
- **POST** — создать новый ресурс
- **PUT** — полностью обновить ресурс
- **PATCH** — частично обновить ресурс
- **DELETE** — удалить ресурс

## 📌 Статус-коды
- **2xx** — успех (200 OK, 201 Created)
- **3xx** — редирект (301 Moved Permanently)
- **4xx** — ошибка клиента (400 Bad Request, 401 Unauthorized, 404 Not Found)
- **5xx** — ошибка сервера (500 Internal Server Error)

## 📌 Заголовки (Headers)
- `Content-Type: application/json` — тип данных
- `Authorization: Bearer <token>` — аутентификация
- `Accept: application/json` — что клиент готов принять

## 📌 Параметры в URL
- **Path parameters**: `/users/123`
- **Query parameters**: `/users?age=25&city=Moscow`

## 📌 Примеры с curl
```powershell
# GET
curl https://jsonplaceholder.typicode.com/posts/1

# POST
curl -X POST https://jsonplaceholder.typicode.com/posts ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}"

# Проверка 404
curl https://jsonplaceholder.typicode.com/posts/99999