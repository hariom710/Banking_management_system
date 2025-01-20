package banking_application;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public long open_account(String email) {
        if (!account_exist(email)) {
            String open_account_query = "INSERT INTO Accounts(account_number, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";
            scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String full_name = scanner.nextLine();
            System.out.print("Enter Initial Amount: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter Security Pin: ");
            String security_pin = scanner.nextLine();

            long account_number = generateAccountNumber();
            try (PreparedStatement preparedStatement = connection.prepareStatement(open_account_query)) {
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, full_name);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, security_pin);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Account created successfully with account number: " + account_number);
                    return account_number;
                } else {
                    throw new RuntimeException("Account creation failed!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Account creation failed due to a database error.");
            }
        } else {
            throw new RuntimeException("Account already exists for this email.");
        }
    }

    public long getAccount_number(String email) {
        String query = "SELECT account_number FROM Accounts WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("account_number");
                } else {
                    throw new RuntimeException("Account number doesn't exist for the provided email.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve account number due to a database error.");
        }
    }

    private long generateAccountNumber() {
        String query = "SELECT account_number FROM Accounts ORDER BY account_number DESC LIMIT 1";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                long last_account_number = resultSet.getLong("account_number");
                return last_account_number + 1;
            } else {
                return 10000100;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate account number due to a database error.");
        }
    }

    public boolean account_exist(String email) {
        String query = "SELECT account_number FROM Accounts WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to check if account exists due to a database error.");
        }
    }
}
