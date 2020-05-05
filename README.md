# RestaurantVoter
### App for create and display ratings of cafes and restaurants
Приложение позволяет увидеть список ресторанов с меню и количеством голосов, зарегистрироваться и войти под своим логином, администратор создаёт/удаляет рестораны, пользователи голосуют за понравившийся ресторан.

## Task:
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

-----------------------------

#### Используемые технологии:
Spring Boot, REST, H2 db, JPA(Hibernate), Spring Security, JUnit5, ehcache(кешируются данные пользователей), slf4j

-----------------------------

#### Команды для CURL:
- ***Получение списка ресторанов*** ==> `curl localhost:8080/restaurants -u admin@gmail.com:admin`
- ***Получение списка меню в ресторане*** ==> `curl localhost:8080/restaurants/meals/1 -u admin@gmail.com:admin`
- ***Созлание ресторана с 1 блюдом*** ==> `curl -X POST localhost:8080/admin/restaurants -H "Content-type:application/json" -d {\"name\":\"ABC\",\"meals\":[{\"name\":\"Burger\",\"price\":\"8.80\"}]} -u admin@gmail.com:admin`
- ***Голосование за ресторан (если отправляете команду после 11:00(согласно ТЗ) или не за ресторан, созданный сегодня - будет сообщение о недоступности голосования. Для изменения органичения по времени - изменить переменную ```limitHourForVote``` в классе ```utils/TimeUtil.java``` на то время, которое вам нравится)*** ==> `curl localhost:8080/restaurants/vote/11 -u user@gmail.com:123`
- ***Изменение данных ресторана*** ==> `curl -X PUT localhost:8080/admin/restaurants/10 -H "Content-type:application/json" -d {\"name\":\"Zabegalovka\"} -u admin@gmail.com:admin`
- ***Удаление ресторана*** ==> `curl -X DELETE localhost:8080/admin/restaurants/10 -u admin@gmail.com:admin`
- ***Изменить данные пользователя(из профиля этого пользователя)*** ==> `curl -X PUT localhost:8080/users/profile -H "Content-type:application/json" -d {\"email\":\"newmail@gmail.com\",\"password\":\"09876\"} -u user@gmail.com:123`
- ***Получение данных пользователей*** ==> `curl localhost:8080/admin/users -u admin@gmail.com:admin`
- ***Добавить пользователя*** ==> `curl -X POST localhost:8080/users/profile/register -H "Content-type:application/json" -d {\"email\":\"new@gmail.com\",\"password\":\"22222\"}`
- ***Удаление пользователя*** ==> `curl -X DELETE localhost:8080/admin/users/100002 -u admin@gmail.com:admin`
