FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S acta && adduser -S acta -G acta

COPY --from=build /app/target/*.jar app.jar

USER acta

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]