package org.datanglagi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import org.datanglagi.DatabaseHalper;
import org.datanglagi.UserSession;

public class HomepageController {

    @FXML private Label labelUsername, lblHari, lblHariKe, lblTanggalHaidNext;
    @FXML private Label lblMarah, lblSedih, lblBiasa, lblSenang;
    @FXML private Label lblPanjangSiklusVal, lblDurasiHaidVal, lblOvulasiVal, lblSiklusDicatatVal;

    @FXML
    public void initialize() {
        // 1. Username & Hari (Sesuai Data User & Input Data)
        String user = UserSession.getInstance().getUsername();
        labelUsername.setText(user);
        lblHari.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", new Locale("id", "ID"))));
        
        muatDataRingkasan(user);
    }

    private void muatDataRingkasan(String username) {
        String queryData = "SELECT tanggal_mulai, tanggal_akhir, panjang_siklus FROM siklus_haid WHERE username = ? ORDER BY id_siklus DESC LIMIT 1";
        
        try (Connection conn = DatabaseHalper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryData)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LocalDate mulai = rs.getDate("tanggal_mulai").toLocalDate();
                LocalDate akhir = rs.getDate("tanggal_akhir").toLocalDate();
                int panjang = rs.getInt("panjang_siklus");

                // 2. Panjang Siklus (Database) & Durasi Haid (Mulai - Akhir)
                lblPanjangSiklusVal.setText(panjang + " Hari");
                lblDurasiHaidVal.setText((ChronoUnit.DAYS.between(mulai, akhir) + 1) + " Hari");
                
                // 3. Tanggal Haid Selanjutnya & Hari Ke (Countdown menuju Menstruasi)
                LocalDate nextHaid = mulai.plusDays(panjang);
                lblTanggalHaidNext.setText(nextHaid.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
                long sisaHari = ChronoUnit.DAYS.between(LocalDate.now(), nextHaid);
                lblHariKe.setText(sisaHari >= 0 ? sisaHari + " Hari Lagi" : "Haid Telah Tiba");
                
                // 4. Ovulasi (Statis 5 Hari sesuai standar umum)
                lblOvulasiVal.setText("5 Hari");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        
        // 5. Siklus Dicatat (Agregasi database)
        try (Connection conn = DatabaseHalper.getConnection();
             PreparedStatement stmtCount = conn.prepareStatement("SELECT COUNT(*) FROM siklus_haid WHERE username = ?")) {
            stmtCount.setString(1, username);
            ResultSet rs = stmtCount.executeQuery();
            if (rs.next()) lblSiklusDicatatVal.setText(rs.getInt(1) + " Kali");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 6. Perubahan Mood (Single Select Merah)
    @FXML
    private void klikMood(MouseEvent e) {
        Label klik = (Label) e.getSource();
        Label[] moods = {lblMarah, lblSedih, lblBiasa, lblSenang};
        for (Label l : moods) {
            // Jika label yang diklik sama dengan label dalam array, warnai merah, jika tidak kembalikan ke hitam
            l.setStyle(l == klik ? "-fx-text-fill: #6E1418; -fx-font-weight: bold;" : "-fx-text-fill: black; -fx-font-weight: normal;");
        }
    }
}