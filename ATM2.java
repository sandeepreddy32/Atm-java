import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private String userId;
    private int pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(String userId, int pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(String userId, int pin) {
        return this.userId.equals(userId) && this.pin == pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount);
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            System.out.println("Successfully withdrew $" + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void transfer(Account receiver, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + receiver.userId);
            receiver.transactionHistory.add("Received: $" + amount + " from " + this.userId);
            System.out.println("Successfully transferred $" + amount);
        } else {
            System.out.println("Transfer failed: Insufficient balance or invalid amount.");
        }
    }

    public void showTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}

public class ATM2 {
    private static Scanner scanner = new Scanner(System.in);
    
    // Hardcoded User Credentials
    private static Account userAccount = new Account("1234", 1234, 1000.00);
    private static Account receiverAccount = new Account("receiver456", 5678, 500.00);

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM System!");

        int attempts = 3;
        while (attempts > 0) {
            if (authenticateUser()) {
                showMenu();
                scanner.close();
                return;
            } else {
                attempts--;
                System.out.println("Incorrect User ID or PIN. Attempts left: " + attempts);
            }
        }
        System.out.println("Too many failed attempts. Exiting...");
        scanner.close();
    }

    private static boolean authenticateUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        int pin = scanner.nextInt();

        return userAccount.authenticate(userId, pin);
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    userAccount.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (withdrawAmount <= 0) {
                        System.out.println("Invalid withdrawal amount.");
                    } else {
                        userAccount.withdraw(withdrawAmount);
                    }
                    break;
                case 3:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    if (depositAmount <= 0) {
                        System.out.println("Invalid deposit amount.");
                    } else {
                        userAccount.deposit(depositAmount);
                    }
                    break;
                case 4:
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    if (transferAmount <= 0) {
                        System.out.println("Invalid transfer amount.");
                    } else {
                        userAccount.transfer(receiverAccount, transferAmount);
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}