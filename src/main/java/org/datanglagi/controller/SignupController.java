package org.datanglagi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.datanglagi.App;
import org.datanglagi.DatabaseHalper;
import org.datanglagi.UserSession;

public class SignupController {

    @FXML private TextField txtUsernameSignUp; 
    @FXML private TextField txtEmailSignUp;
    @FXML private PasswordField txtPasswordSignUp;
    @FXML private PasswordField txtKonfirmasiSignUp;
    @FXML private TextField txtDurasiHaid;

    @FXML
    public void handleDaftar() {
        String username = txtUsernameSignUp.getText().trim();
        String email = txtEmailSignUp.getText().trim();
        String pass = txtPasswordSignUp.getText().trim();
        String konf = txtKonfirmasiSignUp.getText().trim();
        String durasiStr = txtDurasiHaid.getText().trim();

        // 1. Validasi Input Kosong
        if (username.isEmpty() || email.isEmpty() || pass.isEmpty() || konf.isEmpty() || durasiStr.isEmpty()) {
            tampilkanPesan("Data tidak lengkap. Mohon lengkapi seluruh kolom, wak!");
            return;
        } 
        
        if (pass.length() < 8) {
            tampilkanPesan("Kata sandi minimal harus terdiri dari 8 karakter.");
            return;
        }
        
        if (!pass.equals(konf)) {
            tampilkanPesan("Konfirmasi kata sandi tidak sesuai.");
            return;
        }

        // Validasi format angka untuk durasi haid
        int durasiHaid = 0;
        try {
            durasiHaid = Integer.parseInt(durasiStr);
        } catch (NumberFormatException e) {
            tampilkanPesan("Durasi haid harus berupa angka saja (contoh: 7).");
            return;
        }

        // 2. Query SQL Baru yang disesuaikan (Tanpa mencari id_user otomatis)
        String queryUser = "INSERT INTO user (username, email, password, durasi_haid) VALUES (?, ?, ?, ?)";
        String queryHaid = "INSERT INTO siklus_haid (username, panjang_siklus) VALUES (?, ?)";

        try (Connection conn = DatabaseHalper.getConnection()) {
            conn.setAutoCommit(false); // Mulai transaksi database (Atomic)

            try {
                // A. Masukkan data user lengkap ke tabel user
                try (PreparedStatement stmtUser = conn.prepareStatement(queryUser)) {
                    stmtUser.setString(1, username);
                    stmtUser.setString(2, email);
                    stmtUser.setString(3, pass);
                    stmtUser.setInt(4, durasiHaid);
                    stmtUser.executeUpdate();
                }

                // B. Masukkan inisialisasi siklus awal menggunakan username sebagai Foreign Key
                try (PreparedStatement stmtHaid = conn.prepareStatement(queryHaid)) {
                    stmtHaid.setString(1, username);
                    stmtHaid.setInt(2, durasiHaid); // default panjang siklus awal disamakan durasi haid atau diisi 28
                    stmtHaid.executeUpdate();
                }

                conn.commit(); // Eksekusi sukses semua, kunci perubahan ke MySQL!
                
                tampilkanPesan("Pendaftaran berhasil, wak! Selamat datang di DatangLagi.");
                
                // 3. Masukkan data ke dalam UserSession murni milikmu
                UserSession.getInstance().startSession(username, email, durasiHaid);

                // 4. Pindah langsung ke halaman utama aplikasi
                try {
                    App.setRoot("navbar"); 
                } catch (IOException e) {
                    e.printStackTrace();
                    tampilkanPesan("Terjadi kesalahan saat memuat halaman aplikasi.");
                }

            } catch (SQLException e) {
                conn.rollback(); // Batalkan semua aksi jika salah satu tabel gagal input
                throw e; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Menangkap jika username atau email ternyata kembar di database
            if (e.getMessage().contains("Duplicate entry")) {
                tampilkanPesan("Username atau Email sudah terdaftar. Silakan cari yang lain ya!");
            } else {
                tampilkanPesan("Gagal menyimpan data ke database: " + e.getMessage());
            }
        }
    }

    @FXML
    public void handleMasuk() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tampilkanPesan(String pesan) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Informasi");
        a.setHeaderText(null);
        a.setContentText(pesan);
        a.showAndWait();
    }
}