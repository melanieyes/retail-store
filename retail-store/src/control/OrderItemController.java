package control;

import database.DataAdapter;
import model.OrderItem;

import java.sql.*;
import java.util.*;

public class OrderItemController {

   
    public void addOrderItem(OrderItem orderItem) throws SQLException {
        String query = "INSERT INTO OrdItem (OrdID, ProdsID, Qty, UnitPrice, TotalPrice) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderItem.getOrdID());
            stmt.setInt(2, orderItem.getProdsID());
            stmt.setInt(3, orderItem.getQty());
            stmt.setDouble(4, orderItem.getUnitPrice());
            stmt.setDouble(5, orderItem.getTotalPrice());
            stmt.executeUpdate();
        }
    }

    public List<OrderItem> getOrderItems(int ordID) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM OrdItem WHERE OrdID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ordID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderItems.add(mapOrderItem(rs));
            }
        }
        return orderItems;
    }


    public void deleteOrderItem(int ordItemID) throws SQLException {
        String query = "DELETE FROM OrdItem WHERE OrdItemID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ordItemID);
            stmt.executeUpdate();
        }
    }


    private OrderItem mapOrderItem(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrdItemID(rs.getInt("OrdItemID"));
        orderItem.setOrdID(rs.getInt("OrdID"));
        orderItem.setProdsID(rs.getInt("ProdsID"));
        orderItem.setQty(rs.getInt("Qty"));
        orderItem.setUnitPrice(rs.getDouble("UnitPrice"));
        orderItem.setTotalPrice(rs.getDouble("TotalPrice"));
        return orderItem;
    }
}
