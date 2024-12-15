package control;

import database.DataAdapter;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    // Add a new order
    public void addOrder(Order order) throws SQLException {
        String query = "INSERT INTO Orders (CustID, OrdDate, StatusID, TotAmt) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, order.getCustID());
            stmt.setDate(2, order.getOrdDate());
            stmt.setInt(3, order.getStatusID());
            stmt.setDouble(4, order.getTotAmt());
            stmt.executeUpdate();
        }
    }

    // Update an existing order
    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE Orders SET CustID = ?, OrdDate = ?, StatusID = ?, TotAmt = ? WHERE OrdID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, order.getCustID());
            stmt.setDate(2, order.getOrdDate());
            stmt.setInt(3, order.getStatusID());
            stmt.setDouble(4, order.getTotAmt());
            stmt.setInt(5, order.getOrdID());
            stmt.executeUpdate();
        }
    }

    // Delete an order by ID
    public void deleteOrder(int ordID) throws SQLException {
        String query = "DELETE FROM Orders WHERE OrdID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ordID);
            stmt.executeUpdate();
        }
    }

    // Retrieve all orders
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT OrdID, CustID, OrdDate, StatusID, TotAmt FROM Orders";
        try (Connection conn = DataAdapter.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
        }
        return orders;
    }

    // Retrieve a single order by ID
    public Order getOrderById(int ordID) throws SQLException {
        String query = "SELECT OrdID, CustID, OrdDate, StatusID, TotAmt FROM Orders WHERE OrdID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ordID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapOrder(rs);
                }
            }
        }
        return null;
    }

    // Helper method to map a ResultSet to an Order object
    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrdID(rs.getInt("OrdID"));
        order.setCustID(rs.getInt("CustID"));
        order.setOrdDate(rs.getDate("OrdDate"));
        order.setStatusID(rs.getInt("StatusID"));
        order.setTotAmt(rs.getDouble("TotAmt"));
        return order;
    }
}
