package Banking_management_system;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private final Connection connection;
    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
    }

    public boolean account_exist(String email) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM accounts WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking account existence: " + e.getMessage());
        }
        return false;
    }

    public long open_account(String email) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO accounts (email, balance) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, email);
            ps.setDouble(2, 0);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println("Error opening account: " + e.getMessage());
        }
        return -1;
    }

    public long getAccount_number(String email) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT account_number FROM accounts WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("account_number");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving account number: " + e.getMessage());
        }
        return -1;
    }
}
