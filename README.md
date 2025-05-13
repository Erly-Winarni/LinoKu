# LinoKu
LinoKu adalah aplikasi manajemen produk berbasis Java yang dirancang untuk mengelola dan mempermudah proses pengiriman  produk lokal dari Desa Malino ke berbagai pasar di Kota Makassar. Aplikasi ini mendukung pengelolaan data produk, menghitung biaya distribusi, serta pelaporan aktivitas secara efisien dan terstruktur.

## Role Pengguna
**1. Produsen**
**ID** dimulai dengan `"D-` diikuti 7 digit angka
**Password** terdiri dari 8 karakter (minimal 1 huruf besar, 1 huruf kecil, dan 1 angka)

**2. Admin**
**ID** dan **Password** sudah tersedia
**ID 1**  `E2437152` **Password** `Admin111`
**ID 2**  `E2592641` **Password** `Admin222`

## Registrasi & Login
* Pengguna memilih peran `Produsen` atau `Admin`
* Membuat ID dan Password (Password harus terdiri 8 karakter, termasuk huruf besar, huruf kecil, dan angka)
* Jika ingin login sebagai  `Admin` maka gunakan ID dan Username yang sudah disediakan
* Data akan disimpan dalam file `user.txt `

## Penyimpanan Data
Semua data disimpan dalam file:
1. `produk.txt`, menyimpan daftar produk
   

## Fitur untuk Produsen
**1. Tambah Produk**
Menambahkan produk dengan informasi `nama`, `jenis`, `jumlah`, dan `harga`

**2. Lihat Produk**
Menampilkan seluruh informasi produk yang tersimpan di file `.txt`

**3. Edit Produk**
Memilih produk dari daftar untuk mengubah `nama`, `jenis`, `jumlah`, dan `harga`

**4. Konfirmasi Produk**
Mengkonfirmasi produk yang akan didistribusikan

**5. Hapus Produk**
Menghapus produk dari daftar

## Fitur untuk Admin
**1. Distribusi Produk**
Memilih produk dari daftar untuk didistribusikan

**2. Tujuan Distribusi**
Memilih lokasi distribusi

**3. Laporan Distribusi**
Menampilkan laporan yang terdiri dari:
1. Nama Produk
2. Jenis Produk
3. Jumlah Produk
4. Harga Produk
5. Tujuan Distribusi
6. Biaya Distribusi

## Cara Menjalankan
1. Clone repository atau buka file di IDE seperti NetBeans/Intellij atau jika Anda ingin menjalankan di VS Code juga bisa
2. Jalankan file `LinoKuApp.java`
3. Ikuti alur login dan registrasi sesuai peran pengguna
4. Data akan otomatis disimpan dan dimuat dalam file `.txt`

