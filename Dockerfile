FROM adoptopenjdk/openjdk11:alpine-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY build.gradle.kts settings.gradle.kts gradlew /workspace/
COPY gradle /workspace/gradle
COPY src /workspace/src
RUN ./gradlew build -x test

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=build /workspace/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","app.jar", "sh"]
