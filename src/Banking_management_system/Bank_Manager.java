package Banking_management_system;

import java.sql.*;
import java.util.Scanner;

public class Bank_Manager {
    private final Connection connection;
    private final Scanner scanner;

    public Bank_Manager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void debit_money(long accountNumber) {
        System.out.print("Enter amount to debit: ");
        double amount = scanner.nextDouble();

        try (PreparedStatement ps = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?")) {
            ps.setDouble(1, amount);
            ps.setLong(2, accountNumber);
            ps.executeUpdate();
            System.out.println("Amount Debited Successfully!");
        } catch (SQLException e) {
            System.out.println("Error debiting money: " + e.getMessage());
        }
    }

    public void credit_money(long accountNumber) {
        System.out.print("Enter amount to credit: ");
        double amount = scanner.nextDouble();

        try (PreparedStatement ps = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?")) {
            ps.setDouble(1, amount);
            ps.setLong(2, accountNumber);
            ps.executeUpdate();
            System.out.println("Amount Credited Successfully!");
        } catch (SQLException e) {
            System.out.println("Error crediting money: " + e.getMessage());
        }
    }

    public void transfer_money(long fromAccount) {
        System.out.print("Enter recipient account number: ");
        long toAccount = scanner.nextLong();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement debitPs = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
                 PreparedStatement creditPs = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?")) {

                debitPs.setDouble(1, amount);
                debitPs.setLong(2, fromAccount);
                debitPs.executeUpdate();

                creditPs.setDouble(1, amount);
                creditPs.setLong(2, toAccount);
                creditPs.executeUpdate();

                connection.commit();
                System.out.println("Money Transferred Successfully!");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Error transferring money: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Transaction error: " + e.getMessage());
        }
    }

    public void getBalance(long accountNumber) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?")) {
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Current Balance: " + rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving balance: " + e.getMessage());
        }
    }
}
