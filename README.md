<h1 align="center"> 💻 LINOKU 💻 </h1> <p align="center"> </p>

# LinoKu
LinoKu merupakan aplikasi manajemen produk berbasis Java yang hadir sebagai wujud sinergi antara kearifan lokal dan inovasi teknologi. Diciptakan untuk mendukung pengelolaan distribusi produk unggulan dari Desa Malino ke berbagai pasar di Kota Makassar, LinoKu menawarkan sistem yang terintegrasi untuk pencatatan data produk, perhitungan biaya distribusi yang akurat, serta pelaporan aktivitas secara rapi dan sistematis. Dengan pendekatan elegan dan fungsional, LinoKu menjadi alat yang andal dalam mendukung efisiensi, transparansi, dan kemajuan ekonomi berbasis digital.


## 🔑 Role Pengguna
**1. Produsen**
**ID** dimulai dengan `"D"` diikuti 7 digit angka (misalnya D1234567)
**Password** terdiri dari 8 karakter, harus mengandung:
* Minimal 1 huruf besar
* Minimal 1 huruf kecil
* Minimal 1 angka

**2. Admin**
Gunakan **ID** dan **Password** berikut untuk login sebagai Admin:

* **ID 1**  `E2437152` **Password** `Admin111`

* **ID 2**  `E2592641` **Password** `Admin222`

## 📝 Registrasi & Login
* Pengguna memilih peran sebagai `Produsen` atau `Admin`
* Untuk `Produsen`, pengguna harus membuat ID dan Password sesuai format yang ditentukan
* Untuk `Admin`, login menggunakan ID dan Password yang sudah disediakan
* Semua data pengguna akan disimpan dalam file `users.txt `

## 💾 Penyimpanan Data
Semua data disimpan dalam file:
1. `users.txt` menyimpan data pengguna (Produsen dan Admin)
2. `produk.txt` menyimpan data produk 
3. `produkKonfirmasi.txt` menyimpan data produk yang ingin didistribusikan
   

## 👤 Fitur untuk Produsen
**1. Tambah Produk**
Menambahkan produk dengan informasi `nama`, `jenis`, `jumlah`, dan `harga`

**2. Lihat Produk**
Menampilkan seluruh informasi produk yang tersimpan di file `.txt`

**3. Edit Produk**
Memilih produk dari daftar untuk mengubah `nama`, `jenis`, `jumlah`, dan `harga`

**4. Konfirmasi Produk**
Mengkonfirmasi produk yang akan didistribusikan

**5. Hapus Produk**
Menghapus produk dari daftar yang tersimpan

## 🛠️ Fitur untuk Admin
**1. Distribusi Produk**
Memilih produk dari daftar yang telah dikonfirmasi untuk didistribusikan

**2. Tujuan Distribusi**
Memilih lokasi distribusi produk

**3. Laporan Distribusi**
Menampilkan laporan yang terdiri dari:
1. Nama Produk
2. Jenis Produk
3. Jumlah Produk
4. Harga Produk
5. Tujuan Distribusi
6. Biaya Distribusi

## ▶️ Cara Menjalankan
1. Clone repository ke komputer Anda
   ```Java
   git clone https://github.com/Erly-Winarni/LinoKu.git
   ```
2. Buka project menggunakan IDE seperti NetBeans, Intellij IDEA, atau Visual Studio Code
3. Jalankan file
   ```Java
   LinoKuApp.java
   ```
4. Ikuti alur login atau registrasi sesuai peran pengguna
5. Data akan otomatis disimpan dan dimuat dalam file `.txt`

