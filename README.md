# Task Management System
## Описание
Этот сервис предоставляет REST API для управления задачами. API документирован с использованием Swagger и доступен через Swagger UI.

## Запуск проекта локально
### Требования
- Docker
- Docker Compose

### Шаги для запуска
1. Перейдите в подготовленную папку для проекта:
    ```bash
    cd your-repo-name
    ```
2. Склонируйте проект:
    ```bash
    git clone https://github.com/Radionbes228/TestTask.git
    ```
4. Запустите очистку и сборку проекта с помощью maven:
   ```bash
   mvn clean package
   ```
5. Запустите сервис с помощью Docker Compose:
    ```bash
    docker-compose up --build
    ```

3. После запуска сервис будет доступен по адресу `http://localhost:8082`.
4. Swagger UI будет доступен по адресу `http://localhost:8082/swagger-ui.html`.
## Документация API

Полная документация API доступна через Swagger UI по адресу: `http://localhost:8082/swagger-ui.html` или `http://localhost:8082/v3/api-docs`.
