package view;

import control.InventoryController;
import model.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class InventoryView extends JPanel {
    private final DefaultTableModel tableModel;
    private final InventoryController inventoryController;
    private JTable inventoryTable;

    public InventoryView() {
        inventoryController = new InventoryController();

        setLayout(new BorderLayout());
        setBackground(new Color(0, 139, 139)); // Dark Cyan

        // Title
        JLabel titleLabel = new JLabel("Inventory Management", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Product ID", "Quantity In Stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(tableModel);
        inventoryTable.setBackground(Color.DARK_GRAY);
        inventoryTable.setForeground(Color.WHITE);
        inventoryTable.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inventoryTable.getTableHeader().setBackground(new Color(0, 104, 104));
        inventoryTable.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 139, 139));
        JButton addButton = createStyledButton("Add Inventory");
        JButton updateButton = createStyledButton("Update Quantity");
        JButton deleteButton = createStyledButton("Delete Inventory");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        loadInventory();

        // Button Actions
        addButton.addActionListener(e -> addInventory());
        updateButton.addActionListener(e -> updateInventory());
        deleteButton.addActionListener(e -> deleteInventory());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(244, 187, 68)); // Amber
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Dialog", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setOpaque(true);
        return button;
    }

    private void loadInventory() {
        try {
            tableModel.setRowCount(0); // Clear table
            List<Inventory> inventoryList = inventoryController.getAllInventory();
            for (Inventory inventory : inventoryList) {
                Object[] rowData = {inventory.getProdsID(), inventory.getQtyInStock()};
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading inventory: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addInventory() {
        JTextField productIDField = new JTextField();
        JTextField quantityField = new JTextField();

        Object[] fields = {"Product ID:", productIDField, "Quantity In Stock:", quantityField};

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Inventory", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Inventory inventory = new Inventory();
                inventory.setProdsID(Integer.parseInt(productIDField.getText()));
                inventory.setQtyInStock(Integer.parseInt(quantityField.getText()));
                inventoryController.addInventory(inventory);
                loadInventory();
            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error adding inventory: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateInventory() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            int prodsID = (int) tableModel.getValueAt(selectedRow, 0);

            JTextField quantityField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
            Object[] fields = {"New Quantity In Stock:", quantityField};

            int option = JOptionPane.showConfirmDialog(this, fields, "Update Quantity", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Inventory inventory = new Inventory();
                    inventory.setProdsID(prodsID);
                    inventory.setQtyInStock(Integer.parseInt(quantityField.getText()));
                    inventoryController.updateInventory(inventory);
                    loadInventory();
                } catch (SQLException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error updating inventory: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an inventory row to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteInventory() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            int prodsID = (int) tableModel.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this inventory?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    inventoryController.deleteInventory(prodsID);
                    loadInventory();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting inventory: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an inventory row to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}

