package org.datanglagi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.datanglagi.App;
import org.datanglagi.DatabaseHalper;
import org.datanglagi.UserSession;

public class InputharianController {

    // Label Hari di bawah Title
    @FXML private Label lblHari;

    // 4 Button Biasa untuk Aliran Haid
    @FXML private Button btnTidakAda;
    @FXML private Button btnRingan;
    @FXML private Button btnSedang;
    @FXML private Button btnBerat;

    // CheckBox Gejala
    @FXML private CheckBox cbNyeriPerut;
    @FXML private CheckBox cbKembung;
    @FXML private CheckBox cbPayudaraNyeri;
    @FXML private CheckBox cbSakitKepala;
    @FXML private CheckBox cbKram;
    @FXML private CheckBox cbMual;
    @FXML private CheckBox cbLemas;

    @FXML private TextArea txtCatatan;
    @FXML private ToggleButton btnStatusKB; 

    // Variabel penampung untuk mendeteksi button aliran mana yang aktif (Default: Tidak Ada)
    private String aliranTerpilih = "Tidak Ada";

    @FXML
    public void initialize() {
        // 1. Set Label Hari otomatis sesuai LocalDate hari ini (Format: Hari, Tanggal Bulan Tahun)
        LocalDate hariIni = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
        if (lblHari != null) {
            lblHari.setText(hariIni.format(formatter));
        }

        // 2. Set warna default awal untuk button "Tidak Ada" agar menandakan pilihan dasar
        aturWarnaButtonAliran(btnTidakAda);
    }

    // 2. Fungsi Klik untuk Masing-masing Button Aliran (Dipasang di Scene Builder)
    @FXML
    private void klikTidakAda(MouseEvent event) {
        aliranTerpilih = "Tidak Ada";
        aturWarnaButtonAliran(btnTidakAda);
    }

    @FXML
    private void klikRingan(MouseEvent event) {
        aliranTerpilih = "Ringan";
        aturWarnaButtonAliran(btnRingan);
    }

    @FXML
    private void klikSedang(MouseEvent event) {
        aliranTerpilih = "Sedang";
        aturWarnaButtonAliran(btnSedang);
    }

    @FXML
    private void klikBerat(MouseEvent event) {
        aliranTerpilih = "Berat";
        aturWarnaButtonAliran(btnBerat);
    }

    // Fungsi pembantu untuk mengubah gaya button yang aktif jadi merah tua dan sisanya abu-abu/putih
    private void aturWarnaButtonAliran(Button btnAktif) {
        Button[] semuaButton = {btnTidakAda, btnRingan, btnSedang, btnBerat};
        for (Button btn : semuaButton) {
            if (btn != null) {
                if (btn == btnAktif) {
                    btn.setStyle("-fx-background-color: #6E1418; -fx-text-fill: #FFFFFF; -fx-background-radius: 15;");
                } else {
                    btn.setStyle("-fx-background-color: #F1EFEF; -fx-text-fill: #000000; -fx-background-radius: 15;");
                }
            }
        }
    }

    @FXML
    private void handleSimpan() {
        String usernameAktif = UserSession.getInstance().getUsername();

        // Gabungkan Gejala-gejala dari CheckBox
        StringBuilder gejalaBuilder = new StringBuilder();
        if (cbNyeriPerut != null && cbNyeriPerut.isSelected()) gejalaBuilder.append("Nyeri Perut, ");
        if (cbKembung != null && cbKembung.isSelected()) gejalaBuilder.append("Kembung, ");
        if (cbPayudaraNyeri != null && cbPayudaraNyeri.isSelected()) gejalaBuilder.append("Payudara Nyeri, ");
        if (cbSakitKepala != null && cbSakitKepala.isSelected()) gejalaBuilder.append("Sakit Kepala, ");
        if (cbKram != null && cbKram.isSelected()) gejalaBuilder.append("Kram, ");
        if (cbMual != null && cbMual.isSelected()) gejalaBuilder.append("Mual, ");
        if (cbLemas != null && cbLemas.isSelected()) gejalaBuilder.append("Lemas, ");

        String gejalaFinal = gejalaBuilder.toString();
        if (!gejalaFinal.isEmpty()) {
            gejalaFinal = gejalaFinal.substring(0, gejalaFinal.length() - 2);
        } else {
            gejalaFinal = "Tidak Ada Gejala";
        }

        String catatanTambahan = (txtCatatan != null) ? txtCatatan.getText().trim() : "";
        boolean statusKB = (btnStatusKB != null) && btnStatusKB.isSelected();
        java.sql.Date tanggalHariIni = java.sql.Date.valueOf(LocalDate.now());

        // Eksekusi Simpan Data
        String query = "INSERT INTO catatan_harian (username, tanggal_catatan, aliran, gejala, catatan_tambahan, status_kb) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHalper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usernameAktif);
            stmt.setDate(2, tanggalHariIni);
            stmt.setString(3, aliranTerpilih); 
            stmt.setString(4, gejalaFinal);
            stmt.setString(5, catatanTambahan);
            stmt.setBoolean(6, statusKB);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                tampilkanPesan("Data harian berhasil disimpan, wak!");
                
                try {
                    App.setRoot("navbar"); 
                } catch (IOException e) {
                    e.printStackTrace();
                    tampilkanPesan("Gagal memuat ulang halaman utama.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            tampilkanPesan("Gagal menyimpan catatan harian: " + e.getMessage());
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