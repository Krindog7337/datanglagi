package org.datanglagi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import org.datanglagi.App;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    // Variabel "Memori" sederhana
    private static String savedEmail = "";
    private static String savedPass = "";
    private static boolean akunTerdaftar = false;

    // Fungsi untuk dipanggil oleh SignupController agar bisa menyimpan data
    public static void simpanAkun(String email, String pass) {
        savedEmail = email;
        savedPass = pass;
        akunTerdaftar = true;
    }

    @FXML
    public void handleLogin() {
        String email = txtEmail.getText().trim();
        String pass = txtPassword.getText().trim();

        // 1. Cek jika kolom kosong
        if (email.isEmpty() || pass.isEmpty()) {
            tampilkanPesan(AlertType.WARNING, "Peringatan", "Email dan Kata Sandi tidak boleh kosong!");
            return;
        }

        // 2. Cek apakah akun sudah pernah didaftar
        if (!akunTerdaftar) {
            tampilkanPesan(AlertType.ERROR, "Login Gagal", "Anda belum punya akun. Silakan daftar dulu.");
            return;
        }

        // 3. Cek apakah email dan password benar
        if (email.equals(savedEmail) && pass.equals(savedPass)) {
            try {
                App.setRoot("homepage");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            tampilkanPesan(AlertType.ERROR, "Login Gagal", "Email atau kata sandi salah.");
        }
    }

    @FXML
    public void handleBukaSignup() {
        try {
            App.setRoot("signup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLupaPassword() {
        tampilkanPesan(AlertType.INFORMATION, "Info", "Fitur ini sedang dalam pengembangan.");
    }

    private void tampilkanPesan(AlertType tipe, String judul, String konten) {
        Alert alert = new Alert(tipe);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(konten);
        alert.showAndWait();
    }
}