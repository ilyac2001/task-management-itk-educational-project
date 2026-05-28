#  Auth Service

## Описание
Сервис авторизации и аунтефикации

## API
Базовый URL: `http://localhost:8081`

1. **Регистрация пользвателя**
    - URL: `/api/auth/register`
    - Метод: `POST`
    - Тело запроса:
   ```json
   {
    "name": "ilya",
    "email": "ilya@dom.ru",
    "password": "1111"
   }
   ```
   - Ответ: 201 Created - NO_CONTENT

2. **Получение токена авторизации**
    - URL: `/oauth/token`
    - Метод: `POST`
    - Тело запроса (form-data / x-www-form-urlencoded):
    ```text
        username=ilya@dom.ru
        password=1111
    ```
   - Ответ
   ```json
    {
        "access_token": "xxx",
        "refresh_token": "xxx",
        "token_type": "bearer",
        "expires_in": 3600
    }
    ```
## KAFKA
Сервис публикует события в Kafka при успешной регистрации пользователя.

| Topic         | Description                      |
|---------------|----------------------------------|
| user-stream   | Состояние пользователя           |
| user-flow     | Событие регистрации пользователя |

**Event**
- UserStreamEvent
  - все поля пользователя кроме логина (e-mail) и пароля
     ```json
    {
      "id": "1807c4be-b85a-4899-9a3d-5430eb4d36cd",
      "name": "ilya"
    }
    ```
- UserCreatedFlowEvent
   ```json
   {
      "userId": "1807c4be-b85a-4899-9a3d-5430eb4d36cd",
      "createdAt": "2026-05-26T12:00:00Z"
   }
  ```
  
## Технологии
- Java 21, Spring Boot 3
- Spring Security, Auth2 Resource Server
- Keycloak
- Docker
- Gradle