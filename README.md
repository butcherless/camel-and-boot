# Apache Camel and Spring Boot

## Status

![Github CI](https://github.com/butcherless/camel-and-boot/workflows/CI/badge.svg)

## Proof of concept and research with colleagues and friends.

Goals:

- Learn Camel
- Integrate with Spring Cloud Gateway
- Integrate with Kafka

Tech stack:

- Apache Camel 4.x
- Spring Boot 3.2.x
- Java 21

## New project basic structure

Check _Java_ and _Maven_ versions:

```bash
  ./mvnw -v
```

## Useful commands

Create kafka topic via IntelliJ Kafka plugin:

- new connection on port: `29092`
- new topic: `devices`
- start consumer

Run the microservice:

```bash
  java -jar target/camel-app-0.0.1-SNAPSHOT.jar
```

Dependency list

```bash
    ./mvnw dependency:list -DincludeGroupIds=org.springframework
```

Dependency updates

```bash
  ./mvnw versions:display-dependency-updates
```

## HTTP client commands [`httpie`]

[http client tests](./endpoints.http)

```bash
http -v POST ":8080/camel/api/positions" \
Content-Type:application/json \
< src/test/resources/position-one.json
```

## Links

- https://medium.com/@paulkunda/setting-up-h2-for-testing-in-spring-boot-245d3a98e405
- [Guide to Setting Up Apache Kafka Using Docker](https://www.baeldung.com/ops/kafka-docker-setup)
