# Java Automation 8-Week Challenge

## О проекте

Учебный проект для изучения Java Automation — от основ языка до API и UI тестирования.

## Стек

- Java 17
- Maven
- JUnit 5
- RestAssured 5.3
- Selenide 7.14
- Hamcrest
- Jackson Databind
- JSON Schema Validator

## Структура проекта

```
src/
├── main/java/javabasics/         # Основы Java
│   ├── AgeCalculator             # Вычисление возраста
│   ├── DuplicateFinder           # Поиск дубликатов в коллекции
│   ├── EvenOdd                   # Чётное / нечётное
│   ├── Factorial                 # Факториал числа
│   ├── Grade                     # Оценка по баллам
│   ├── Greeting                  # Приветствие
│   ├── ListProcessor             # Фильтрация списка по длине имени
│   ├── LoginChecker              # Проверка доступа (email + пароль)
│   ├── MultiplicationTable       # Таблица умножения
│   ├── SimpleCalculator          # Калькулятор (+, −, ×, ÷)
│   ├── SumCalculator             # Сумма 1..N
│   ├── UniqueWords               # Уникальные слова
│   ├── WeekDay                   # День недели по номеру
│   └── WordCounter               # Подсчёт слов
│
└── test/java/
    ├── apiclients/
    │   └── ApiClient             # RestAssured-обёртка (CRUD posts, comments, users)
    ├── apitests/
    │   ├── BaseApiTest           # Базовый класс: baseUrl, логирование
    │   ├── FirstApiTest          # GET /posts/1
    │   ├── GetPostsTest          # GET /posts (список, структура, уникальность id)
    │   ├── CreatePostTest        # POST /posts
    │   ├── UpdatePostTest        # PUT /posts/1
    │   ├── DeletePostTest        # DELETE /posts/1
    │   ├── QueryParamsTest       # GET /posts?userId=1
    │   ├── GetNonExistentPostTest# GET /posts/99999 → 404
    │   ├── NegativeTests         # Негативные сценарии (404, bad body, wrong method)
    │   ├── CommentModelTest      # GET /comments с моделью Comment
    │   ├── PostModelTest         # CRUD через Post модель + ApiClient
    │   ├── JsonDataTest          # Загрузка тестовых данных из JSON
    │   ├── JUnit5FeaturesTest    # @DisplayName, @Disabled, параметризация, время ответа
    │   └── ParameterizedTestsTest# Параметризованные тесты (ids, ресурсы, CSV)
    ├── webtests/
    │   ├── BaseUiTest            # Настройка Selenide (browser, timeout, baseUrl)
    │   ├── FirstSelenideTest     # Первый UI-тест: поиск и проверка результатов
    │   ├── GoogleSearchTest      # Поиск через GooglePage + проверка результатов
    │   ├── GoogleSearchAdvancedTest # ElementsCollection: фильтрация, подсчёт, тексты
    │   └── LoginTest             # Логин на the-internet.herokuapp.com
    ├── pages/
    │   ├── GooglePage            # Page Object: страница поиска (DuckDuckGo)
    │   ├── GoogleResultsPage     # Page Object: результаты поиска (ElementsCollection)
    │   └── LoginPage             # Page Object: страница логина (Heroku)
    ├── models/
    │   ├── ApiResponse           # Обёртка ответа: статус, body, время
    │   ├── Post                  # DTO для posts API
    │   ├── Comment               # DTO для comments API
    │   └── User                  # DTO для users API
    ├── utils/
    │   ├── ApiEndpoints          # Константы URL и путей
    │   └── JsonReader            # Чтение JSON из файлов (Jackson)
    └── testdata/
        └── TestDataFactory       # Фабрика тестовых данных (Post, Comment, User)
```

## Реализованные тесты

### API тесты (RestAssured + jsonplaceholder.typicode.com)

- CRUD операции: GET, POST, PUT, DELETE для /posts
- Фильтрация по query-параметрам (`?userId=1`)
- Негативные сценарии (404, невалидный body, неправильный метод)
- Работа с моделями (Post, Comment, User) через ApiClient
- Загрузка тестовых данных из JSON-файлов
- Параметризованные тесты (ValueSource, CsvSource, MethodSource)
- Проверка времени ответа и JSON Schema

### UI тесты (Selenide + DuckDuckGo)

- Поиск и проверка результатов (SelenideElement)
- Работа с коллекциями элементов (ElementsCollection)
- Фильтрация результатов по тексту
- Page Object паттерн (GooglePage → GoogleResultsPage)
- Логин-форма на the-internet.herokuapp.com

## Запуск

```bash
# Все тесты
mvn clean test

# Только API тесты
mvn test -Dtest="apitests.*"

# Только UI тесты
mvn test -Dtest="webtests.*"
```
