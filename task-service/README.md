# Task Management Service

## Описание
Сервис управления задачами с REST API, PostgreSQL и Kafka.  
Позволяет создавать задачи, назначать исполнителей, менять статус и получать задачи с пагинацией.

## Технологии
- Java 21, Spring Boot
- Spring Data, Spring Kafka
- PostgreSQL
- Docker (для БД и Kafka)
- Gradle

## Настройка проекта

1. Переименовать `.env.example` в `.env` и заполнить переменные POSTGRES:

```bash
# Создать .env из примера
cp .env.example .env

# Установить нужные значения
sed -i '' 's/^POSTGRES_USERNAME=.*/POSTGRES_USERNAME=itk/' .env
sed -i '' 's/^POSTGRES_PASSWORD=.*/POSTGRES_PASSWORD=admin/' .env
```
2. Запустить Docker Compose для PostgreSQL и Kafka:
```bash
docker-compose up -d
```
2. Собрать и запустить приложение:
```bash
./gradlew build
./gradlew bootRun
```

## API

Базовый URL: `http://localhost:8080/api/tasks`

1. **Получение списка задач**
    - URL: `/api/tasks`
    - Метод: `GET`
    - Query-параметры (опционально): `?page=0&size=10&sort=id,desc`
    - Тело запроса: отсутствует
    - Описание: Получение списка задач с пагинацией

2. **Получение задачи по ID**
    - URL: `/api/tasks/{id}`
    - Метод: `GET`
    - Тело запроса: отсутствует
    - Описание: Получение одной задачи по её ID

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
## Примечания
 -	Файл .env не хранится в репозитории. Использовать .env.example как шаблон.
 - Собранные .jar файлы не включаются в репозиторий, их генерирует Gradle при сборке.