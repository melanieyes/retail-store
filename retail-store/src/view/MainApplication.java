package view;

import javax.swing.*;
import java.awt.*;

public class MainApplication extends JFrame {

    private final String userRole;
    private JPanel contentPanel;

    public MainApplication(String userRole) {
        this.userRole = userRole;

        setTitle("Retail Store System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Setup Sidebar for Admin Dashboard
        if ("admin".equalsIgnoreCase(userRole)) {
            add(createAdminSidebar(), BorderLayout.WEST);
            contentPanel = new JPanel(new CardLayout());
            add(contentPanel, BorderLayout.CENTER);
        }
        // Setup Buttons for Cashier Dashboard
        else if ("cashier".equalsIgnoreCase(userRole)) {
            add(createCashierDashboard(), BorderLayout.CENTER);
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Admin Sidebar
    private JPanel createAdminSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(10, 1, 5, 5));
        sidebar.setBackground(new Color(0, 139, 139));

        // Add sidebar buttons
        addSidebarButton(sidebar, "Manage Users", this::showManageUserView);
        addSidebarButton(sidebar, "Customer Management", this::showCustomerView);
        addSidebarButton(sidebar, "Product Management", this::showProductView);
        addSidebarButton(sidebar, "Order Management", this::showOrderView);
        addSidebarButton(sidebar, "Order Item Management", this::showOrderItemView);
        addSidebarButton(sidebar, "Product Category", this::showProductCategoryView);
        addSidebarButton(sidebar, "Supplier Management", this::showSupplierView);
        addSidebarButton(sidebar, "Supplier Order Management", this::showSupplierOrderView);
        addSidebarButton(sidebar, "Inventory Management", this::showInventoryView);

        return sidebar;
    }

    private void addSidebarButton(JPanel sidebar, String text, Runnable onClickAction) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 104, 104));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Dialog", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.addActionListener(e -> onClickAction.run());
        sidebar.add(button);
    }

    // Cashier Dashboard:
    private JPanel createCashierDashboard() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(new Color(0, 139, 139));

        buttonPanel.add(createButton("Customer Management", this::showCustomerView));
        buttonPanel.add(createButton("Order Management", this::showOrderView));
        buttonPanel.add(createButton("Inventory Management", this::showInventoryView));

        return buttonPanel;
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Dialog", Font.BOLD, 18));
        button.setBackground(new Color(0, 104, 104));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.addActionListener(e -> action.run());
        return button;
    }

    // View Methods
    private void showManageUserView() {
        openView(new ManageUsersView(), "Manage Users");
    }

    private void showCustomerView() {
        openView(new CustomerView(), "Customer Management");
    }

    private void showProductView() {
        openView(new ProductView(), "Product Management");
    }

    private void showOrderView() {
        openView(new OrderView(), "Order Management");
    }

    private void showOrderItemView() {
        String input = JOptionPane.showInputDialog(this, "Enter Order ID:", "Order Item Management", JOptionPane.QUESTION_MESSAGE);
        try {
            int orderId = Integer.parseInt(input);
            openView(new OrderItemView(orderId), "Order Item Management");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Order ID. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showProductCategoryView() {
        openView(new ProdCategoryView(), "Product Categories");
    }

    private void showSupplierView() {
        openView(new SupplierView(), "Supplier Management");
    }

    private void showSupplierOrderView() {
        openView(new SupplierOrderView(), "Supplier Order Management");
    }

    private void showInventoryView() {
        openView(new InventoryView(), "Inventory Management");
    }

    // Helper to open views in CardLayout for Admin
    private void openView(JPanel panel, String title) {
        if (contentPanel != null) {
            contentPanel.removeAll();
            contentPanel.add(panel, title);
            contentPanel.revalidate();
            contentPanel.repaint();
        } else {
            JFrame frame = new JFrame(title);
            frame.setContentPane(panel);
            frame.setSize(600, 400);
            frame.setVisible(true);
        }
    }
}
