package Gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.GridLayout;
import Code.Produk;
import Code.Produsen;
import Gui.ProdusenGUI;
import Main.LinoKuApp;
import java.util.ArrayList;
import java.util.List;


public class ProdusenGUI extends JFrame {
    private String produsenId;
    private Produsen produsen;
    private JTable laporanTable;
    private JLabel roleInfoLabel;

    public ProdusenGUI(String produsenId, Produsen produsen) {
        this.produsenId = produsenId;
        this.produsen = produsen;

        setTitle("Dashboard Produsen - LinoKu");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Selamat Datang, Produsen");
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        JPanel tombolPanel = new JPanel();
        tombolPanel.setLayout(new GridLayout(6, 1, 10, 10));
        tombolPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Menu Produk", TitledBorder.CENTER, TitledBorder.TOP));

        JButton tambahProdukBtn = new JButton("Tambah Produk");
        JButton lihatProdukBtn = new JButton("Lihat Produk");
        JButton konfirmasiProdukBtn = new JButton("Konfirmasi Produk");
        JButton editProdukBtn = new JButton("Edit Produk");
        JButton hapusProdukBtn = new JButton("Hapus Produk");
        JButton lihatLaporanBtn = new JButton("Lihat Laporan");
        JButton keluarBtn = new JButton("Keluar");

        styleButton(tambahProdukBtn, new Color(70, 130, 180));      
        styleButton(lihatProdukBtn, new Color(70, 130, 180));       
        styleButton(konfirmasiProdukBtn, new Color(70, 130, 180));   
        styleButton(editProdukBtn, new Color(70, 130, 180));         
        styleButton(hapusProdukBtn, new Color(70, 130, 180));
        styleButton(lihatLaporanBtn, new Color(70, 130, 180));        
        styleButton(keluarBtn, new Color(70, 130, 180));            

        tombolPanel.add(tambahProdukBtn);
        tombolPanel.add(lihatProdukBtn);
        tombolPanel.add(konfirmasiProdukBtn);
        tombolPanel.add(editProdukBtn);
        tombolPanel.add(hapusProdukBtn);
        tombolPanel.add(lihatLaporanBtn);
        tombolPanel.add(keluarBtn);


        tambahProdukBtn.addActionListener(e -> tambahProduk());
        lihatProdukBtn.addActionListener(e -> lihatProduk());
        konfirmasiProdukBtn.addActionListener(e -> konfirmasiProduk());
        editProdukBtn.addActionListener(e -> editProduk());
        hapusProdukBtn.addActionListener(e -> hapusProduk());
        lihatLaporanBtn.addActionListener(e -> lihatLaporan());
        keluarBtn.addActionListener(e -> keluar());

        add(tombolPanel, BorderLayout.CENTER);

        roleInfoLabel = new JLabel("Role Info: " + produsen.getRoleInfo());
        roleInfoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(roleInfoLabel, BorderLayout.NORTH);

        JLabel footer = new JLabel("LinoKu - Sistem Distribusi Desa Malino", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(footer, BorderLayout.SOUTH);
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


    private void keluar() {
        this.dispose();
        JFrame loginFrame = new JFrame("LinoKu - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setContentPane(new LoginPanel(LinoKuApp.getUsers()));
        loginFrame.setVisible(true);
    }

    private void tambahProduk() {
    JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JTextField namaField = new JTextField();
    JComboBox<String> jenisBox = new JComboBox<>(new String[]{
        "Makanan dan Minuman", "Pakaian dan Aksesoris",
        "Kerajinan dan Dekorasi", "Produk Lainnya"
    });
    JTextField jumlahField = new JTextField();
    JTextField hargaField = new JTextField();

    panel.add(new JLabel("Nama Produk:"));
    panel.add(namaField);
    panel.add(new JLabel("Jenis Produk:"));
    panel.add(jenisBox);
    panel.add(new JLabel("Jumlah:"));
    panel.add(jumlahField);
    panel.add(new JLabel("Harga:"));
    panel.add(hargaField);

    int result = JOptionPane.showConfirmDialog(this, panel, "Tambah Produk",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String nama = namaField.getText().trim();
        String jenis = (String) jenisBox.getSelectedItem();
        int jumlah;
        double harga;

        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama produk tidak boleh kosong.");
            return;
        }

        try {
            jumlah = Integer.parseInt(jumlahField.getText().trim());
            if (jumlah < 0) throw new NumberFormatException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Jumlah tidak valid.");
            return;
        }

        try {
            harga = Double.parseDouble(hargaField.getText().trim());
            if (harga < 0) throw new NumberFormatException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Harga tidak valid.");
            return;
        }

        Produk produkBaru = new Produk(nama, jenis, jumlah, harga, produsenId);
        produsen.getProdukGlobal().add(produkBaru);
        produsen.saveProdukGlobal();
        JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan.");
    }
}

    private void lihatLaporan() {
    produsen.muatUlangLaporan();
    List<String> semuaLaporan = produsen.getLaporan();

    if (semuaLaporan.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Belum ada laporan distribusi.",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    List<String[]> dataLaporan = new ArrayList<>();
    for (String baris : semuaLaporan) {
        String[] bagian = baris.split(";");
        if (bagian.length >= 6) {
            dataLaporan.add(bagian);
        }
    }

    String[] kolom = {"Nama Produk", "Kategori", "Harga", "Stok", "Tujuan", "Biaya"};
    String[][] data = new String[dataLaporan.size()][kolom.length];
    for (int i = 0; i < dataLaporan.size(); i++) {
        data[i] = dataLaporan.get(i);
    }

    JTable table = new JTable(data, kolom);
    table.setEnabled(false);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(600, 300));

    JOptionPane.showMessageDialog(this, scrollPane, "Semua Laporan Distribusi", JOptionPane.INFORMATION_MESSAGE);
}

    private void lihatProduk() {
    ArrayList<Produk> milikSaya = new ArrayList<>();
    for (Produk p : produsen.getProdukGlobal()) {
        if (p.getPemilik().equals(produsenId)) milikSaya.add(p);
    }

    if (milikSaya.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada produk.");
        return;
    }

    String[] kolom = {"Nama", "Jenis", "Jumlah", "Harga"};
    String[][] data = new String[milikSaya.size()][4];

    for (int i = 0; i < milikSaya.size(); i++) {
        Produk p = milikSaya.get(i);
        data[i][0] = p.getNama();
        data[i][1] = p.getJenis();
        data[i][2] = String.valueOf(p.getJumlah());
        data[i][3] = "Rp" + p.getHarga();
    }

    JTable table = new JTable(data, kolom);
    table.setEnabled(false);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(500, 200));

    JOptionPane.showMessageDialog(this, scrollPane, "Daftar Produk Anda", JOptionPane.INFORMATION_MESSAGE);
}


    private void editProduk() {
    ArrayList<Produk> milikSaya = new ArrayList<>();
    for (Produk p : produsen.getProdukGlobal()) {
        if (p.getPemilik().equals(produsenId)) milikSaya.add(p);
    }

    if (milikSaya.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada produk untuk diedit.");
        return;
    }

    String[] kolom = {"Nama", "Jenis", "Jumlah", "Harga"};
    String[][] data = new String[milikSaya.size()][4];
    for (int i = 0; i < milikSaya.size(); i++) {
        Produk p = milikSaya.get(i);
        data[i][0] = p.getNama();
        data[i][1] = p.getJenis();
        data[i][2] = String.valueOf(p.getJumlah());
        data[i][3] = "Rp" + p.getHarga();
    }

    JTable table = new JTable(data, kolom);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(table);

    int result = JOptionPane.showConfirmDialog(this, scrollPane, "Pilih Produk yang Akan Diedit",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Produk dipilih = milikSaya.get(selectedRow);
            editProdukDetail(dipilih);
        } else {
            JOptionPane.showMessageDialog(this, "Tidak ada produk yang dipilih.");
        }
    }
}

private void editProdukDetail(Produk produk) {
    JTextField namaField = new JTextField(produk.getNama());
    JTextField jenisField = new JTextField(produk.getJenis());
    JTextField jumlahField = new JTextField(String.valueOf(produk.getJumlah()));
    JTextField hargaField = new JTextField(String.valueOf(produk.getHarga()));

    JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
    panel.add(new JLabel("Nama Produk:"));
    panel.add(namaField);
    panel.add(new JLabel("Jenis Produk:"));
    panel.add(jenisField);
    panel.add(new JLabel("Jumlah Produk:"));
    panel.add(jumlahField);
    panel.add(new JLabel("Harga Produk:"));
    panel.add(hargaField);

    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Detail Produk",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        try {
            String nama = namaField.getText().trim();
            String jenis = jenisField.getText().trim();
            int jumlah = Integer.parseInt(jumlahField.getText().trim());
            double harga = Double.parseDouble(hargaField.getText().trim());

            produk.setNama(nama);
            produk.setJenis(jenis);
            produk.setJumlah(jumlah);
            produk.setHarga(harga);

            produsen.saveProdukGlobal();

            JOptionPane.showMessageDialog(this, "Produk berhasil diperbarui.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah dan harga harus berupa angka.", "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void konfirmasiProduk() {
    ArrayList<Produk> milikSaya = new ArrayList<>();
    for (Produk p : produsen.getProdukGlobal()) {
        if (p.getPemilik().equals(produsenId)) milikSaya.add(p);
    }

    if (milikSaya.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Tidak ada produk untuk dikonfirmasi.");
        return;
    }

    String[] kolom = {"Nama", "Jenis", "Jumlah", "Harga"};
    String[][] data = new String[milikSaya.size()][4];
    for (int i = 0; i < milikSaya.size(); i++) {
        Produk p = milikSaya.get(i);
        data[i][0] = p.getNama();
        data[i][1] = p.getJenis();
        data[i][2] = String.valueOf(p.getJumlah());
        data[i][3] = "Rp" + p.getHarga();
    }

    JTable table = new JTable(data, kolom);
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane scrollPane = new JScrollPane(table);

    int result = JOptionPane.showConfirmDialog(this, scrollPane, "Pilih Produk untuk Dikonfirmasi",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            for (int idx : selectedRows) {
                Produk p = milikSaya.get(idx);
                produsen.getProdukKonfirmasi().add(p);
                produsen.getProdukGlobal().remove(p);
            }
            produsen.saveProdukKonfirmasi();
            produsen.saveProdukGlobal();
            JOptionPane.showMessageDialog(this, "Produk berhasil dikonfirmasi.");
        } else {
            JOptionPane.showMessageDialog(this, "Tidak ada produk yang dipilih.");
        }
    }
}

