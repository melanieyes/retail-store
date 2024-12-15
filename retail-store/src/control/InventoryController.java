package control;

import database.DataAdapter;
import model.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {


    public void addInventory(Inventory inventory) throws SQLException {
        String query = "INSERT INTO Inventory (ProdsID, QtyInStock) VALUES (?, ?)";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, inventory.getProdsID());
            stmt.setInt(2, inventory.getQtyInStock());
            stmt.executeUpdate();
        }
    }


    public void updateInventory(Inventory inventory) throws SQLException {
        String query = "UPDATE Inventory SET QtyInStock = ? WHERE ProdsID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, inventory.getQtyInStock());
            stmt.setInt(2, inventory.getProdsID());
            stmt.executeUpdate();
        }
    }


    public void deleteInventory(int prodsID) throws SQLException {
        String query = "DELETE FROM Inventory WHERE ProdsID = ?";
        try (Connection conn = DataAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, prodsID);
            stmt.executeUpdate();
        }
    }


    public List<Inventory> getAllInventory() throws SQLException {
        List<Inventory> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM Inventory";
        try (Connection conn = DataAdapter.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setProdsID(rs.getInt("ProdsID"));
                inventory.setQtyInStock(rs.getInt("QtyInStock"));
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }
}

