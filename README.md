# Модуль МРМ (ms-sky-high-rmm) для приложения SkySurfing Sound
В данном проекте разрабатывается модуль МРМ (Матрица Ролевой Модели), предназначенный для управления ролями и разрешениями пользователей. Именно в нем хранятся такие персональные данные пользователей,
как, например, логин и пароль (в захэшированном виде), имя, отчество, возраст. Также в данном модуле представлена функциональность блокировки пользователей.

На данный момент проект находится в стадиии активной разработки.

## Содержание
- [Технологии](#технологии)
- [Зависимости](#зависимости)
- [Начало работы](#начало-работы)

## Технологии
- [Java Spring Boot](https://spring.io/)
- [PostgreSQL](https://www.postgresql.org/)
- [Gradle](https://gradle.org/)
- REST API

## Зависимости
На данном этапе разработки проекта используются следующие зависимости:
- org.springframework.boot:spring-boot-starter-web - это программа запуска Spring Boot, используемая для начальной загрузки веб-приложения Spring Boot. Она автоматически инициализирует проект spring boot и сама импортирует всю связанную конфигурацию.
- org.springframework.boot:spring-boot-starter-validation - стартовый пакет, предоставляемый Spring Boot, который упрощает использование валидации данных в приложениях. Он включает в себя необходимые библиотеки и конфигурации для установки и использования валидации данных с помощью аннотаций в приложениях, основанных на Spring.
- org.json:json:20231013 - библиотека для работы с JSON в языке Java.
- org.springframework.boot:spring-boot-starter-aop - стартовый пакет предоставляет все необходимые зависимости для использования аспектно-ориентированного программирования (AOP) в приложениях, созданных с использованием Spring Boot. AOP позволяет выносить общие функциональные аспекты, такие как логирование, безопасность и транзакции, из основного кода приложения, что способствует легкости и удобству управления данными аспектами.
- org.projectlombok:lombok - библиотека, которая позволяет упростить разработку на языке Java путем автоматической генерации кода. Она предоставляет аннотации для устранения необходимости в рутинном и рутинном коде, таком как методы доступа, hashCode и equals. Lombok помогает сделать код более чистым и уменьшить объем написанного кода.
- org.postgresql:postgresql - зависимость для подключения к базе данных PostgreSQL из приложений Java. Она предоставляет необходимые инструменты для работы с базой данных PostgreSQL, включая драйвер JDBC, который позволяет Java-приложениям взаимодействовать с PostgreSQL.
- org.springframework.boot:spring-boot-starter-test - стартовый пакет предоставляет необходимые зависимости для тестирования приложений, созданных с использованием Spring Boot. Он включает библиотеки для модульного тестирования, интеграционного тестирования и тестирования веб-приложений, упрощая процесс написания и запуска тестов в приложениях на основе Spring Boot.

## Начало работы
Для установки проекта клонируйте репозиторий себе на локальную машину, предварительно скачав на неё сборщик Gradle.
Рекомендуется открывать проект с помощью среды разработки IntelliJ IDEA.

Для запуска проекта перейдите непосредственно в директорию проекта:
```sh
$ cd "YourDisk:\YourProjectsFolder\ms-sky-high-rmm"
```

Если вы используете Windows, то введите команду для сборки проекта:
```sh
gradlew build
```

Затем, для запуска проекта, введите команду:
```sh
gradlew bootRun
```

Для остановки проекта можно воспользоваться сочетанием клавич Ctrl + C.