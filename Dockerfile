FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/java-argocd-demo1-1.0.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
