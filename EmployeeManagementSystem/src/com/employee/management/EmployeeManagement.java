
package com.employee.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class EmployeeManagement extends JFrame {

    private JTextField idField, nameField, departmentField, salaryField;

    private JButton submitButton, viewButton, clearButton;

    private final Color BACKGROUND = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color BLUE = new Color(59, 130, 246);
    private final Color GREEN = new Color(16, 185, 129);
    private final Color LIGHT = new Color(226, 232, 240);

    public EmployeeManagement() {

        setTitle("EMS | Employee Management");
        setSize(1050, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBackground(BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel headingBox = new JPanel();
        headingBox.setLayout(new BoxLayout(headingBox, BoxLayout.Y_AXIS));
        headingBox.setOpaque(false);

        JLabel title = new JLabel("EMPLOYEE MANAGEMENT");
        title.setFont(new Font("Segoe UI", Font.BOLD, 29));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage your workforce in one place");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(148, 163, 184));

        headingBox.add(title);
        headingBox.add(Box.createVerticalStrut(6));
        headingBox.add(subtitle);

        JLabel logo = new JLabel("EMS  ✦");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(new Color(96, 165, 250));

        header.add(headingBox, BorderLayout.WEST);
        header.add(logo, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // MAIN CONTENT
        JPanel content = new JPanel(new GridLayout(1, 2, 22, 0));
        content.setOpaque(false);

        // EMPLOYEE FORM CARD
        RoundedPanel formCard = new RoundedPanel(25, CARD);
        formCard.setLayout(new BorderLayout(15, 20));
        formCard.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel formHeading = new JPanel();
        formHeading.setLayout(new BoxLayout(formHeading, BoxLayout.Y_AXIS));
        formHeading.setOpaque(false);

        JLabel formTitle = new JLabel("Add New Employee");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 21));
        formTitle.setForeground(Color.WHITE);

        JLabel formSubtitle = new JLabel("Enter employee information below");
        formSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formSubtitle.setForeground(new Color(148, 163, 184));

        formHeading.add(formTitle);
        formHeading.add(Box.createVerticalStrut(7));
        formHeading.add(formSubtitle);

        formCard.add(formHeading, BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridLayout(4, 1, 0, 13));
        fields.setOpaque(false);

        idField = new JTextField();
        nameField = new JTextField();
        departmentField = new JTextField();
        salaryField = new JTextField();

        addField(fields, "EMPLOYEE ID", idField);
        addField(fields, "FULL NAME", nameField);
        addField(fields, "DEPARTMENT", departmentField);
        addField(fields, "SALARY", salaryField);

        formCard.add(fields, BorderLayout.CENTER);

        // WORKSPACE CARD
        RoundedPanel infoCard = new RoundedPanel(
                25, new Color(37, 52, 76)
        );

        infoCard.setLayout(new BorderLayout(15, 20));
        infoCard.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        JLabel infoTitle = new JLabel("Your Workspace");
        infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 23));
        infoTitle.setForeground(Color.WHITE);

        JLabel infoSubtitle = new JLabel(
                "<html>Keep your employee records<br>organized and accessible.</html>"
        );

        infoSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoSubtitle.setForeground(new Color(203, 213, 225));

        JPanel infoTop = new JPanel();
        infoTop.setOpaque(false);
        infoTop.setLayout(new BoxLayout(infoTop, BoxLayout.Y_AXIS));

        infoTop.add(infoTitle);
        infoTop.add(Box.createVerticalStrut(12));
        infoTop.add(infoSubtitle);

        JPanel featureList = new JPanel();
        featureList.setOpaque(false);
        featureList.setLayout(new BoxLayout(featureList, BoxLayout.Y_AXIS));

        addFeature(featureList, "✦  Secure database storage");
        addFeature(featureList, "✦  Easy employee registration");
        addFeature(featureList, "✦  View all employee records");
        addFeature(featureList, "✦  Organized employee data");

        JLabel databaseStatus = new JLabel("●  DATABASE SYSTEM");
        databaseStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        databaseStatus.setForeground(GREEN);

        infoCard.add(infoTop, BorderLayout.NORTH);
        infoCard.add(featureList, BorderLayout.CENTER);
        infoCard.add(databaseStatus, BorderLayout.SOUTH);

        content.add(formCard);
        content.add(infoCard);

        root.add(content, BorderLayout.CENTER);

        // BUTTONS
        JPanel bottom = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 0)
        );

        bottom.setOpaque(false);

        submitButton = createButton("＋  Submit Employee", BLUE);
        viewButton = createButton("▤  View Employees", GREEN);
        clearButton = createButton("↺  Clear Form", new Color(100, 116, 139));

        bottom.add(submitButton);
        bottom.add(viewButton);
        bottom.add(clearButton);

        root.add(bottom, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> addEmployee());
        viewButton.addActionListener(e -> viewEmployees());
        clearButton.addActionListener(e -> clearForm());

        add(root);
    }

    // ROUNDED PANEL
    static class RoundedPanel extends JPanel {

        private final int radius;
        private final Color color;

        RoundedPanel(int radius, Color color) {
            this.radius = radius;
            this.color = color;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(),
                    radius, radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // FORM FIELDS
    private void addField(
            JPanel panel,
            String labelText,
            JTextField field
    ) {

        JPanel box = new JPanel(new BorderLayout(0, 7));
        box.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(new Color(148, 163, 184));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBackground(new Color(51, 65, 85));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(71, 85, 105)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        field.setPreferredSize(new Dimension(200, 42));

        box.add(label, BorderLayout.NORTH);
        box.add(field, BorderLayout.CENTER);

        panel.add(box);
    }

    // FEATURE LABELS
    private void addFeature(JPanel panel, String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(LIGHT);

        label.setBorder(
                BorderFactory.createEmptyBorder(12, 0, 12, 0)
        );

        panel.add(label);
    }

    // BUTTON DESIGN
    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 48));

        button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    // SUBMIT EMPLOYEE
    private void addEmployee() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        String salaryText = salaryField.getText().trim();

        if (id.isEmpty() || name.isEmpty()
                || department.isEmpty() || salaryText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this, "Please fill in all fields!"
            );
            return;
        }

        try {

            int empId = Integer.parseInt(id);
            double salary = Double.parseDouble(salaryText);

            if (salary < 0) {
                JOptionPane.showMessageDialog(
                        this, "Salary cannot be negative!"
                );
                return;
            }

            String sql = "INSERT INTO employees "
                    + "(emp_id, name, department, salary) "
                    + "VALUES (?, ?, ?, ?)";

            Connection con = DBConnection.getConnection();

            if (con == null) {
                JOptionPane.showMessageDialog(
                        this, "Database connection failed!"
                );
                return;
            }

            try (Connection connection = con;
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, empId);
                ps.setString(2, name);
                ps.setString(3, department);
                ps.setDouble(4, salary);

                ps.executeUpdate();

                showSuccessPopup(name, empId, department, salary);

                clearForm();
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this, "Enter a valid Employee ID and Salary!"
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this, "Database Error: " + ex.getMessage()
            );
        }
    }

    // CUSTOM SUCCESS POPUP
    private void showSuccessPopup(
            String name, int empId, String department, double salary
    ) {

        JDialog dialog = new JDialog(this, "Success", true);

        dialog.setSize(450, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(15, 23, 42));
        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("✓");
        icon.setFont(new Font("Arial", Font.BOLD, 60));
        icon.setForeground(new Color(52, 211, 153));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heading = new JLabel("Employee Saved!");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Your employee record was added successfully."
        );

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(148, 163, 184));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel details = new JPanel(new GridLayout(4, 1, 5, 5));
        details.setBackground(new Color(30, 41, 59));
        details.setBorder(
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        );

        addDetail(details, "Employee ID:  " + empId);
        addDetail(details, "Name:  " + name);
        addDetail(details, "Department:  " + department);
        addDetail(details, "Salary:  " + String.format("%.2f", salary));

        JButton done = new JButton("DONE");
        done.setFont(new Font("Segoe UI", Font.BOLD, 14));
        done.setForeground(Color.WHITE);
        done.setBackground(new Color(16, 185, 129));
        done.setFocusPainted(false);
        done.setBorderPainted(false);
        done.setAlignmentX(Component.CENTER_ALIGNMENT);
        done.setMaximumSize(new Dimension(180, 43));
        done.setCursor(new Cursor(Cursor.HAND_CURSOR));

        done.addActionListener(e -> dialog.dispose());

        panel.add(icon);
        panel.add(Box.createVerticalStrut(5));
        panel.add(heading);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(22));
        panel.add(details);
        panel.add(Box.createVerticalStrut(22));
        panel.add(done);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // SUCCESS POPUP DETAILS
    private void addDetail(JPanel panel, String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(Color.WHITE);

        panel.add(label);
    }

    // VIEW EMPLOYEES
    private void viewEmployees() {

        String[] columns = {
                "Employee ID", "Name", "Department", "Salary"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = "SELECT * FROM employees";

        Connection con = DBConnection.getConnection();

        if (con == null) {
            JOptionPane.showMessageDialog(
                    this, "Database connection failed!"
            );
            return;
        }

        try (Connection connection = con;
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                });
            }

            JTable table = new JTable(model);

            table.setRowHeight(35);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.setGridColor(new Color(226, 232, 240));

            JTableHeader tableHeader = table.getTableHeader();

            tableHeader.setFont(
                    new Font("Segoe UI", Font.BOLD, 14)
            );

            tableHeader.setBackground(new Color(30, 41, 59));
            tableHeader.setForeground(Color.WHITE);
            tableHeader.setPreferredSize(new Dimension(0, 40));

            JFrame tableFrame = new JFrame("Employee Records");

            tableFrame.setSize(800, 450);
            tableFrame.setLocationRelativeTo(this);
            tableFrame.add(new JScrollPane(table));
            tableFrame.setVisible(true);

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this, "Unable to fetch records: " + ex.getMessage()
            );
        }
    }

    // CLEAR FORM
    private void clearForm() {

        idField.setText("");
        nameField.setText("");
        departmentField.setText("");
        salaryField.setText("");

        idField.requestFocus();
    }

    // MAIN METHOD
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            EmployeeManagement app = new EmployeeManagement();
            app.setVisible(true);

        });
    }
}
