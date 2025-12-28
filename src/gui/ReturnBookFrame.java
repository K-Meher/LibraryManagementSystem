package gui;

import db.DBConnection;
import ui.UITheme;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReturnBookFrame extends JFrame {

    JTextField issueIdField;

    public ReturnBookFrame() {
        setTitle("Return Book");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(UITheme.BACKGROUND_COLOR);

        panel.add(new JLabel("Issue ID:"));
        issueIdField = new JTextField();
        issueIdField.setFont(UITheme.LABEL_FONT);
        panel.add(issueIdField);

        JButton returnBtn = new JButton("Return Book");
        JButton backBtn = new JButton("Back");

        UITheme.styleButton(returnBtn);
        UITheme.styleButton(backBtn);

        panel.add(returnBtn);
        panel.add(backBtn);

        add(panel);
        setVisible(true);

        returnBtn.addActionListener(e -> returnBook());
        backBtn.addActionListener(e -> dispose());
    }

    private void returnBook() {
        String issueIdText = issueIdField.getText();

        if (issueIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Issue ID");
            return;
        }

        try {
            int issueId = Integer.parseInt(issueIdText);
            Connection con = DBConnection.getConnection();

            // Get book_id from issued_books
            String selectSql = "SELECT book_id FROM issued_books WHERE issue_id = ?";
            PreparedStatement selectStmt = con.prepareStatement(selectSql);
            selectStmt.setInt(1, issueId);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Invalid Issue ID");
                return;
            }

            int bookId = rs.getInt("book_id");

            // Delete issue record
            String deleteSql = "DELETE FROM issued_books WHERE issue_id = ?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteSql);
            deleteStmt.setInt(1, issueId);
            deleteStmt.executeUpdate();

            // Increase book quantity
            String updateSql = "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateSql);
            updateStmt.setInt(1, bookId);
            updateStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book Returned Successfully");
            issueIdField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}