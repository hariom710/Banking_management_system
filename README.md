# 🏦 Banking Application

![Java](https://img.shields.io/badge/Java-v17-orange?style=flat&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-v8-blue?style=flat&logo=mysql)
![License](https://img.shields.io/badge/License-MIT-green?style=flat)
![Contributions Welcome](https://img.shields.io/badge/Contributions-Welcome-brightgreen)

**Banking Application** is a console-based project for performing essential banking operations like user registration, balance inquiry, secure transactions, and account management. This project is built using **Java**, **MySQL**, and **JDBC** for a robust backend experience.

---

## 🚀 Features

- 🔐 **User Registration and Login**: Securely register and authenticate users.
- 🏦 **Bank Account Management**:
  - Open new bank accounts.
  - Perform credit, debit, and fund transfer operations.
- 💰 **Balance Inquiry**: Check the balance of accounts anytime.
- 📊 **Database Integration**: Uses MySQL for managing user and account data.

---

## 🛠️ Technologies Used

| **Technology**   | **Purpose**                              |
|-------------------|------------------------------------------|
| **Java**          | Core programming language               |
| **MySQL**         | Database to store user and account data |
| **JDBC**          | Integration between Java and MySQL      |

---

## 📋 Prerequisites

Before running this project, ensure you have the following:

- [Java JDK 8+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [MySQL Server 8+](https://dev.mysql.com/downloads/)
- [MySQL Connector/J (JDBC Driver)](https://dev.mysql.com/downloads/connector/j/)

---

## 🗃️ Database Setup

1. Open MySQL and execute the following SQL commands:
   ```sql
   CREATE DATABASE bank_application;

   USE bank_application;

   CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       email VARCHAR(255) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL
   );

   CREATE TABLE accounts (
       account_number BIGINT AUTO_INCREMENT PRIMARY KEY,
       email VARCHAR(255) NOT NULL,
       balance DOUBLE DEFAULT 0,
       FOREIGN KEY (email) REFERENCES users(email)
   );

## 🖥️ How to Run

1. **Clone the repository**:  
   Clone the repository using the following command:  
   ```bash
   git clone https://github.com/hariom710/BankingApplication.git


    Navigate to the project folder:
    ```bash
    cd BankingApplication

    Compile the project:
    ```bash
    cd src
    javac -d ../out Banking_management_system/*.java banking_application/*.java

    Run the application:
    ```bash
    java -cp "../out;../lib/mysql-connector-j-9.1.0.jar" banking_application.BankingApp
(For macOS/Linux, replace ; with : in the -cp option.)



Customization (Optional): Feel free to modify the content, add more destinations, or change the visuals to fit your own safari packages.

# 🤝 Contributingg
We welcome contributions to Banking Application If you'd like to add new features follow these steps:

Fork the repository.
Create a new branch (git checkout -b feature-branch).
Add your changes (git add .).
Commit your changes (git commit -m 'Add new feature').
Push to the branch (git push origin feature-branch).
Open a Pull Request to the main branch.
Before submitting a pull request, ensure your code is properly formatted and includes necessary comments.

# 🧑‍💻 Code of Conduct
This project follows the Contributor Covenant Code of Conduct. We are committed to maintaining a harassment-free environment for all participants.

Please follow the guidelines outlined in the CODE_OF_CONDUCT.md file for more details.

# 📜 License
This project is licensed under the MIT License - see the LICENSE.md file for details.

### Key Sections:

- **Features**: Highlighted key features of the Banking Application.
- **Technologies Used**: Outlined the core technologies used for building the website (Java).
- **How to Use**: Provided clear steps on how to clone and run the project.
- **Contributing**: Explained how to contribute to the repository.
- **License**: Basic information about the license for the project.

🌟 Acknowledgements
Built with ❤️ using Java and MySQL.
Special thanks to open-source contributors and the development community.

This `README.md` provides an organized and clear introduction to your **Banking Application** project. You can expand or adjust the sections as your project grows.