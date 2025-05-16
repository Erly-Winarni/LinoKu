package Code;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Produsen extends Role implements ILaporan{
    private String nama;
    private ArrayList<Produk> produkGlobal;
    private ArrayList<Produk> produkKonfirmasi;
    private ArrayList<String> laporan;

    public Produsen(String nama) {
        this.nama = nama;
        this.produkGlobal = loadProdukGlobal();
        this.produkKonfirmasi = loadProdukKonfirmasi();
        this.laporan = loadLaporan();
    }

    public ArrayList<Produk> getProdukGlobal() {
        return produkGlobal;
    }

    public ArrayList<Produk> getProdukKonfirmasi() {
        return produkKonfirmasi;
    }

    public List<Produk> getProdukSaya() {
        List<Produk> milikSaya = new ArrayList<>();
        for (Produk p : produkGlobal) {
            if (p.getPemilik().equals(nama)) {
                milikSaya.add(p);
            }
        }
        return milikSaya;
    }



    public void tambahProduk(String namaProduk, String jenis, int jumlah, double harga) {
        produkGlobal.add(new Produk(namaProduk, jenis, jumlah, harga, nama));
        String laporanBaru = String.format("%s;%s;%d;%.2f;%s;%s", namaProduk, jenis, jumlah, harga, "some info", "some biaya");
        laporan.add(laporanBaru);
        saveProdukGlobal();
        saveLaporan();

    }

    public void editProduk(Produk p, String namaBaru, String jenisBaru, Integer jumlahBaru, Double hargaBaru) {
        if (namaBaru != null && !namaBaru.isEmpty()) p.setNama(namaBaru);
        if (jenisBaru != null && !jenisBaru.isEmpty()) p.setJenis(jenisBaru);
        if (jumlahBaru != null && jumlahBaru >= 0) p.setJumlah(jumlahBaru);
        if (hargaBaru != null && hargaBaru >= 0) p.setHarga(hargaBaru);
        saveProdukGlobal();
    }

    public void hapusProduk(Produk p) {
        produkGlobal.remove(p);
        saveProdukGlobal();
    }

    public void konfirmasiProduk(List<Produk> produkDipilih) {
        for (Produk p : produkDipilih) {
            if (!produkKonfirmasi.contains(p)) {
                produkKonfirmasi.add(p);
                produkGlobal.remove(p);
            }
        }
        saveProdukGlobal();
        saveProdukKonfirmasi();
    }
    @Override
     public void muatUlangLaporan() {
    laporan.clear();
    File file = new File("laporan.txt");
    if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                laporan.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    @Override
    public List<String> getLaporan() {
        return new ArrayList<>(laporan);
    }


    public void saveProdukGlobal() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("produk.txt"))) {
            for (Produk p : produkGlobal) {
                pw.println(p.serialize());
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan produk: " + e.getMessage());
        }
    }

    public void saveProdukKonfirmasi() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("produkKonfirmasi.txt"))) {
            for (Produk p : produkKonfirmasi) {
                pw.println(p.serialize());
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan produk konfirmasi: " + e.getMessage());
        }
    }

    public void saveLaporan() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("laporan.txt"))) {
            for (String l : laporan) {
                pw.println(l);
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan laporan: " + e.getMessage());
        }
    }

    public ArrayList<Produk> loadProdukGlobal() {
        ArrayList<Produk> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("produk.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Produk p = Produk.deserialize(line);
                if (p != null) list.add(p);
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat produk: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Produk> loadProdukKonfirmasi() {
        ArrayList<Produk> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("produkKonfirmasi.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Produk p = Produk.deserialize(line);
                if (p != null) list.add(p);
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat produk konfirmasi: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<String> loadLaporan() {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("laporan.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat laporan: " + e.getMessage());
        }
        return list;
    }

    @Override
    public String getRoleInfo() {
        return "Produsen bertanggung jawab mengelola data produk yang ingin didistribusikan.";
    }
} 
