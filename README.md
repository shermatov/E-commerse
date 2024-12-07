# 🛍️ Dream Shop

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green.svg)

**Dream Shop** is a cutting-edge e-commerce application designed for managing and selling products online. It leverages modern development frameworks and provides seamless user experiences through secure authentication and intuitive APIs.

---

## 🚀 Features
- 🧑‍💻 **User Management:** Secure user registration and login.
- 🔒 **JWT Authentication:** Protect endpoints with token-based authentication.
- 📦 **Product Management:** Add, edit, and display products easily.
- 🌐 **RESTful API:** Modular API with versioning support.
- ⚙️ **Database Management:** Robust database handling with Hibernate and Flyway.
- 🛠️ **Scalable Deployment:** Dockerized for easy cloud hosting.

---

## 📋 Technologies Used

| Technology        | Purpose                                |
|--------------------|----------------------------------------|
| **Java**          | Primary backend language              |
| **Spring Boot 3** | Backend framework for REST API        |
| **MySQL**         | Database management system            |
| **JWT**           | Secure token-based authentication     |
| **Docker**        | Containerization for deployment       |
| **Flyway**        | Database migration management         |
| **React/Angular** | (Optional) Frontend integration       |

---

## 🛠️ Getting Started

### Prerequisites
- JDK 17 or later
- MySQL installed and running
- Maven installed
- Docker (optional for containerization)

### Installation Steps

1. **Clone the Repository**
    ```bash
    git clone https://github.com/yourusername/dream-shop.git
    cd dream-shop
    ```

2. **Configure the Application**
    Update the `application.yml` file with your database credentials:
    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/dream_shops_db
        username: root
        password: YOUR_PASSWORD
    auth:
      token:
        jwtSecret: YOUR_SECRET_KEY
    ```

3. **Run the Application**
    ```bash
    ./mvnw spring-boot:run
    ```

4. **Access the API**
    Visit the API at: [http://localhost:8080/api/v1](http://localhost:8080/api/v1)

---

## 📌 API Endpoints

| Endpoint                 | Method | Description                    |
|--------------------------|--------|--------------------------------|
| `/auth/register`         | POST   | Register a new user           |
| `/auth/login`            | POST   | Authenticate and get a token  |
| `/products`              | GET    | Retrieve all products         |
| `/products`              | POST   | Add a new product (Admin only)|

---



---

## 🛠️ Contributing

1. Fork the repository.
2. Create a feature branch:
    ```bash
    git checkout -b feature-branch-name
    ```
3. Commit your changes:
    ```bash
    git commit -m "Add some feature"
    ```
4. Push to the branch:
    ```bash
    git push origin feature-branch-name
    ```
5. Open a pull request.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 💬 Contact
For suggestions or inquiries, contact me at:  
📧 **[aibekshermatovv@example.com](mailto:aibekshermatovv@example.com)**  
🌐 **[GitHub Profile](https://github.com/shermatov)**  
