package view;

import javax.swing.*;
import java.awt.*;

public class CashierDashboard extends JFrame {
    public CashierDashboard() {
        setTitle("Cashier Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        add(createStyledButton("View Customers"));
        add(createStyledButton("Complete Purchase"));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(244, 187, 68)); // Amber
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Dialog", Font.BOLD, 14));
        return button;
    }
}
