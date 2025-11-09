# Blog App - Spring Boot REST API

This repository contains two Spring Boot applications for managing blog posts:

1. **BlogApp** - Basic Spring Boot REST API for managing blog posts
2. **BlogApp-H2Db** - Spring Boot REST API with H2 in-memory database and JPA support

---

## Prerequisites

### Java Runtime Requirements

Before running this application, ensure you have **Java Development Kit (JDK) 17 or higher** installed on your system.

#### Installing Java

**macOS:**
```bash
# Using Homebrew
brew install openjdk@17

# Or download from Oracle
# Visit https://www.oracle.com/java/technologies/downloads/
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Windows:**
- Download JDK 17 from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)
- Run the installer and follow the setup wizard
- Set JAVA_HOME environment variable

**Verify Installation:**
```bash
java -version
# Should display: java version "17.x.x" or higher
```

### Maven

Maven 3.6+ is required (bundled with wrapper in the project).

---

## Project Structure

```
Blog-App/
├── BlogApp/           # Basic REST API without database
└── BlogApp-H2Db/      # REST API with H2 database and JPA
```

---

## Features

- RESTful CRUD endpoints (`GET`, `POST`, `PUT`, `DELETE`)
- Unit tests with JUnit 5 + MockMvc
- Swagger UI documentation (BlogApp-H2Db)
- Maven build & run
- H2 in-memory database with auto-schema creation (BlogApp-H2Db)
- JPA/Hibernate support (BlogApp-H2Db)

---

## Running the Applications

### Option 1: BlogApp (Basic API)

```bash
git clone https://github.com/<your-username>/Blog-App.git
cd Blog-App/BlogApp
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

### Option 2: BlogApp-H2Db (API with Database)

```bash
git clone https://github.com/<your-username>/Blog-App.git
cd Blog-App/BlogApp-H2Db
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

**Access H2 Console:** http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

**Swagger UI:** http://localhost:8080/swagger-ui.html

---

## Building the Applications

```bash
# Build BlogApp
cd BlogApp
mvn clean install

# Build BlogApp-H2Db
cd BlogApp-H2Db
mvn clean install
```

---

## Running Tests

```bash
# Test BlogApp
cd BlogApp
mvn test

# Test BlogApp-H2Db
cd BlogApp-H2Db
mvn test
```

---

## Database Configuration

### Using H2 Database (Development)

BlogApp-H2Db is pre-configured with H2 in-memory database. The configuration in `application.properties`:

```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate - Auto-create schema
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Using MySQL Database (Production)

To use MySQL instead of H2, update `application.properties`:

```properties
# MySQL Database
spring.datasource.url=jdbc:mysql://localhost:3306/blogdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate - Auto-create/update schema
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

**Add MySQL dependency to `pom.xml`:**
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

**Create the database:**
```sql
CREATE DATABASE blogdb;
```

### Using PostgreSQL Database

Update `application.properties`:

```properties
# PostgreSQL Database
spring.datasource.url=jdbc:postgresql://localhost:5432/blogdb
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate - Auto-create/update schema
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

**Add PostgreSQL dependency to `pom.xml`:**
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## Hibernate DDL Auto Configuration

The `spring.jpa.hibernate.ddl-auto` property controls schema management:

| Value | Description | Use Case |
|-------|-------------|----------|
| `create` | Drop and recreate schema on startup | Initial development |
| `create-drop` | Create schema on startup, drop on shutdown | Testing |
| `update` | Update schema if needed, never drops | Development |
| `validate` | Validate schema, throws error if mismatch | Production (requires pre-existing schema) |
| `none` | No schema management | Production with manual migrations |

**⚠️ Important:** 
- For **development**, use `update` to automatically create/update tables
- For **production**, use `validate` or `none` with manual schema migrations (Flyway/Liquibase)
- Never use `create` or `create-drop` in production!

---

## Troubleshooting

### Issue: "Unable to locate a Java Runtime"

**Solution:**
1. Ensure Java 17 or higher is installed:
   ```bash
   java -version
   ```
2. If not installed, follow the [Installing Java](#installing-java) section above
3. Verify JAVA_HOME is set correctly:
   ```bash
   echo $JAVA_HOME  # macOS/Linux
   echo %JAVA_HOME% # Windows
   ```

### Issue: "Schema-validation: missing table [tablename]"

**Error Example:**
```
org.hibernate.tool.schema.spi.SchemaManagementException: Schema-validation: missing table [users]
```

**Solution:**

This error occurs when `spring.jpa.hibernate.ddl-auto=validate` but the required tables don't exist in the database.

**Option 1: Auto-create tables (Development)**
Change in `application.properties`:
```properties
spring.jpa.hibernate.ddl-auto=update
```

**Option 2: Create tables manually (Production)**
Create the required schema before starting the application:

```sql
-- Example for posts table
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content VARCHAR(255),
    author VARCHAR(255)
);
```

**Option 3: Use database migration tools**
- [Flyway](https://flywaydb.org/) - Version-controlled migrations
- [Liquibase](https://www.liquibase.org/) - Database-independent changes

### Issue: "Connection refused" when connecting to MySQL

**Solution:**
1. Ensure MySQL is running:
   ```bash
   # macOS
   brew services start mysql
   
   # Linux
   sudo systemctl start mysql
   ```
2. Verify database exists:
   ```sql
   SHOW DATABASES;
   CREATE DATABASE IF NOT EXISTS blogdb;
   ```
3. Check credentials in `application.properties`
4. Ensure MySQL is accepting connections on the configured port (default: 3306)

### Issue: Port 8080 already in use

**Solution:**
Change the port in `application.properties`:
```properties
server.port=8081
```

---

## API Endpoints

### BlogApp and BlogApp-H2Db

#### Get all posts
```
GET http://localhost:8080/posts
```

#### Get post by ID
```
GET http://localhost:8080/posts/{id}
```

#### Create a new post
```
POST http://localhost:8080/posts
Content-Type: application/json

{
  "title": "My First Post",
  "content": "This is the content of my first post",
  "author": "John Doe"
}
```

#### Update a post
```
PUT http://localhost:8080/posts/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "content": "Updated content",
  "author": "John Doe"
}
```

#### Delete a post
```
DELETE http://localhost:8080/posts/{id}
```

---

## Technology Stack

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Web** - RESTful API
- **Spring Data JPA** - Database access (BlogApp-H2Db)
- **H2 Database** - In-memory database (BlogApp-H2Db)
- **Lombok** - Reduce boilerplate code (BlogApp-H2Db)
- **Swagger/OpenAPI** - API documentation (BlogApp-H2Db)
- **JUnit 5** - Unit testing
- **Maven** - Build tool

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## License

This project is open source and available under the MIT License.

---

## Support

For issues and questions:
- Create an issue in the GitHub repository
- Check the [Troubleshooting](#troubleshooting) section above
- Refer to [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
