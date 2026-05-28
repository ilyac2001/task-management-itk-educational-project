#  Api Gateway

## Описание
Сервис единой точки входа: переадресация на другие сервисы и проверка токена

## API
Базовый URL: `http://localhost:8082`  
# Другие сервисы не доступны напрямую
- api смотри в README соответствующего сервиса
- замени порт сервиса на порт api-gateway "8082"

1. **Переадресция на auth-service**

2. **Переадресция на task-service**
    - пользователь добавляет Headers (api gateway валидирует токен):
   ```text
        Authorization: Bearer access_token
    ```
   - api-gateway добавляет Headers (после валидации токена):
   ```text
        user-id: uuid
        user-roles: [roles]
   ```
   
## Технологии
- Java 21, Spring Boot 3
- Spring cloud Gateway Server
- Spring Security, Auth2 Resource Server
- Keycloak
- Docker
- Gradle