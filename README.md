# Apache Camel and Spring Boot

## Status

![Github CI](https://github.com/butcherless/camel-and-boot/workflows/CI/badge.svg)

## Proof of concept and research with colleagues and friends.

Goals:

- Learn Camel
- Integrate with Spring Cloud Gateway
- Integrate with Kafka

Tech stack:

- Apache Camel 4.3.x
- Spring Boot 3.2.x
- Java 21

## New project basic structure

Check _Java_ and _Maven_ versions:

```bash
  ./mvnw -v
```

## Useful commands

Run the microservice:

```bash
  java -jar target/microservice-two-0.0.1-SNAPSHOT.jar
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

> **[TODO]:** complete commands


| Command                | Description |
|------------------------|-------------|
| `http -v ':8083/todo'` | TODO        |

```bash
http -v POST ":8080/camel/api/positions" \
Content-Type:application/json \
< src/test/resources/position-one.json
```