    private void hapusProduk() {
    ArrayList<Produk> milikSaya = new ArrayList<>();
    for (Produk p : produsen.getProdukGlobal()) {
        if (p.getPemilik().equals(produsenId)) milikSaya.add(p);
    }

    if (milikSaya.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Belum ada produk untuk dihapus.");
        return;
    }

    String[] kolom = {"Nama", "Jenis", "Jumlah", "Harga"};
    String[][] data = new String[milikSaya.size()][4];
    for (int i = 0; i < milikSaya.size(); i++) {
        Produk p = milikSaya.get(i);
        data[i][0] = p.getNama();
        data[i][1] = p.getJenis();
        data[i][2] = String.valueOf(p.getJumlah());
        data[i][3] = "Rp" + p.getHarga();
    }

    JTable table = new JTable(data, kolom);
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane scrollPane = new JScrollPane(table);

    int result = JOptionPane.showConfirmDialog(this, scrollPane, "Pilih Produk yang Akan Dihapus",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Yakin ingin menghapus " + selectedRows.length + " produk?",
                    "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                for (int idx : selectedRows) {
                    produsen.hapusProduk(milikSaya.get(idx));
                }
                produsen.saveProdukGlobal();
                JOptionPane.showMessageDialog(this, "Produk berhasil dihapus.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tidak ada produk yang dipilih.");
        }
    }
}

}
