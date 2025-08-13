Notes service
-
This repo contains project which is currently deployed on domain https://nikita-lavrenchuk/notes

Before steps
-

**MySQL database setup**

Fill **MySQL** server parameters at `Notes-service/src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:mysql://<your-db-host>:<your-db-port>/messages
spring.datasource.username=<your-db-username>
spring.datasource.password=<your-db-password>
```

Connect to your *MySQL* server and run following commands sequentially:

```
CREATE DATABASE messages;
```

```
USE messages;
```

```
CREATE TABLE message_details (message VARCHAR(1000), creator VARCHAR(50), create_time VARCHAR(255), public BOOL, receiver_user VARCHAR(50), file_name VARCHAR(255), id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));
```

```
CREATE TABLE users (username VARCHAR(50) NOT NULL, password VARCHAR(255) NOT NULL, enabled tinyint NOT NULL, PRIMARY KEY (username));
```

```
CREATE TABLE authorities (username VARCHAR(50) NOT NULL, authority VARCHAR(50) NOT NULL, UNIQUE KEY authorities_idx_1 (username, authority), constraint authorities_ibfk_1 FOREIGN KEY (username) REFERENCES users (username));
```

Local run
-

**Prerequisites**
- Java >=11
- Maven

```
mvn -D maven.test.skip clean install
java -jar target/notes-1.0.0.jar
```

The app will be running on address http://localhost:8090/notes