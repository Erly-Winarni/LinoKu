package Gui;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import Code.*;
import Code.User;
import Gui.RegisterPanel;

public class RegisterPanel extends JFrame {
    private JTextField idField;
    private JPasswordField pwField;
    private JButton regBtn;
    private JButton backBtn;
    private JLabel errorLabel;
    private ArrayList<User> users;

    public RegisterPanel(ArrayList<User> users) {
        this.users = users;

        setTitle("Registrasi Pengguna");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // AliceBlue
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setContentPane(mainPanel);

        JLabel titleLabel = new JLabel("Form Registrasi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID (format: D1234567):");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idLabel.setForeground(new Color(40, 40, 40));

        idField = new JTextField();
        idField.setColumns(15);

        JLabel pwLabel = new JLabel("Password (8 karakter):");
        pwLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pwLabel.setForeground(new Color(40, 40, 40));

        pwField = new JPasswordField();
        pwField.setColumns(15);

        regBtn = new JButton("Daftar");
        backBtn = new JButton("Kembali");

        styleButton(regBtn, new Color(70, 130, 180)); 
        styleButton(backBtn, new Color(60, 179, 113)); 

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

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(regBtn, gbc);

        gbc.gridy = 3;
        formPanel.add(backBtn, gbc);

        gbc.gridy = 4;
        formPanel.add(errorLabel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        pack(); 
        setLocationRelativeTo(null);

        regBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> dispose());
    }

    private void register() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword()).trim();

        if (!id.matches("^D\\d{7}$") || !pw.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8}")) {
            errorLabel.setText("<html>ID harus format D1234567<br>Password 8 karakter kombinasi huruf besar, kecil & angka</html>");
            return;
        }

        for (User u : users) {
            if (u.getId().equals(id)) {
                errorLabel.setText("ID sudah digunakan.");
                return;
            }
        }

        User userBaru = new User(id, pw, new Produsen(id));
        users.add(userBaru);
        simpanUserBaru(userBaru);

        JOptionPane.showMessageDialog(this, "Registrasi berhasil.");
        dispose();
    }

    private void simpanUserBaru(User userBaru) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                pw.println(user.serialize());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan user ke file.");
        }
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
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
