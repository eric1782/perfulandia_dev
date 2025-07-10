#etapa 1 builds
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

#etapa 2 RUN
FROM eclipse-temurin:17-jdk
COPY --from=build /app/target/*jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]