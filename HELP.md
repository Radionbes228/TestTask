# Task Management System
## Описание
Этот сервис предоставляет REST API для управления задачами. API документирован с использованием Swagger и доступен через Swagger UI.

## Запуск проекта локально
### Требования
- Docker
- Docker Compose

### Шаги для запуска
1. Клонируйте репозиторий:

    ```bash
    git clone https://github.com/your-username/your-repo-name.git
    cd your-repo-name
    ```

2. Запустите сервис с помощью Docker Compose:

    ```bash
    docker-compose up --build
    ```

3. После запуска сервис будет доступен по адресу `http://localhost:8080`.
4. Swagger UI будет доступен по адресу `http://localhost:8080/swagger-ui.html`.
## Документация API

Полная документация API доступна через Swagger UI по адресу: `http://localhost:8080/swagger-ui.html`.