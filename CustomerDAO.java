import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection conn;
    private AccountDAO accountDAO;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
        this.accountDAO = new AccountDAO(conn);
    }

    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO YA_Customer (Surname, Name, Gender, Birthday) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getSurname());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getGender());
            stmt.setDate(4, customer.getBirthday());
            stmt.executeUpdate();

            // Holen der generierten ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                customer.setID(rs.getInt(1));
            }
        }
    }

    public Customer getCustomerByID(int id) throws SQLException {
    String sql = "SELECT * FROM YA_Customer WHERE ID = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Customer customer = mapResultSetToCustomer(rs);

            // Konten des Kunden laden
            List<Account> accounts = accountDAO.getAccountsByCustomerID(id);
            customer.setAccounts(accounts);

            return customer;
        } else {
            return null;
        }
    }
}


    public List<Customer> getAllCustomers() throws SQLException {
    String sql = "SELECT * FROM YA_Customer";
    List<Customer> customers = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Customer customer = mapResultSetToCustomer(rs);

            // Konten des Kunden laden
            List<Account> accounts = accountDAO.getAccountsByCustomerID(customer.getID());
            customer.setAccounts(accounts);

            customers.add(customer);
        }
    }
    return customers;
}


    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE YA_Customer SET Surname = ?, Name = ?, Gender = ?, Birthday = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getSurname());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getGender());
            stmt.setDate(4, customer.getBirthday());
            stmt.setInt(5, customer.getID());
            stmt.executeUpdate();
        }
    }

    // Hilfsmethode zur Abbildung des ResultSets auf ein Customer-Objekt
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setID(rs.getInt("ID"));
        customer.setSurname(rs.getString("Surname"));
        customer.setName(rs.getString("Name"));
        customer.setGender(rs.getString("Gender"));
        customer.setBirthday(rs.getDate("Birthday"));
        // Account muss separat geladen werden
        return customer;
    }
}
