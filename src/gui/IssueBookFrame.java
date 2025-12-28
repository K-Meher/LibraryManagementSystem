package gui;

import db.DBConnection;
import ui.UITheme;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class IssueBookFrame extends JFrame {

    JTextField bookIdField, studentIdField;

    public IssueBookFrame() {
        setTitle("Issue Book");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(UITheme.BACKGROUND_COLOR);

        panel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        bookIdField.setFont(UITheme.LABEL_FONT);
        panel.add(bookIdField);

        panel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        studentIdField.setFont(UITheme.LABEL_FONT);
        panel.add(studentIdField);

        JButton issueBtn = new JButton("Issue Book");
        JButton backBtn = new JButton("Back");

        UITheme.styleButton(issueBtn);
        UITheme.styleButton(backBtn);

        panel.add(issueBtn);
        panel.add(backBtn);

        add(panel);
        setVisible(true);

        issueBtn.addActionListener(e -> issueBook());
        backBtn.addActionListener(e -> dispose());
    }

    private void issueBook() {
        String bookIdText = bookIdField.getText();
        String studentIdText = studentIdField.getText();

        if (bookIdText.isEmpty() || studentIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdText);
            int studentId = Integer.parseInt(studentIdText);

            Connection con = DBConnection.getConnection();

            // Check book quantity
            String checkSql = "SELECT quantity FROM books WHERE book_id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Book not found");
                return;
            }

            int quantity = rs.getInt("quantity");
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Book not available");
                return;
            }

            // Issue book
            String issueSql = "INSERT INTO issued_books(book_id, student_id, issue_date) VALUES (?, ?, CURDATE())";
            PreparedStatement issueStmt = con.prepareStatement(issueSql);
            issueStmt.setInt(1, bookId);
            issueStmt.setInt(2, studentId);
            issueStmt.executeUpdate();

            // Update quantity
            String updateSql = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateSql);
            updateStmt.setInt(1, bookId);
            updateStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book Issued Successfully");

            bookIdField.setText("");
            studentIdField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}