package org.datanglagi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException; // Tambahkan ini
import org.datanglagi.App;
import org.datanglagi.DataUser;

public class SignupController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtKonfirmasi;
    @FXML private TextField txtDurasiHaid;

    @FXML
    public void handleDaftar() {
        String email = txtEmail.getText().trim();
        String pass = txtPassword.getText().trim();
        String konf = txtKonfirmasi.getText().trim();
        String durasi = txtDurasiHaid.getText().trim();

        // 1. Validasi
        if (email.isEmpty() || pass.isEmpty() || konf.isEmpty() || durasi.isEmpty()) {
            tampilkanPesan("Data tidak lengkap. Mohon lengkapi seluruh kolom.");
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

        // 2. Simpan Data
        DataUser.simpanData(email, pass); 

        // 3. Pindah langsung ke Homepage (Bypass halaman login)
        try {
            // Kita tidak perlu menampilkan pesan sukses lagi agar alur terasa lebih cepat
            App.setRoot("homepage");
        } catch (IOException e) {
            e.printStackTrace();
            tampilkanPesan("Terjadi kesalahan saat memuat halaman.");
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