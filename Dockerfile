FROM maven:3.9.9-eclipse-temurin-23

ARG DB_PASSWORD
ARG DB_USERNAME
ARG JWT_SECRET
ARG JWT_EXPIRATION

ENV DB_PASSWORD $DB_PASSWORD
ENV DB_USERNAME $DB_USERNAME
ENV JWT_SECRET $JWT_SECRET
ENV JWT_EXPIRATION $JWT_EXPIRATION

WORKDIR /app

COPY . /app

RUN mvn package

EXPOSE 8081

ENTRYPOINT ["mvn","spring-boot:run"]



