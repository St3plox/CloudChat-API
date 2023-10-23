# CloudChat API

SecureChat API is a Java Spring Boot application that provides a secure and efficient platform for storing and retrieving files while enabling secure communication between users through encrypted messages. The API leverages Spring Security for robust authentication and authorization mechanisms, ensuring the protection of sensitive user data.

## Features

- **File Storage and Retrieval:** Easily upload and retrieve files through RESTful endpoints, making use of PostgreSQL for efficient data management.

- **JWT Authorization and Authentication:** Implementing JSON Web Tokens (JWT) for secure user authentication and authorization, adding an extra layer of protection to your API.

- **Group Creation and Messaging:** Users can create groups, collaborate with each other, and exchange messages within these groups. Messages are encrypted using broadcast encryption for enhanced security.

- **Guava Cache Integration:** Utilize Guava Cache to store temporary encryption keys, ensuring optimal performance and automatically clearing keys after 12 hours.

## Development Setup

For simplicity during development, the project utilizes the same database for storing users' public and secret keys. Similarly, sensitive information like JWT secrets and connection settings are stored directly in constants within their respective classes.

Please note that this approach may differ in a production environment where a more secure storage strategy for secrets and keys is recommended.

## Project Structure

The project follows best practices for GitHub repositories, ensuring a clean and organized structure. Key components include:

- **src/main:** Contains the main source code of the application.
- **src/test:** Includes test cases to validate the functionality of the implemented features.

## Usage

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/secure-chat-api.git
2. Run the project.
  
   ```bash
   ./mvnw spring-boot:run
## Learning Purpose

This project was developed for learning purposes, combining Java Spring Boot, Spring Security, PostgreSQL, and Guava Cache to build a secure and efficient REST API. Feel free to explore, modify, and contribute to enhance the functionalities and security aspects of the SecureChat API.
