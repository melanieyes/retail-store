package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {
    private JTextField txtEmployeeName;
    private JTextField txtEmployeeID;
    private JButton btnAddEmployee;
    private JButton btnEditEmployee;
    private JButton btnDeleteEmployee;
    private JButton btnViewEmployees;

    public EmployeeView() {
        // Setting up the frame
        setTitle("Employee Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Initializing components
        JLabel lblEmployeeName = new JLabel("Employee Name:");
        txtEmployeeName = new JTextField();
        JLabel lblEmployeeID = new JLabel("Employee ID:");
        txtEmployeeID = new JTextField();
        btnAddEmployee = new JButton("Add Employee");
        btnEditEmployee = new JButton("Edit Employee");
        btnDeleteEmployee = new JButton("Delete Employee");
        btnViewEmployees = new JButton("View All Employees");

        // Adding components to the frame
        add(lblEmployeeName);
        add(txtEmployeeName);
        add(lblEmployeeID);
        add(txtEmployeeID);
        add(btnAddEmployee);
        add(btnEditEmployee);
        add(btnDeleteEmployee);
        add(btnViewEmployees);
    }

    // Getters for data fields and buttons
    public String getEmployeeName() {
        return txtEmployeeName.getText();
    }

    public String getEmployeeID() {
        return txtEmployeeID.getText();
    }

    public void addEmployeeListener(ActionListener actionListener) {
        btnAddEmployee.addActionListener(actionListener);
    }

    public void editEmployeeListener(ActionListener actionListener) {
        btnEditEmployee.addActionListener(actionListener);
    }

    public void deleteEmployeeListener(ActionListener actionListener) {
        btnDeleteEmployee.addActionListener(actionListener);
    }

    public void viewEmployeesListener(ActionListener actionListener) {
        btnViewEmployees.addActionListener(actionListener);
    }
}
