import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MiniBankController {
    private Connection conn;
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;

    public MiniBankController(Connection conn) {
        this.conn = conn;
        this.customerDAO = new CustomerDAO(conn);
        this.accountDAO = new AccountDAO(conn);
    }

    // Method to add a new customer
    public void addCustomer(Scanner sc) {
        try {
            System.out.println("\n--- Add New Customer ---");
            System.out.print("Surname: ");
            String surname = sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Gender (M/F): ");
            String gender = sc.nextLine();
            System.out.print("Birthday (YYYY-MM-DD): ");
            String birthdayStr = sc.nextLine();
            Date birthday = Date.valueOf(birthdayStr);

            // Generate account number
            int accountNumber = generateAccountNumber();

            // Select account type
            System.out.println("Select Account Type:");
            System.out.println("1. Checking Account");
            System.out.println("2. Savings Account");
            System.out.println("3. Fixed-Terms Account");
            System.out.print("Selection: ");
            int accountTypeSelection = Integer.parseInt(sc.nextLine());

            Account account;

            switch (accountTypeSelection) {
                case 1:
                    account = new CheckingAcc(0, accountNumber, 0.0);
                    break;
                case 2:
                    account = new SavingsAcc(0, accountNumber, 0.0);
                    break;
                case 3:
                    account = new FixedTermsAcc(0, accountNumber, 0.0);
                    break;
                default:
                    System.out.println("Invalid account type selected. Defaulting to Checking Account.");
                    account = new CheckingAcc(0, accountNumber, 0.0);
                    break;
            }

            System.out.print("Initial Balance: ");
            double balance = Double.parseDouble(sc.nextLine());
            account.setBalance(balance);

            // Create Customer object
            Customer customer = new Customer(0, surname, name, gender, birthday, account);

            // Save customer and account to database
            customerDAO.addCustomer(customer);
            accountDAO.addAccount(account, customer.getID());

            System.out.println("Customer and account successfully added. Customer ID: " + customer.getID() + ", Account Number: " + accountNumber);
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
        }
    }

    // Method to perform a pay-out from an account
    public void doPayOut(Scanner sc) {
        try {
            System.out.println("\n--- Pay Out ---");
            System.out.print("Account Number: ");
            int accountNumber = Integer.parseInt(sc.nextLine());
            Account account = accountDAO.getAccountByAccountNumber(accountNumber);

            if (account != null) {
                System.out.print("Amount to Pay Out: ");
                double amount = Double.parseDouble(sc.nextLine());
                if (account.payOut(amount)) {
                    accountDAO.updateAccount(account);
                    System.out.println("Pay Out successful. New balance: " + account.getBalance());
                } else {
                    System.out.println("Insufficient funds.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error during pay out: " + e.getMessage());
        }
    }

    // Method to perform a pay-in to an account
    public void doPayIn(Scanner sc) {
        try {
            System.out.println("\n--- Pay In ---");
            System.out.print("Account Number: ");
            int accountNumber = Integer.parseInt(sc.nextLine());
            Account account = accountDAO.getAccountByAccountNumber(accountNumber);

            if (account != null) {
                System.out.print("Amount to Pay In: ");
                double amount = Double.parseDouble(sc.nextLine());
                account.payIn(amount);
                accountDAO.updateAccount(account);
                System.out.println("Pay In successful. New balance: " + account.getBalance());
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error during pay in: " + e.getMessage());
        }
    }

    // Method to show specific customer data
    public void showCustomerData(Scanner sc) {
    try {
        System.out.println("\n--- Show Customer Data ---");
        System.out.print("Customer ID: ");
        int customerId = Integer.parseInt(sc.nextLine());
        Customer customer = customerDAO.getCustomerByID(customerId);

        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("Customer not found.");
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving customer data: " + e.getMessage());
    }
}


    // Method to show all customer data (admin only)
    public void showAllCustomerData() {
    try {
        System.out.println("\n--- Show All Customer Data ---");
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving customer data: " + e.getMessage());
    }
}


    // Method to change customer data (admin only)
    public void changeCustomerData(Scanner sc) {
        try {
            System.out.println("\n--- Change Customer Data ---");
            System.out.print("Customer ID: ");
            int customerId = Integer.parseInt(sc.nextLine());
            Customer customer = customerDAO.getCustomerByID(customerId);

            if (customer != null) {
                System.out.println("Current Data: " + customer);
                System.out.print("New Surname (leave blank to keep current): ");
                String surname = sc.nextLine();
                if (!surname.isEmpty()) {
                    customer.setSurname(surname);
                }
                System.out.print("New Name (leave blank to keep current): ");
                String name = sc.nextLine();
                if (!name.isEmpty()) {
                    customer.setName(name);
                }
                // Additional fields...
                customerDAO.updateCustomer(customer);
                System.out.println("Customer data successfully updated.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating customer data: " + e.getMessage());
        }
    }

    // Helper method to generate a new account number
    private int generateAccountNumber() {
        // Implement logic to generate a unique account number
        // For example, generate a random 10-digit number
        return (int) (Math.random() * 1_000_000_000);
    }
}
