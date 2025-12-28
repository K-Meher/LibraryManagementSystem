package gui;

import db.DBConnection;
import ui.UITheme;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Library Login");
        setSize(300, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(UITheme.BACKGROUND_COLOR);

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        usernameField.setFont(UITheme.LABEL_FONT);
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setFont(UITheme.LABEL_FONT);
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");

        UITheme.styleButton(loginBtn);
        UITheme.styleButton(exitBtn);

        panel.add(loginBtn);
        panel.add(exitBtn);

        add(panel);
        setVisible(true);

        loginBtn.setBackground(UITheme.PRIMARY_COLOR);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(UITheme.BUTTON_FONT);
        loginBtn.addActionListener(e -> login());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void login() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                dispose(); // close login
                new MainMenu(); // open main menu
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}