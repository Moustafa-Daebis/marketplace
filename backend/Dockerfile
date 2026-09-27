FROM maven:3.9.16-eclipse-temurin-26-alpine AS build
WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q dependency:go-offline

COPY src ./src
RUN ./mvnw -q -DskipTests clean package \
    && JAR_FILE=$(find target -maxdepth 1 -type f -name "*.jar" ! -name "*-plain.jar" | head -n 1) \
    && cp "$JAR_FILE" /tmp/app.jar

FROM eclipse-temurin:26-jre
WORKDIR /app

ENV CURR_ENV=prod
ENV PORT=8080

COPY --from=build /tmp/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --server.port=${PORT:-8080}"]
