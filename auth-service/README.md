#  Auth Service

## Описание
Сервис авторизации и аунтефикации

## API
Базовый URL: `http://localhost:8080`

1. **Регистрация пользвателя**
    - URL: `/api/auth/register`
    - Метод: `POST`
    - Тело запроса:
   ```json
    {
        "email": "test@some.ru",
        "password": "1234"
    }

2. **Получение токена (OAuth2 Password Grant)**
    - URL: `/oauth/token`
    - Метод: `POST`
    - Headers:
   ```text
        Authorization: Basic base64(clientId:clientSecret)
        Content-Type: application/x-www-form-urlencoded
    ```
    - Тело запроса (form-data / x-www-form-urlencoded):
    ```text
        grant_type=password
        username=test@some
        password=1111
    ```
   - Ответ
   ```json
    {
        "access_token": "xxx",
        "token_type": "bearer",
        "expires_in": 3600,
        "refresh_token": "xxx"
    }
    ```

## Технологии
- Java 17, Spring Boot 2
- Spring Security OAuth2 (legacy Authorization Server), Spring Data JPA
- PostgreSQL
- Docker
- Gradle