package view;

import control.OrderController;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class OrderView extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable orderTable;
    private final OrderController orderController;

    public OrderView() {
        orderController = new OrderController();

        setLayout(new BorderLayout());
        setBackground(new Color(0, 139, 139));

        // Title label
        JLabel titleLabel = new JLabel("Order Management", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Order ID", "Customer ID", "Order Date", "Status ID", "Total Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setBackground(Color.DARK_GRAY);
        orderTable.setForeground(Color.WHITE);
        orderTable.setFont(new Font("Monospaced", Font.PLAIN, 14));
        orderTable.getTableHeader().setBackground(new Color(0, 104, 104));
        orderTable.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(); // Default FlowLayout
        buttonPanel.setBackground(new Color(0, 139, 139));

        JButton addButton = createStyledButton("Add Order");
        JButton deleteButton = createStyledButton("Delete Order");
        JButton updateButton = createStyledButton("Update Order");
        JButton completeButton = createStyledButton("Complete Purchase");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(completeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Orders
        loadOrders();

        // Button Actions
        addButton.addActionListener(e -> addOrder());
        deleteButton.addActionListener(e -> deleteOrder());
        updateButton.addActionListener(e -> updateOrder());
        completeButton.addActionListener(e -> completePurchase());
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

    private void loadOrders() {
        try {
            List<Order> orders = orderController.getAllOrders();
            tableModel.setRowCount(0);
            for (Order order : orders) {
                tableModel.addRow(new Object[]{
                        order.getOrdID(),
                        order.getCustID(),
                        order.getOrdDate(),
                        order.getStatusID(),
                        order.getTotAmt()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOrder() {
        JTextField custIDField = new JTextField();
        JTextField orderDateField = new JTextField();
        JTextField statusIDField = new JTextField();
        JTextField totalAmtField = new JTextField();

        Object[] fields = {
                "Customer ID:", custIDField,
                "Order Date (YYYY-MM-DD):", orderDateField,
                "Status ID:", statusIDField,
                "Total Amount:", totalAmtField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Order", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Order order = new Order();
                order.setCustID(Integer.parseInt(custIDField.getText()));
                order.setOrdDate(Date.valueOf(orderDateField.getText()));
                order.setStatusID(Integer.parseInt(statusIDField.getText()));
                order.setTotAmt(Double.parseDouble(totalAmtField.getText()));

                orderController.addOrder(order);
                loadOrders(); // Refresh table
                JOptionPane.showMessageDialog(this, "Order added successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                orderController.deleteOrder(orderId);
                loadOrders();
                JOptionPane.showMessageDialog(this, "Order deleted successfully.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (int) tableModel.getValueAt(selectedRow, 0);

            JTextField custIDField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
            JTextField orderDateField = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
            JTextField statusIDField = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
            JTextField totalAmtField = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());

            Object[] fields = {
                    "Customer ID:", custIDField,
                    "Order Date (YYYY-MM-DD):", orderDateField,
                    "Status ID:", statusIDField,
                    "Total Amount:", totalAmtField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Update Order", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Order order = new Order();
                    order.setOrdID(orderId);
                    order.setCustID(Integer.parseInt(custIDField.getText()));
                    order.setOrdDate(Date.valueOf(orderDateField.getText()));
                    order.setStatusID(Integer.parseInt(statusIDField.getText()));
                    order.setTotAmt(Double.parseDouble(totalAmtField.getText()));

                    orderController.updateOrder(order);
                    loadOrders();
                    JOptionPane.showMessageDialog(this, "Order updated successfully.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void completePurchase() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String[] paymentMethods = {"Apple Pay", "QR", "Cash"};
            String method = (String) JOptionPane.showInputDialog(this, "Select Payment Method:", "Complete Purchase",
                    JOptionPane.QUESTION_MESSAGE, null, paymentMethods, paymentMethods[0]);

            if (method != null) {
                deleteOrder();
                JOptionPane.showMessageDialog(this, "Payment successful using " + method + "!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to complete the purchase.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
