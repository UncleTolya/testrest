<div><b>TestRest</b></div>

A simply RESTful app.
App execute two functions:
  1)Listen "/visit", requred "userId" and "pageId" as JSON, add new visit and return visits statistic by today;
  2)Listen "/visits", requred time-interval withs two parmas: "from" and "to", and then return visits statistic by received dates.

Build instruction:
1) git clone https://github.com/UncleTolya/testrest.git
2) In "application.properties" file specify your database name, database user and time zone;

//or u can create new database and new user, whoose names uses as default in properties 
-create new MySQL8 database "bankspring"                                 
-create new user "springuser" with password "pass" and all permissions.
-default time zone is "Europe/Moscow"

Tests use owned application-test.properties file, and use in-memory database, which fills by sql-files.

note: method counterUniqRegularUsersByDate return quantity of users looked the different 10(number from task) pages.

Language:
Java

Base FrameWorks:
Spring Boot
Spring MVC

Builder
Maven

Code generator
Lombok

Main Database
MySQL

In-Memory database 
H2
