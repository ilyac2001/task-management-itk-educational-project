# Task Management

## Описание
Учебный проект управления задачами в itk

## Микросервисы
- task-service
- auth-service

## Настройка проекта

1. Переименовать `.env.example` в `.env` и заполнить переменные POSTGRES:

```bash
# Создать .env из примера
cp .env.example .env

# Установить нужные значения для БД
sed -i '' 's/^POSTGRES_USERNAME=.*/POSTGRES_USERNAME=admin/' .env
sed -i '' 's/^POSTGRES_PASSWORD=.*/POSTGRES_PASSWORD=admin/' .env

# Установить нужные значения для Security
sed -i '' 's/^POSTGRES_USERNAME=.*/OAUTH_CLIENT_ID=client/' .env
sed -i '' 's/^POSTGRES_PASSWORD=.*/OAUTH_CLIENT_SECRET=secret/' .env
```
2. Собрать и запустить микросервисы:
```bash
./gradlew build
./gradlew bootRun
```
3. Запустить Docker Compose для PostgreSQL и Kafka и микросервисов:
```bash
docker-compose up -d
```

## Примечания
-	Файл .env не хранится в репозитории. Использовать .env.example как шаблон.
- Собранные .jar файлы не включаются в репозиторий, их генерирует Gradle при сборке.