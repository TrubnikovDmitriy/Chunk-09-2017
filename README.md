# Chunk-09-2017
## Tower Defense
>***Строй башни, уничтожай врагов, оберегай ядро<br>
>(пока только авторизируйся)***
***
## Members
* Андрей aka Лапища
* Игорь Дружинин
* Дима Трубников

## API
| Действие | Тип запроса, URL | Тело запроса | Тело ответа |
| --- | --- | --- | --- |
| Зарегистрироваться | POST, /sign_up | "username", "email", "password" | "username", "email" |
| Авторизоваться | POST, /sign_in | "login", "password" | "username", "email" |
| Изменить профиль текущего пользователя | POST, /update | “username”, ”email”, “password”, “old_password” | "username", "email" |
| Запросить данные пользователя текущей сессии | GET, /whoisit | | "username", "email" | |
| Разлогиниться | GET, /exit |  |  |
