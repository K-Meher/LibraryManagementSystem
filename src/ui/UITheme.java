package ui;

import javax.swing.*;
import java.awt.*;

public class UITheme {

    public static Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public static Color PRIMARY_COLOR = new Color(33, 150, 243); // blue
    public static Color BACKGROUND_COLOR = new Color(245, 245, 245); // light gray
    public static Color BUTTON_TEXT_COLOR = Color.WHITE;

    public static void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFont(BUTTON_FONT);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);              // ⭐ IMPORTANT
        button.setContentAreaFilled(true);   // ⭐ IMPORTANT
    }
}