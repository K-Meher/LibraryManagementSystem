package gui;

import javax.swing.*;
import java.awt.*;
import gui.AddBookFrame;
import gui.IssueBookFrame;
import gui.AddStudentFrame;
import gui.ViewBooksFrame;
import gui.ReturnBookFrame;
import gui.ViewIssuedBooksFrame;
import ui.UITheme;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Library Management System");
        setSize(400, 400);
        setLocationRelativeTo(null); // center screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(UITheme.BACKGROUND_COLOR);

        // Title
        JLabel title = new JLabel("Library Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(UITheme.PRIMARY_COLOR);

        // Buttons
        JButton addBookBtn = new JButton("Add Book");
        JButton addStudentBtn = new JButton("Add Student");
        JButton issueBookBtn = new JButton("Issue Book");
        JButton viewIssuedBooksBtn = new JButton("View Issued Books");
        JButton returnBookBtn = new JButton("Return Book");
        JButton viewBooksBtn = new JButton("View Books");
        JButton exitBtn = new JButton("Exit");
        UITheme.styleButton(addBookBtn);
        UITheme.styleButton(addStudentBtn);
        UITheme.styleButton(issueBookBtn);
        UITheme.styleButton(viewBooksBtn);
        UITheme.styleButton(viewIssuedBooksBtn);
        UITheme.styleButton(returnBookBtn);
        UITheme.styleButton(exitBtn);

        addBookBtn.addActionListener(e -> new AddBookFrame());
        addStudentBtn.addActionListener(e -> new AddStudentFrame());
        issueBookBtn.addActionListener(e -> new IssueBookFrame());
        viewIssuedBooksBtn.addActionListener(e -> new ViewIssuedBooksFrame());
        returnBookBtn.addActionListener(e -> new ReturnBookFrame());
        viewBooksBtn.addActionListener(e -> new ViewBooksFrame());

        // Add components
        panel.add(title);
        panel.add(addBookBtn);
        panel.add(addStudentBtn);
        panel.add(issueBookBtn);
        panel.add(viewIssuedBooksBtn);
        panel.add(returnBookBtn);
        panel.add(viewBooksBtn);
        panel.add(exitBtn);


        add(panel);
        setVisible(true);

        // Exit button action
        exitBtn.addActionListener(e -> System.exit(0));
    }
}