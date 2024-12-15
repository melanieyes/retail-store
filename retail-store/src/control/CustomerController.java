package control;

import database.DataAdapter;
import model.Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    // Add a new customer
    public void addCustomer(Customers customer) throws SQLException {
        String query = "INSERT INTO Customers (FName, LName, Email, Phone, Address, JoinDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getFName());
            stmt.setString(2, customer.getLName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setDate(6, new java.sql.Date(customer.getJoinDate().getTime()));
            stmt.executeUpdate();
        }
    }

    // Get a customer by ID
    public Customers getCustomer(int custID) throws SQLException {
        String query = "SELECT * FROM Customers WHERE CustID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, custID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapCustomer(rs);
            }
        }
        return null;
    }

    // Update an existing customer
    public void updateCustomer(Customers customer) throws SQLException {
        String query = "UPDATE Customers SET FName = ?, LName = ?, Email = ?, Phone = ?, Address = ?, JoinDate = ? WHERE CustID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getFName());
            stmt.setString(2, customer.getLName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setDate(6, new java.sql.Date(customer.getJoinDate().getTime()));
            stmt.setInt(7, customer.getCustID());
            stmt.executeUpdate();
        }
    }

    // Delete a customer by ID
    public void deleteCustomer(int custID) throws SQLException {
        String query = "DELETE FROM Customers WHERE CustID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, custID);
            stmt.executeUpdate();
        }
    }

    // Get all customers
    public List<Customers> getAllCustomers() throws SQLException {
        List<Customers> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers";
        try (Connection conn = DataAdapter.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        }
        return customers;
    }

    // Helper method to map a ResultSet to a Customer object
    private Customers mapCustomer(ResultSet rs) throws SQLException {
        Customers customer = new Customers();
        customer.setCustID(rs.getInt("CustID"));
        customer.setFName(rs.getString("FName"));
        customer.setLName(rs.getString("LName"));
        customer.setEmail(rs.getString("Email"));
        customer.setPhone(rs.getString("Phone"));
        customer.setAddress(rs.getString("Address"));
        customer.setJoinDate(rs.getDate("JoinDate"));
        return customer;
    }
}
