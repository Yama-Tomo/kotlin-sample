# Get started

create mysql database

```
$ mysql -e 'create database `kotlin_sample`'
```

launch spring boot

```
$ ./gradlew bootRun
```

# Development

Restart spring server automatically when  changed source code using spring-boot-devtools. 

execute following command on other open terminal.

```
./gradlew build -continuous
```
