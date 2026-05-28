# Task Management

## Описание
Учебный проект управления задачами в itk

## Микросервисы
- task-service
- auth-service
- api-gateway

## Настройка проекта

1. Переименовать `.env.example` в `.env` и заполнить переменные
```bash
# Создать .env из примера
cp .env.example .env
# Установить нужные значения переменных
sed -i '' 's/^KEY=.*/KEY=value/' .env
```
- POSTGRES:
  - POSTGRES_USERNAME=admin_login 
  - POSTGRES_PASSWORD=admin_password
- KEYCLOAK:
  - KEYCLOAK_ADMIN=admin_login
  - KEYCLOAK_ADMIN_PASSWORD=admin_password

2. Собрать и запустить микросервисы:
```bash
./gradlew build
./gradlew bootRun
```
3. Запустить Docker Compose для PostgreSQL и Kafka и микросервисов:
```bash
docker-compose up -d
```

4. Настроить KEYCLOAK, а именно KEYCLOAK_CLIENT  

**Перейти http://localhost:9091 в Keycloak Admin Console**  
   Войти под (из .env):
- KEYCLOAK_ADMIN=admin_login
- KEYCLOAK_ADMIN_PASSWORD=admin_password
  
**Создать Client**  
   Realm → Clients → Create client (General settings)
* Client type: OpenID Connect
* Client ID: auth-service (можно любое значние)   
  → Next (Capability config)
* Client authentication - ON 
* Standard flow - ✘
* Direct access grants - ✔  
  → Next (Login settings) → SAVE

**Скопировать Credentials в .env**  
Realm → Clients → auth-service (Client ID) → Credentials  
* Скопировать Client Secret
* Вставить значения в:
  - KEYCLOAK_CLIENT_ID=auth-service (из Client ID)
  - KEYCLOAK_CLIENT_SECRET=secret (из Client Secret)

5. Настроить KEYCLOAK, а именно roles  
**Перейти http://localhost:9091 в Keycloak Admin Console**  
   Войти под (из .env):
- KEYCLOAK_ADMIN=admin_login
- KEYCLOAK_ADMIN_PASSWORD=admin_password  

**Настроить передачу User Realm Role в userinfo**  
Client scopes → Client scope details → role (Protocol=openid-connect) → 
Mappers → Add mapper → By configuration → User Realm Role  
И настроить

| Поле                | Значение           |
|---------------------|--------------------|
| Name                | realm-roles        |
| Mapper Type         | User Realm Role    |
| Multivalued         | ON                 |
| Token Claim Name    | realm_access.roles |
| Add to userinfo     | ON                 |
| Add to access token | ON                 |
→ SAVE

**Создать Realm Role**  
Realm roles → Create role  
Role name = USER (можно любую роль)  
→ SAVE
**Названить пользователям роли (после регистрации)**  
USERS → username@dom.ru → Role Mapping → Assign role → Realm roles  
выбрать роли → Assign  

## Примечания
- Файл .env не хранится в репозитории. Использовать .env.example как шаблон.
- Собранные .jar файлы не включаются в репозиторий, их генерирует Gradle при сборке.
- Keycloak в Admin Console и заполнением KEYCLOAK_CLIENT_ID и KEYCLOAK_CLIENT_SECRET в .env 
требуется заполнить другие переменные из .env и поднять контейнеры 
- api-gateway -- единая точка входа, см. API в README.md внутри сервисов