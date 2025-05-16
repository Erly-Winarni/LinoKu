package Code;

import java.util.Objects;

public class Produk {
    private String nama;
    private String jenis;
    private int jumlah;
    private double harga;
    private String pemilik;

    public Produk(String nama, String jenis, int jumlah, double harga, String pemilik) {
        this.nama = nama;
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.harga = harga;
        this.pemilik = pemilik;
    }

    public String getNama() { return nama; }
    public String getJenis() { return jenis; }
    public int getJumlah() { return jumlah; }
    public double getHarga() { return harga; }
    public String getPemilik() { return pemilik; }

    public void setNama(String nama) { this.nama = nama; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setHarga(double harga) { this.harga = harga; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produk)) return false;
        Produk produk = (Produk) o;
        return jumlah == produk.jumlah &&
               Objects.equals(nama, produk.nama) &&
               Objects.equals(jenis, produk.jenis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nama, jenis, jumlah);
    }

    public String serialize() {
        return nama + ";" + jenis + ";" + jumlah + ";" + harga + ";" + pemilik;
    }

    public static Produk deserialize(String line) {
        String[] parts = line.split(";");
        if(parts.length != 5) return null;
        try {
            String nama = parts[0];
            String jenis = parts[1];
            int jumlah = Integer.parseInt(parts[2]);
            double harga = Double.parseDouble(parts[3]);
            String pemilik = parts[4];
            return new Produk(nama, jenis, jumlah, harga, pemilik);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Nama: " + nama + ", Jenis: " + jenis + ", Jumlah: " + jumlah + ", Harga: Rp" + harga;
    }
}
