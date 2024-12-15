package view;

import control.SupplierOrderController;
import model.SupplierOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class SupplierOrderView extends JPanel {
    private final DefaultTableModel tableModel;
    private final SupplierOrderController supplierOrderController;
    private JTable supplierOrderTable;

    // Consistent color theme
    private static final Color PRIMARY_COLOR = new Color(0, 139, 139); // Dark cyan
    private static final Color TEXT_COLOR = Color.WHITE;

    public SupplierOrderView() {
        supplierOrderController = new SupplierOrderController();
        setLayout(new BorderLayout());
        setBackground(PRIMARY_COLOR);

        // Title label
        JLabel titleLabel = new JLabel("Supplier Order Management", JLabel.CENTER);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Table for displaying supplier order data
        String[] columnNames = {"Order ID", "Supplier ID", "Order Date", "Total Cost", "Status ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        supplierOrderTable = new JTable(tableModel);
        supplierOrderTable.setBackground(Color.DARK_GRAY);
        supplierOrderTable.setForeground(TEXT_COLOR);
        supplierOrderTable.setFont(new Font("Monospaced", Font.PLAIN, 14));
        supplierOrderTable.getTableHeader().setBackground(PRIMARY_COLOR);
        supplierOrderTable.getTableHeader().setForeground(TEXT_COLOR);
        add(new JScrollPane(supplierOrderTable), BorderLayout.CENTER);

        // Buttons for supplier order actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(PRIMARY_COLOR);
        JButton addButton = createStyledButton("Add Supplier Order");
        JButton updateButton = createStyledButton("Update Supplier Order");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load supplier orders when the panel is initialized
        loadSupplierOrders();

        // Add supplier order functionality
        addButton.addActionListener(e -> addSupplierOrder());

        // Update supplier order functionality
        updateButton.addActionListener(e -> {
            int selectedRow = supplierOrderTable.getSelectedRow();
            if (selectedRow >= 0) {
                int orderId = (int) tableModel.getValueAt(selectedRow, 0); // Get Order ID
                updateSupplierOrder(orderId, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a supplier order to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        Color buttonColor = new Color(244, 187, 68);
        Color buttonTextColor = Color.WHITE;
        button.setBackground(buttonColor);
        button.setForeground(buttonTextColor);
        button.setFont(new Font("Dialog", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setOpaque(true);
        return button;
    }

    private void loadSupplierOrders() {
        try {
            List<SupplierOrder> supplierOrders = supplierOrderController.getAllSupplierOrders();

            // Clear existing rows in the table
            tableModel.setRowCount(0);

            // Populate the table with supplier order data
            for (SupplierOrder supplierOrder : supplierOrders) {
                Object[] rowData = {
                        supplierOrder.getSuppOrdID(),
                        supplierOrder.getSuppID(),
                        supplierOrder.getOrdDate(),
                        supplierOrder.getTotCost(),
                        supplierOrder.getStatusID()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading supplier orders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSupplierOrder() {
        JTextField supplierIDField = new JTextField();
        JTextField orderDateField = new JTextField();
        JTextField totalCostField = new JTextField();
        JTextField statusIDField = new JTextField();

        Object[] fields = {
                "Supplier ID:", supplierIDField,
                "Order Date (YYYY-MM-DD):", orderDateField,
                "Total Cost:", totalCostField,
                "Status ID:", statusIDField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Supplier Order", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                SupplierOrder supplierOrder = new SupplierOrder();
                supplierOrder.setSuppID(Integer.parseInt(supplierIDField.getText()));
                supplierOrder.setOrdDate(Date.valueOf(orderDateField.getText()));
                supplierOrder.setTotCost(Double.parseDouble(totalCostField.getText()));
                supplierOrder.setStatusID(Integer.parseInt(statusIDField.getText()));
                supplierOrderController.addSupplierOrder(supplierOrder);
                loadSupplierOrders(); // Refresh table
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding supplier order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateSupplierOrder(int orderId, int selectedRow) {
        // Get current values from the selected row
        String currentSupplierID = tableModel.getValueAt(selectedRow, 1).toString();
        String currentOrderDate = tableModel.getValueAt(selectedRow, 2).toString();
        String currentTotalCost = tableModel.getValueAt(selectedRow, 3).toString();
        String currentStatusID = tableModel.getValueAt(selectedRow, 4).toString();

        // Input fields pre-filled with current values
        JTextField supplierIDField = new JTextField(currentSupplierID);
        JTextField orderDateField = new JTextField(currentOrderDate);
        JTextField totalCostField = new JTextField(currentTotalCost);
        JTextField statusIDField = new JTextField(currentStatusID);

        Object[] fields = {
                "Supplier ID:", supplierIDField,
                "Order Date (YYYY-MM-DD):", orderDateField,
                "Total Cost:", totalCostField,
                "Status ID:", statusIDField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Update Supplier Order", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                SupplierOrder supplierOrder = new SupplierOrder();
                supplierOrder.setSuppOrdID(orderId);
                supplierOrder.setSuppID(Integer.parseInt(supplierIDField.getText()));
                supplierOrder.setOrdDate(Date.valueOf(orderDateField.getText()));
                supplierOrder.setTotCost(Double.parseDouble(totalCostField.getText()));
                supplierOrder.setStatusID(Integer.parseInt(statusIDField.getText()));

                supplierOrderController.updateSupplierOrder(supplierOrder); // Update in database
                loadSupplierOrders(); // Refresh table
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating supplier order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
