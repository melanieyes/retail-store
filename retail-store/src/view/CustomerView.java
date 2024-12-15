package view;

import control.CustomerController;
import model.Customers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CustomerView extends JPanel {
    private final DefaultTableModel tableModel;
    private final CustomerController customerController;
    private JTable customerTable;

    public CustomerView() {
        customerController = new CustomerController();

        setLayout(new BorderLayout());
        setBackground(new Color(0, 139, 139));

        // Title label
        JLabel titleLabel = new JLabel("Customer Management", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Customer ID", "First Name", "Last Name", "Email", "Phone", "Address", "Join Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        customerTable.setBackground(Color.DARK_GRAY);
        customerTable.setForeground(Color.WHITE);
        customerTable.setFont(new Font("Monospaced", Font.PLAIN, 14));
        customerTable.getTableHeader().setBackground(new Color(0, 104, 104));
        customerTable.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(customerTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 139, 139));
        JButton addButton = createStyledButton("Add Customer");
        JButton deleteButton = createStyledButton("Delete Customer");
        JButton updateButton = createStyledButton("Update Customer");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        loadCustomers();

        // Add customer functionality
        addButton.addActionListener(e -> addCustomer());

        // Delete customer functionality
        deleteButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int customerId = (int) tableModel.getValueAt(selectedRow, 0);
                deleteCustomer(customerId);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Update customer functionality
        updateButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int customerId = (int) tableModel.getValueAt(selectedRow, 0);
                updateCustomer(customerId, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(244, 187, 68));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Dialog", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setOpaque(true);
        return button;
    }

    private void loadCustomers() {
        try {
            tableModel.setRowCount(0); // Clear table
            List<Customers> customers = customerController.getAllCustomers();
            for (Customers customer : customers) {
                Object[] rowData = {
                        customer.getCustID(),
                        customer.getFName(),
                        customer.getLName(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getAddress(),
                        customer.getJoinDate()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCustomer() {
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField joinDateField = new JTextField();

        Object[] fields = {
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Email:", emailField,
                "Phone:", phoneField,
                "Address:", addressField,
                "Join Date (YYYY-MM-DD):", joinDateField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Customers customer = new Customers();
                customer.setFName(firstNameField.getText());
                customer.setLName(lastNameField.getText());
                customer.setEmail(emailField.getText());
                customer.setPhone(phoneField.getText());
                customer.setAddress(addressField.getText());
                customer.setJoinDate(java.sql.Date.valueOf(joinDateField.getText()));
                customerController.addCustomer(customer);
                loadCustomers(); // Refresh table
            } catch (SQLException | IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteCustomer(int customerId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                customerController.deleteCustomer(customerId);
                loadCustomers(); // Refresh table
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateCustomer(int customerId, int selectedRow) {
        String currentFName = (String) tableModel.getValueAt(selectedRow, 1);
        String currentLName = (String) tableModel.getValueAt(selectedRow, 2);
        String currentEmail = (String) tableModel.getValueAt(selectedRow, 3);
        String currentPhone = (String) tableModel.getValueAt(selectedRow, 4);
        String currentAddress = (String) tableModel.getValueAt(selectedRow, 5);
        String currentJoinDate = tableModel.getValueAt(selectedRow, 6).toString();

        JTextField firstNameField = new JTextField(currentFName);
        JTextField lastNameField = new JTextField(currentLName);
        JTextField emailField = new JTextField(currentEmail);
        JTextField phoneField = new JTextField(currentPhone);
        JTextField addressField = new JTextField(currentAddress);
        JTextField joinDateField = new JTextField(currentJoinDate);

        Object[] fields = {
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Email:", emailField,
                "Phone:", phoneField,
                "Address:", addressField,
                "Join Date (YYYY-MM-DD):", joinDateField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Update Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Customers customer = new Customers();
                customer.setCustID(customerId);
                customer.setFName(firstNameField.getText());
                customer.setLName(lastNameField.getText());
                customer.setEmail(emailField.getText());
                customer.setPhone(phoneField.getText());
                customer.setAddress(addressField.getText());
                customer.setJoinDate(java.sql.Date.valueOf(joinDateField.getText()));
                customerController.updateCustomer(customer);
                loadCustomers(); // Refresh table
            } catch (SQLException | IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Error updating customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
