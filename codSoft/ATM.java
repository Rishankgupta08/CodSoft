import java.util.*;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount account = new BankAccount("Rishank Gupta", 5000.00, 1234);

        System.out.print("Enter your 4-digit ATM PIN: ");
        int inputPin = sc.nextInt();

        if (!account.verifyPin(inputPin)) {
            System.out.println("Invalid PIN. Access Denied.");
            sc.close();
            return;
        }

        System.out.println("Welcome to CodSoft ATM, " + account.getAccountHolder() + "!");

        List<String> transactionHistory = new ArrayList<>();

        while (true) {
            System.out.println("\n----- ATM Menu -----");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");
            
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    double balance = account.getBalance();
                    System.out.println("Your current balance is " + balance);
                    transactionHistory.add("Checked balance: " + balance);
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double deposit = sc.nextDouble();
                    account.deposit(deposit);
                    transactionHistory.add("Deposited " + deposit);
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdraw = sc.nextDouble();
                    double before = account.getBalance();
                    account.withdraw(withdraw);
                    if (before != account.getBalance()) {
                        transactionHistory.add("Withdrew " + withdraw);
                    }
                    break;

                case 4:
                    System.out.println("---- Transaction History ----");
                    if (transactionHistory.isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        for (String record : transactionHistory) {
                            System.out.println(record);
                        }
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using CodSoft ATM. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
class BankAccount {
    private String accountHolder;
    private double balance;
    private int pin;

    public BankAccount(String accountHolder, double initialBalance, int pin) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.pin = pin;
    }

    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(amount + " deposited successfully.");
        } else {
            System.out.println("Invalid amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(amount + " withdrawn successfully.");
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}
