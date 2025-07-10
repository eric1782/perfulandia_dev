# Etapa 1: El "Taller de Construcción" con Gradle
# Usamos una imagen oficial que ya tiene Gradle y Java 17 listos.
FROM gradle:jdk17 AS build
COPY . /app
WORKDIR /app
# Ejecutamos el build de Gradle. "-x test" es el equivalente a "-DskipTests" de Maven.
RUN gradle build --no-daemon -x test

# Etapa 2: El "Producto Final" limpio (igual que antes)
# Empezamos con una imagen limpia y pequeña que solo tiene Java.
FROM eclipse-temurin:17-jdk
# Copiamos el .jar desde la carpeta de Gradle (que es build/libs, no target)
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]