import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection conn;

    public AccountDAO(Connection conn) {
        this.conn = conn;
    }

    // Method to add an Account to the database
    public void addAccount(Account account, int customerId) throws SQLException {
        String sql = "INSERT INTO YA_Account (ID, Balance, CustomerID, AccountNumber, AccountType) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, account.getID());
            stmt.setDouble(2, account.getBalance());
            stmt.setInt(3, customerId);
            stmt.setInt(4, account.getAccountNumber());
            stmt.setString(5, account.getAccountType());
            stmt.executeUpdate();
        }
    }

    // Method to retrieve an Account by account number
    public Account getAccountByAccountNumber(int accountNumber) throws SQLException {
        String sql = "SELECT * FROM YA_Account WHERE AccountNumber = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToAccount(rs);
            } else {
                return null;
            }
        }
    }

    // Method to update an existing Account
    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE YA_Account SET Balance = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getID());
            stmt.executeUpdate();
        }
    }

// Method to retrieve all accounts associated with a specific customer ID
    public List<Account> getAccountsByCustomerID(int customerId) throws SQLException {
        String sql = "SELECT * FROM YA_Account WHERE CustomerID = ?";
        List<Account> accounts = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = mapResultSetToAccount(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving accounts for customer ID " + customerId + ": " + e.getMessage());
        }

        return accounts;
    }

    // Helper method to map a ResultSet to an Account object
    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        int accountNumber = rs.getInt("AccountNumber");
        double balance = rs.getDouble("Balance");
        String accountType = rs.getString("AccountType");
    
        Account account;
    
        switch (accountType) {
            case "Fixed-Terms Account":
                account = new FixedTermsAcc(id, accountNumber, balance);
                break;
            case "Checking Account":
                account = new CheckingAcc(id, accountNumber, balance);
                break;
            case "Savings Account":
                account = new SavingsAcc(id, accountNumber, balance);
                break;
            default:
                throw new SQLException("Unknown account type: " + accountType);
        }
    
        return account;
    }
    

    // Additional methods as needed...
}
