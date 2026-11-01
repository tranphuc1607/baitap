# Sử dụng OpenJDK 17 làm môi trường chạy
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy file jar đã build vào container
COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]