package org.datanglagi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.datanglagi.App;
import org.datanglagi.UserSession;
import java.io.IOException;

public class UserController {

    // Menghubungkan ke komponen label teks "Selamat Datang Username" di FXML
    @FXML private Label lblUsername; 

    @FXML
    public void initialize() {
        // 1. Mengambil data username yang tersimpan di memori UserSession saat login/signup
        String namaAktif = UserSession.getInstance().getUsername();
        
        if (lblUsername != null) {
            if (namaAktif != null && !namaAktif.isEmpty()) {
                // 2. Mengubah teks secara dinamis sesuai nama di memori
                lblUsername.setText("Selamat Datang " + namaAktif);
            } else {
                // Cadangan aman jika memori kosong
                lblUsername.setText("Selamat Datang Pengguna");
            }
        } else {
            System.out.println("[WARNING] Komponen labelUsername masih bernilai null! Periksa fx:id di Scene Builder.");
        }
    }
    @FXML
private void handleTentang(MouseEvent event) {
    tampilkanPesan("DatangLagi adalah aplikasi pelacak siklus menstruasi dan kesehatan wanita modern.");
}

    @FXML
    private void handleLogout(MouseEvent event) {
        // 1. Menghapus data login yang tersimpan di memori session (di-set kembali ke null/0)
        UserSession.getInstance().clearSession();
        
        try {
            // 2. Merombak panggung utama untuk kembali ke halaman login awal
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
            tampilkanPesan("Gagal kembali ke halaman login, wak!");
        }
    }

    // Fungsi pembantu untuk memunculkan notifikasi pop-up dialog
    private void tampilkanPesan(String pesan) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Informasi");
        a.setHeaderText(null);
        a.setContentText(pesan);
        a.showAndWait();
    }
}