package Main;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import Code.User;
import Code.Admin;
import Code.Produk;
import Gui.LoginPanel;
import Gui.AdminGUI;
import Gui.ProdusenGUI;
import Gui.RegisterPanel;


public class LinoKuApp {
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        loadUsers(users);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("LinoKu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new LoginPanel(users)); // Panel login utama
            frame.setVisible(true);
        });
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void loadUsers(ArrayList<User> users) {
        File file = new File("users.txt");
        if (!file.exists()) {
            users.add(new User("E2437152", "Admin111", new Admin(loadProdukKonfirmasi(), loadDistribusi(), loadLaporan())));
            users.add(new User("E2592641", "Admin222", new Admin(loadProdukKonfirmasi(), loadDistribusi(), loadLaporan())));
            saveUsers(users);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = User.deserialize(
                    line,
                    new ArrayList<Produk>(), 
                    loadProdukKonfirmasi(),
                    loadDistribusi(),
                    loadLaporan()
                    
                );
                if (user != null) users.add(user);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca users.txt: " + e.getMessage());
        }
    }

    private static void saveUsers(ArrayList<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                pw.println(user.serialize());
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan users.txt: " + e.getMessage());
        }
    }


    public static ArrayList<Produk> loadProdukKonfirmasi() {
        return loadProdukDariFile("produkKonfirmasi.txt");
    }

    public static ArrayList<Produk> loadDistribusi() {
        return loadProdukDariFile("distribusi.txt");
    }

    public static ArrayList<String> loadLaporan() {
        ArrayList<String> laporan = new ArrayList<>();
        File file = new File("laporan.txt");
        if (!file.exists()) return laporan;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                laporan.add(line);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca laporan.txt: " + e.getMessage());
        }

        return laporan;
    }

    private static ArrayList<Produk> loadProdukDariFile(String namaFile) {
        ArrayList<Produk> list = new ArrayList<>();
        File file = new File(namaFile);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Produk p = Produk.deserialize(line);
                if (p != null) list.add(p);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca " + namaFile + ": " + e.getMessage());
        }

        return list;
    }
}
