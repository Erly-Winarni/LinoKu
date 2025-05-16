package Code;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;


public class Admin extends Role implements ILaporan {
    private ArrayList<Produk> produkKonfirmasi;
    private ArrayList<Produk> distribusi;
    private ArrayList<String> laporan;

    private List<String> pasarList;
    private List<Double> biayaList;

    public Admin(ArrayList<Produk> produkKonfirmasi, ArrayList<Produk> distribusi, ArrayList<String> laporan) {
        this.produkKonfirmasi = produkKonfirmasi;
        this.distribusi = distribusi;
        this.laporan = laporan;

        pasarList = Arrays.asList("Pasar Tello", "Pasar Terong", "Pasar Antang", "Pasar Toddopuli", "Pasar Tamamaung");
        biayaList = Arrays.asList(900000.0, 960000.0, 885000.0, 870000.0, 930000.0);
    }

    public List<Produk> getProdukKonfirmasi() {
        return new ArrayList<>(produkKonfirmasi);
    }

    public List<Produk> getDistribusi() {
        return new ArrayList<>(distribusi);
    }

    public List<String> getPasarList() {
        return new ArrayList<>(pasarList);
    }

    @Override
    public List<String> getLaporan() {
        return new ArrayList<>(laporan);
    }

    public double getBiayaPasar(String pasar) {
        int index = pasarList.indexOf(pasar);
        if (index != -1) {
            return biayaList.get(index);
        }
        return 0;
    }

    public void pindahkanKeDistribusi(List<Integer> indexDipilih) {
        List<Produk> dipindahkan = new ArrayList<>();
        for (int i : indexDipilih) {
            if (i >= 0 && i < produkKonfirmasi.size()) {
                Produk p = produkKonfirmasi.get(i);
                dipindahkan.add(p);
                distribusi.add(p);
            }
        }
        indexDipilih.stream().sorted(Comparator.reverseOrder()).forEach(i -> {
            if (i >= 0 && i < produkKonfirmasi.size()) produkKonfirmasi.remove((int) i);
        });
        saveProdukKonfirmasi();
        saveDistribusi();
    }

    public void distribusikanProduk(Produk produk, String pasar, double biaya) {
    distribusi.remove(produk); 
    
    String laporanBaru = String.format("%s;%s;%.0f;%.0f;%s;Rp%,.0f",
    produk.getNama(),
    produk.getJenis(),
    (double) produk.getHarga(),
    (double) produk.getJumlah(),
    pasar,
    biaya);
  
    laporan.add(laporanBaru);
    saveDistribusi();
    saveLaporan();
}


    private void saveProdukKonfirmasi() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("produkKonfirmasi.txt"))) {
            for (Produk p : produkKonfirmasi) {
                pw.println(p.serialize());
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan produk konfirmasi: " + e.getMessage());
        }
    }

    private void saveDistribusi() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("distribusi.txt"))) {
            for (Produk p : distribusi) {
                pw.println(p.serialize());
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan distribusi: " + e.getMessage());
        }
    }

    @Override
    public void saveLaporan() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("laporan.txt"))) {
            for (String l : laporan) {
                pw.println(l);
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan laporan: " + e.getMessage());
        }
    }

    
    public void muatUlangProdukKonfirmasi() {
        produkKonfirmasi.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("produkKonfirmasi.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Produk p = Produk.deserialize(line);
                if (p != null) produkKonfirmasi.add(p);
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat ulang produk konfirmasi: " + e.getMessage());
        }
    }

    public void muatUlangDistribusi() {
        distribusi.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("distribusi.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Produk p = Produk.deserialize(line);
                if (p != null) distribusi.add(p);
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat ulang distribusi: " + e.getMessage());
        }
    }
    @Override
    public void muatUlangLaporan() {
        laporan.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("laporan.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                laporan.add(line);
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat ulang laporan: " + e.getMessage());
        }
    }

    public void loadAll() {
        muatUlangProdukKonfirmasi();
        muatUlangDistribusi();
        muatUlangLaporan();
    }

    @Override
    public String getRoleInfo() {
        return "Admin bertanggung jawab mengelola produk terkonfirmasi dan distribusi.";
    }
}
