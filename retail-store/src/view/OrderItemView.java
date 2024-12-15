package view;

import control.OrderItemController;
import model.OrderItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OrderItemView extends JPanel {
    private final DefaultTableModel tableModel;
    private final OrderItemController orderItemController;
    private JTable orderItemTable;
    private int currentOrderId; // The Order ID for which items are being managed

    // Constructor
    public OrderItemView(int orderId) {
        this.currentOrderId = orderId; // Set the current Order ID
        orderItemController = new OrderItemController();

        setLayout(new BorderLayout());
        setBackground(new Color(0, 139, 139)); // Dark cyan

        // Title label
        JLabel titleLabel = new JLabel("Order Items Management for Order ID: " + orderId, JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Order Item ID", "Product ID", "Quantity", "Unit Price", "Total Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderItemTable = new JTable(tableModel);
        orderItemTable.setBackground(Color.DARK_GRAY);
        orderItemTable.setForeground(Color.WHITE);
        orderItemTable.setFont(new Font("Monospaced", Font.PLAIN, 14));
        orderItemTable.getTableHeader().setBackground(new Color(0, 104, 104));
        orderItemTable.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(orderItemTable), BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 139, 139));
        JButton addButton = createStyledButton("Add Item");
        JButton deleteButton = createStyledButton("Delete Item");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load order items when the panel is initialized
        loadOrderItems();

        // Add item functionality
        addButton.addActionListener(e -> addOrderItem());

        // Delete item functionality
        deleteButton.addActionListener(e -> deleteOrderItem());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        Color buttonColor = new Color(244, 187, 68); // Amber
        Color buttonTextColor = Color.WHITE;
        button.setBackground(buttonColor);
        button.setForeground(buttonTextColor);
        button.setFont(new Font("Dialog", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setOpaque(true);
        return button;
    }

    private void loadOrderItems() {
        try {
            // Clear existing rows in the table
            tableModel.setRowCount(0);

            // Fetch order items for the current Order ID
            List<OrderItem> orderItems = orderItemController.getOrderItems(currentOrderId);
            for (OrderItem item : orderItems) {
                Object[] rowData = {
                        item.getOrdItemID(),
                        item.getProdsID(),
                        item.getQty(),
                        item.getUnitPrice(),
                        item.getTotalPrice()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading order items: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addOrderItem() {
        // Input fields for a new order item
        JTextField productIdField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField unitPriceField = new JTextField();

        Object[] fields = {
                "Product ID:", productIdField,
                "Quantity:", quantityField,
                "Unit Price:", unitPriceField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Order Item", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                double unitPrice = Double.parseDouble(unitPriceField.getText());
                double totalPrice = quantity * unitPrice;

                OrderItem orderItem = new OrderItem();
                orderItem.setOrdID(currentOrderId);
                orderItem.setProdsID(productId);
                orderItem.setQty(quantity);
                orderItem.setUnitPrice(unitPrice);
                orderItem.setTotalPrice(totalPrice);

                orderItemController.addOrderItem(orderItem);
                loadOrderItems(); // Refresh the table
            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error adding order item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void deleteOrderItem() {
        int selectedRow = orderItemTable.getSelectedRow();
        if (selectedRow >= 0) {
            int orderItemId = (int) tableModel.getValueAt(selectedRow, 0); // Get Order Item ID from the selected row
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this order item?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    orderItemController.deleteOrderItem(orderItemId); // Call the controller to delete the item
                    loadOrderItems(); // Refresh the table
                    JOptionPane.showMessageDialog(this, "Order item deleted successfully.");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting order item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order item to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
