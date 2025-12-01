Admin Server (Spring Boot Admin) - Quickstart
============================================

This is a minimal Spring Boot Admin Server project.

How to run:
1. Build:
   mvn clean package

2. Run:
   mvn spring-boot:run
   OR
   java -jar target/admin-server-0.0.1-SNAPSHOT.jar

The admin UI will be available at: http://localhost:9090

To register a client application, add the spring-boot-admin-starter-client dependency
to the client, and configure spring.boot.admin.client.url to point to this server.
