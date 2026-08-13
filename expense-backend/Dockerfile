<<<<<<< HEAD
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/expense-backend-0.0.1-SNAPSHOT.jar"]
=======
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java","-jar","target/expense-backend-0.0.1-SNAPSHOT.jar"]
>>>>>>> 13eccb466418c974e3157a6956c3e4d16e590473
