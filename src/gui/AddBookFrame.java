package gui;

import db.DBConnection;
import ui.UITheme;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddBookFrame extends JFrame {

    JTextField titleField, authorField, quantityField;

    public AddBookFrame() {
        setTitle("Add Book");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(UITheme.BACKGROUND_COLOR);

        // Labels
        panel.add(new JLabel("Book Title:"));
        titleField = new JTextField();
        titleField.setFont(UITheme.LABEL_FONT);
        titleField.setPreferredSize(new Dimension(200, 30));
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        authorField.setFont(UITheme.LABEL_FONT);
        authorField.setPreferredSize(new Dimension(200, 30));
        panel.add(authorField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        quantityField.setFont(UITheme.LABEL_FONT);
        quantityField.setPreferredSize(new Dimension(200, 30));
        panel.add(quantityField);

        // Buttons
        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("Back");

        UITheme.styleButton(saveBtn);
        UITheme.styleButton(backBtn);

        panel.add(saveBtn);
        panel.add(backBtn);

        add(panel);
        setVisible(true);

        // Button actions
        saveBtn.addActionListener(e -> addBook());
        backBtn.addActionListener(e -> dispose());
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String quantityText = quantityField.getText();

        if (title.isEmpty() || author.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);

            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO books(title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, title);
            pst.setString(2, author);
            pst.setInt(3, quantity);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book Added Successfully");

            titleField.setText("");
            authorField.setText("");
            quantityField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}