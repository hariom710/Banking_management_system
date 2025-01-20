package banking_application;

import java.sql.*;
import java.util.Scanner;

public class Bank_Manager {
    private Connection connection;
    private Scanner scanner;

    public Bank_Manager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void credit_money(long account_number) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?")) {
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String credit_query = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
                    try (PreparedStatement creditStmt = connection.prepareStatement(credit_query)) {
                        creditStmt.setDouble(1, amount);
                        creditStmt.setLong(2, account_number);
                        int rowsAffected = creditStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Rs." + amount + " credited successfully.");
                            connection.commit();
                        } else {
                            System.out.println("Transaction failed!");
                            connection.rollback();
                        }
                    }
                } else {
                    System.out.println("Invalid Security Pin!");
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void debit_money(long account_number) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?")) {
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (amount <= current_balance) {
                        String debit_query = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                        try (PreparedStatement debitStmt = connection.prepareStatement(debit_query)) {
                            debitStmt.setDouble(1, amount);
                            debitStmt.setLong(2, account_number);
                            int rowsAffected = debitStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Rs." + amount + " debited successfully.");
                                connection.commit();
                            } else {
                                System.out.println("Transaction failed!");
                                connection.rollback();
                            }
                        }
                    } else {
                        System.out.println("Insufficient Balance!");
                        connection.rollback();
                    }
                } else {
                    System.out.println("Invalid Security Pin!");
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void transfer_money(long sender_account_number) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiver_account_number = scanner.nextLong();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?")) {
            preparedStatement.setLong(1, sender_account_number);
            preparedStatement.setString(2, security_pin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (amount <= current_balance) {
                        String debit_query = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                        String credit_query = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
                        
                        try (PreparedStatement debitStmt = connection.prepareStatement(debit_query);
                             PreparedStatement creditStmt = connection.prepareStatement(credit_query)) {
                             
                            debitStmt.setDouble(1, amount);
                            debitStmt.setLong(2, sender_account_number);
                            creditStmt.setDouble(1, amount);
                            creditStmt.setLong(2, receiver_account_number);

                            int rowsAffected1 = debitStmt.executeUpdate();
                            int rowsAffected2 = creditStmt.executeUpdate();

                            if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                                System.out.println("Transaction successful! Rs." + amount + " transferred successfully.");
                                connection.commit();
                            } else {
                                System.out.println("Transaction failed!");
                                connection.rollback();
                            }
                        }
                    } else {
                        System.out.println("Insufficient Balance!");
                        connection.rollback();
                    }
                } else {
                    System.out.println("Invalid Security Pin!");
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void getBalance(long account_number) {
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?")) {
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double balance = resultSet.getDouble("balance");
                    System.out.println("Balance: " + balance);
                } else {
                    System.out.println("Invalid Security Pin!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
