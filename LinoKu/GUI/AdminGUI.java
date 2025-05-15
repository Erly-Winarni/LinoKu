package Gui;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;
import Code.Admin;
import Code.Produk;
import Gui.AdminGUI;
import Main.LinoKuApp;

public class AdminGUI extends JFrame {
    private Admin admin;
    private JTable produkKonfirmasiTable;
    private JTable distribusiTable;
    private JTable laporanTable;
    private JLabel roleInfoLabel;

    public AdminGUI(Admin paraAdmin) {
        this.admin = paraAdmin;

        setTitle("📦 Admin Dashboard - LinoKu");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255));

        Font fontRegular = new Font("SansSerif", Font.PLAIN, 13);

        produkKonfirmasiTable = new JTable(new DefaultTableModel(new Object[]{"Nama", "Kategori", "Harga", "Stok"}, 0));
        produkKonfirmasiTable.setFont(fontRegular);
        produkKonfirmasiTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane produkKonfirmasiScroll = new JScrollPane(produkKonfirmasiTable);
        produkKonfirmasiScroll.setBorder(BorderFactory.createTitledBorder("Produk Konfirmasi"));
        produkKonfirmasiScroll.setPreferredSize(new Dimension(300, 350));

        distribusiTable = new JTable(new DefaultTableModel(new Object[]{"Nama", "Kategori", "Harga", "Stok"}, 0));
        distribusiTable.setFont(fontRegular);
        distribusiTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane distribusiScroll = new JScrollPane(distribusiTable);
        distribusiScroll.setBorder(BorderFactory.createTitledBorder("Distribusi"));
        distribusiScroll.setPreferredSize(new Dimension(300, 350));

        laporanTable = new JTable(new DefaultTableModel(
            new Object[]{"Nama", "Kategori", "Harga", "Stok", "Tujuan", "Biaya"}, 0));
        laporanTable.setFont(fontRegular);
        laporanTable.setEnabled(false);
        JScrollPane laporanScroll = new JScrollPane(laporanTable);
        laporanScroll.setBorder(BorderFactory.createTitledBorder("Laporan"));
        laporanScroll.setPreferredSize(new Dimension(400, 350));

        roleInfoLabel = new JLabel("Role Info: " + admin.getRoleInfo());
        roleInfoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(roleInfoLabel, BorderLayout.NORTH);

        JButton pindahkanButton = createStyledButton("Pindahkan", new Color(70, 130, 180));
        pindahkanButton.addActionListener(e -> {
            int[] selectedIndices = produkKonfirmasiTable.getSelectedRows();
            if (selectedIndices.length == 0) {
                JOptionPane.showMessageDialog(this, "Silakan pilih produk konfirmasi untuk dipindahkan.");
                return;
            }

            List<Integer> indicesToMove = new ArrayList<>();
            for (int idx : selectedIndices) indicesToMove.add(idx);

            admin.pindahkanKeDistribusi(indicesToMove);
            updateProdukKonfirmasi();
            updateDistribusi();
            updateLaporan();
        });

        JButton distribusikanButton = createStyledButton("Distribusikan", new Color(70, 130, 180));
        distribusikanButton.addActionListener(e -> {
            int[] selectedRows = distribusiTable.getSelectedRows();
            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(this, "Silakan pilih satu atau lebih produk untuk didistribusikan.");
                return;
            }

            List<Produk> selectedProdukList = new ArrayList<>();
            for (int row : selectedRows) {
                selectedProdukList.add(admin.getDistribusi().get(row));
            }

            List<String> pasarList = admin.getPasarList();
            JComboBox<String> pasarComboBox = new JComboBox<>(pasarList.toArray(new String[0]));

            JPanel panel = new JPanel();
            panel.add(new JLabel("Pilih Pasar Tujuan:"));
            panel.add(pasarComboBox);

            int option = JOptionPane.showConfirmDialog(this, panel, "Pilih Tujuan Distribusi",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String selectedPasar = (String) pasarComboBox.getSelectedItem();
                double biaya = admin.getBiayaPasar(selectedPasar);

                String message = "Tujuan: " + selectedPasar +
                        "\nBiaya Distribusi per produk: Rp" + String.format("%,.0f", biaya) +
                        "\nJumlah produk: " + selectedProdukList.size();

                int confirm = JOptionPane.showConfirmDialog(this, message,
                        "Konfirmasi Biaya Distribusi", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    for (Produk produk : selectedProdukList) {
                        admin.distribusikanProduk(produk, selectedPasar, biaya);
                    }
                    updateDistribusi();
                    updateLaporan();
                }
            }
        });

        JButton logoutButton = createStyledButton("Logout", new Color(220, 53, 69));
        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                JFrame loginFrame = new JFrame("LinoKu");
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setSize(400, 300);
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setContentPane(new LoginPanel(LinoKuApp.getUsers()));
                loginFrame.setVisible(true);
            });
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(pindahkanButton);
        buttonPanel.add(distribusikanButton);
        buttonPanel.add(logoutButton);

        add(produkKonfirmasiScroll, BorderLayout.WEST);
        add(distribusiScroll, BorderLayout.CENTER);
        add(laporanScroll, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        updateProdukKonfirmasi();
        updateDistribusi();
        updateLaporan();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
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

        return button;
    }

    private void updateProdukKonfirmasi() {
        admin.muatUlangProdukKonfirmasi();
        DefaultTableModel model = (DefaultTableModel) produkKonfirmasiTable.getModel();
        model.setRowCount(0);
        for (Produk p : admin.getProdukKonfirmasi()) {
            model.addRow(new Object[]{p.getNama(), p.getJenis(), p.getHarga(), p.getJumlah()});
        }
    }

    private void updateDistribusi() {
        DefaultTableModel model = (DefaultTableModel) distribusiTable.getModel();
        model.setRowCount(0);
        for (Produk p : admin.getDistribusi()) {
            model.addRow(new Object[]{p.getNama(), p.getJenis(), p.getHarga(), p.getJumlah()});
        }
    }

    private void updateLaporan() {
        DefaultTableModel model = (DefaultTableModel) laporanTable.getModel();
        model.setRowCount(0);
        for (String l : admin.getLaporan()) {
            String[] parts = l.split(";");
            if (parts.length == 6) {
                model.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]});
            } else {
                model.addRow(new Object[]{l, "", "", "", "", ""});
            }
        }
    }
}
