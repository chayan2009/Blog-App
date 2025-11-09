# Quick Start Guide

This guide will help you get the Blog App up and running in minutes.

## Prerequisites Check

Before you begin, verify you have the required tools:

```bash
# Check Java version (must be 17 or higher)
java -version

# Check Maven (optional, wrapper included)
mvn -version
```

If Java is not installed or version is below 17, see the [Java Installation Guide](README.md#installing-java) in the main README.

## 1. Clone the Repository

```bash
git clone https://github.com/<your-username>/Blog-App.git
cd Blog-App
```

## 2. Choose Your Application

### Option A: BlogApp (Simple REST API, No Database)

Perfect for learning Spring Boot basics without database complexity.

```bash
cd BlogApp
mvn spring-boot:run
```

**Test it:**
```bash
curl http://localhost:8080/posts
```

### Option B: BlogApp-H2Db (Full Stack with Database)

Recommended for most use cases. Includes database, Swagger UI, and full CRUD operations.

```bash
cd BlogApp-H2Db
mvn spring-boot:run
```

**Access the application:**
- API: http://localhost:8080/posts
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave blank)

## 3. Test the API

### Create a post
```bash
curl -X POST http://localhost:8080/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My First Post",
    "content": "Hello, World!",
    "author": "John Doe"
  }'
```

### Get all posts
```bash
curl http://localhost:8080/posts
```

### Get a specific post
```bash
curl http://localhost:8080/posts/1
```

### Update a post
```bash
curl -X PUT http://localhost:8080/posts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Title",
    "content": "Updated content",
    "author": "John Doe"
  }'
```

### Delete a post
```bash
curl -X DELETE http://localhost:8080/posts/1
```

## 4. Explore with Swagger UI (BlogApp-H2Db only)

Open http://localhost:8080/swagger-ui.html in your browser to:
- View all available endpoints
- Test API calls directly from the browser
- See request/response schemas

## 5. View Database (BlogApp-H2Db only)

1. Open http://localhost:8080/h2-console
2. Enter connection details:
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (leave blank)
3. Click "Connect"
4. Run SQL queries to see your data:
   ```sql
   SELECT * FROM posts;
   ```

## Common Issues

### "Unable to locate a Java Runtime"
**Solution:** Install JDK 17 or higher. See [README - Installing Java](README.md#installing-java)

### "Port 8080 is already in use"
**Solution:** Stop the process using port 8080 or change the port:
```bash
# In application.properties, add:
server.port=8081
```

### "Schema-validation: missing table"
**Solution:** This happens when using MySQL/PostgreSQL with wrong configuration. 
For development, ensure `spring.jpa.hibernate.ddl-auto=update` in `application.properties`.
See [README - Troubleshooting](README.md#troubleshooting) for details.

## Next Steps

- Read the full [README.md](README.md) for detailed configuration options
- Explore database configurations for MySQL/PostgreSQL
- Check out the [API Endpoints documentation](README.md#api-endpoints)
- Learn about production deployment best practices

## Need Help?

- Check the [Troubleshooting section](README.md#troubleshooting) in README
- Create an issue on GitHub
- Review [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)

Happy coding! 🚀
