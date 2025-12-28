package gui;

import db.DBConnection;
import ui.UITheme;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddStudentFrame extends JFrame {

    JTextField nameField;

    public AddStudentFrame() {
        setTitle("Add Student");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(UITheme.BACKGROUND_COLOR);

        panel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        nameField.setFont(UITheme.LABEL_FONT);
        panel.add(nameField);

        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("Back");

        UITheme.styleButton(saveBtn);
        UITheme.styleButton(backBtn);

        panel.add(saveBtn);
        panel.add(backBtn);

        add(panel);
        setVisible(true);

        saveBtn.addActionListener(e -> addStudent());
        backBtn.addActionListener(e -> dispose());
    }

    private void addStudent() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO students(name) VALUES (?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Added Successfully");
            nameField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}