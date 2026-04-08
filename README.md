# Java Automation 8-Week Challenge

## О проекте

Учебный проект для изучения Java Automation — от основ языка до API и UI тестирования.

## Стек

- Java 17
- Maven
- JUnit 5.9.2
- RestAssured 5.5
- Selenide 7.14
- Hamcrest 2.2
- Jackson Databind 2.15.2
- JSON Schema Validator (RestAssured)
- Allure 2.27 (JUnit 5, RestAssured) + AspectJ (для встраивания отчётов)

## Структура проекта

```
src/
├── main/java/
│   ├── Java/AUT/Main.java        # Точка входа для ручного запуска примеров javabasics
│   └── javabasics/               # Основы Java
│       ├── AgeCalculator         # Вычисление возраста
│       ├── DuplicateFinder       # Поиск дубликатов в коллекции
│       ├── EvenOdd               # Чётное / нечётное
│       ├── Factorial             # Факториал числа
│       ├── Grade                 # Оценка по баллам
│       ├── Greeting              # Приветствие
│       ├── ListProcessor         # Фильтрация списка по длине имени
│       ├── LoginChecker          # Проверка доступа (email + пароль)
│       ├── MultiplicationTable   # Таблица умножения
│       ├── SimpleCalculator      # Калькулятор (+, −, ×, ÷)
│       ├── SumCalculator         # Сумма 1..N
│       ├── UniqueWords           # Уникальные слова
│       ├── WeekDay               # День недели по номеру
│       └── WordCounter           # Подсчёт слов
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
    │   ├── BaseUiTest            # Selenide: настройки из application.properties
    │   ├── AdvancedSearchTest    # DuckDuckGo: поиск, ElementsCollection, фильтрация
    │   ├── CheckboxesTest        # Чекбоксы на the-internet.herokuapp.com
    │   ├── DropdownTest          # Выпадающий список на the-internet.herokuapp.com
    │   └── LoginTest             # Логин на the-internet.herokuapp.com
    ├── pages/
    │   ├── SearchPage            # Page Object: поиск (DuckDuckGo)
    │   ├── SearchResultsPage     # Page Object: результаты поиска
    │   ├── LoginPage             # Page Object: страница логина (Heroku)
    │   ├── DropdownPage          # Page Object: /dropdown (Heroku)
    │   └── CheckboxesPage        # Page Object: /checkboxes (Heroku)
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

src/test/resources/
├── application.properties        # browser, headless, timeout, base.url (см. BaseUiTest)
└── testdata/                     # JSON/CSV для API-тестов
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

### UI тесты (Selenide)

- Поиск в DuckDuckGo: результаты, коллекции элементов, фильтрация по тексту (`AdvancedSearchTest`, `SearchPage` / `SearchResultsPage`)
- Сценарии на [the-internet.herokuapp.com](https://the-internet.herokuapp.com): логин, dropdown, чекбоксы
- Часть UI-тестов помечена тегами JUnit 5 (`@Tag("smoke")`, `@Tag("regression")`); в Allure для логина используются `@Epic` / `@Feature` / `@Story`

### Настройка браузера (UI)

Параметры читаются из `src/test/resources/application.properties` (`base.url`, `browser`, `headless`, `timeout`). Страницы Heroku и DuckDuckGo открываются по полным URL в Page Object; при отсутствии файла `BaseUiTest` подставляет значения по умолчанию (в коде — DuckDuckGo как `base.url`).

## Запуск

```bash
# Все тесты
mvn clean test

# Только API тесты
mvn test -Dtest="apitests.*"

# Только UI тесты
mvn test -Dtest="webtests.*"

# По тегу JUnit 5 (пример: только smoke)
mvn test -Dgroups=smoke
```

## Allure-отчёт

После прогона тестов результаты пишутся в `target/allure-results`. Просмотр отчёта:

```bash
mvn allure:serve
```

Либо сгенерировать статический отчёт: `mvn allure:report` (HTML в `target/site/allure-maven-plugin`).
