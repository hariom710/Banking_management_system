package banking_application;

import java.sql.*;
import java.util.Scanner;

public class BankingApp {
    private static final String url = "jdbc:mysql://localhost:3306/bank_application";
    private static final String username = "root";
    private static final String password = "Password@123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Database driver not found: " + e.getMessage());
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Scanner scanner = new Scanner(System.in)) {

            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            Bank_Manager bank_manager = new Bank_Manager(connection, scanner);

            int choice;
            do {
                System.out.println("\n*** WELCOME TO BANKING SYSTEM ***");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                choice = getValidIntInput(scanner, "Enter your choice: ");

                switch (choice) {
                    case 1:
                        registerUser(user);
                        break;
                    case 2:
                        loginUser(user, accounts, bank_manager, scanner);
                        break;
                    case 3:
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                        break;
                    default:
                        System.out.println("Enter a valid choice.");
                }
            } while (choice != 3);

        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private static void registerUser(User user) {
        user.register();
    }

    private static void loginUser(User user, Accounts accounts, Bank_Manager bank_manager, Scanner scanner) {
        String email = user.login();
        if (email != null) {
            handleUserOperations(email, accounts, bank_manager, scanner);
        } else {
            System.out.println("Incorrect Email or Password!");
        }
    }

    private static void handleUserOperations(String email, Accounts accounts, Bank_Manager bank_manager, Scanner scanner) {
        if (!accounts.account_exist(email)) {
            System.out.println("1. Open a new Bank Account");
            System.out.println("2. Exit");
            if (getValidIntInput(scanner, "Enter your choice: ") == 1) {
                long accountNumber = accounts.open_account(email);
                System.out.println("Account Created Successfully");
                System.out.println("Your Account Number is: " + accountNumber);
            } else {
                return;
            }
        }

        long accountNumber = accounts.getAccount_number(email);
        int choice;
        do {
            System.out.println("\n1. Debit Money");
            System.out.println("2. Credit Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Log Out");
            choice = getValidIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    bank_manager.debit_money(accountNumber);
                    break;
                case 2:
                    bank_manager.credit_money(accountNumber);
                    break;
                case 3:
                    bank_manager.transfer_money(accountNumber);
                    break;
                case 4:
                    bank_manager.getBalance(accountNumber);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Enter Valid Choice!");
            }
        } while (choice != 5);
    }

    private static int getValidIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
