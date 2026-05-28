# Task Management Service

## Описание
Сервис управления задачами с REST API, PostgreSQL и Kafka.  
Позволяет создавать задачи, назначать исполнителей, менять статус и получать задачи с пагинацией.

## API
Базовый URL: `http://localhost:8080/api/tasks`

1. **Получение списка задач**
    - URL: `/api/tasks`
    - Метод: `GET`
    - Query-параметры (опционально): `?page=0&size=10&sort=id,desc`
    - Тело запроса: отсутствует

2. **Получение задачи по ID**
    - URL: `/api/tasks/{id}`
    - Метод: `GET`
    - Тело запроса: отсутствует

3. **Создание новой задачи**
    - URL: `/api/tasks`
    - Метод: `POST`
    - Тело запроса:
   ```json
   {
     "title": "Название задачи",
     "description": "Описание задачи"
   }

4. **Назначение задачи**
   - URL: `/api/tasks/{id}/assignee`
   - Метод: `PATCH`
   - Тело запроса:
   ```json
   {
     "assigneeId": 1
   }

5. **Изменение статуса задачи**
   - URL: `/api/tasks/{id}/status`
   - Метод: `PATCH`
   - Тело запроса:
   ```json
   {
     "status": DONE
   }
## KAFKA
Сервис публикует события в Kafka при создании и изменении задач.

| Topic         | Description             |
|---------------|-------------------------|
| task-stream   | Состояние задачи        |
| task-flow     | Событие создания задачи |

**Event**
- TaskStreamEvent
   ```json
   {
      "id": 1,
      "title": "Название задачи",
      "description": "Описание задачи",
      "status": "IN_PROGRESS",
      "assigneeId": 5
   }
  ```
- TaskCreatedFlowEvent
   ```json
   {
    "taskId": 1,
    "createdAt": "2026-05-26T12:00:00Z"
   }
  ```

## Технологии
- Java 21, Spring Boot 3
- Spring Data JPA, Spring Kafka
- PostgreSQL
- Docker
- Gradle