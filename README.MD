# Getting Started
## Prerequisites
- Docker
- Internet connection
- Access Key from: https://currencylayer.com/ - register and get the free access key

## Installation
1. Clone the repository
2. Run the following command from project root directory
    ```docker compose up --build```
3. Access the openapi Swagger docs at http://localhost:8080/swagger-ui.html
4. Access the H2 database console at http://localhost:8080/h2-console
    - JDBC URL: jdbc:h2:mem:testdb
    - User Name: sa
    - Password: password