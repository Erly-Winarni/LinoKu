package Gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import Code.User;
import Code.*;

public class LoginPanel extends JPanel {
    private JTextField idField;
    private JPasswordField pwField;
    private JButton loginBtn;
    private JButton registerBtn;
    private JLabel errorLabel;
    private ArrayList<User> users;
    
    public LoginPanel(ArrayList<User> users) {
        this.users = users;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); 
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("Selamat Datang di LinoKu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID Pengguna:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        idField = new JTextField();
        idField.setPreferredSize(new Dimension(220, 30));

        JLabel pwLabel = new JLabel("Kata Sandi:");
        pwLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pwField = new JPasswordField();
        pwField.setPreferredSize(new Dimension(220, 30));

        loginBtn = new JButton("Login");
        registerBtn = new JButton("Register");

        styleButton(loginBtn, new Color(250, 0,0)); 
        styleButton(registerBtn, new Color(60, 179, 113)); 

        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        errorLabel.setForeground(Color.RED);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(pwLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(pwField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginBtn, gbc);

        gbc.gridy = 3;
        formPanel.add(registerBtn, gbc);

        gbc.gridy = 4;
        formPanel.add(errorLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> showRegisterPanel());
    }

    private void login() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword()).trim();
        for (User u : users) {
            if (u.login(id, pw)) {
                if (u.getRole() instanceof Admin) {
                    new AdminGUI((Admin) u.getRole()).setVisible(true);
                } else if (u.getRole() instanceof Produsen) {
                    new ProdusenGUI(u.getId(), (Produsen) u.getRole()).setVisible(true);
                }
                SwingUtilities.getWindowAncestor(this).dispose(); 
                return;
            }
        }
        errorLabel.setText("Login gagal. ID atau password salah.");
    }

    private void showRegisterPanel() {
        new RegisterPanel(users).setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(10, 24, 10, 24)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }
}
