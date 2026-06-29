package org.datanglagi;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SiklusDAO {

    public Map<String, Object> hitungLogikaSiklus(String username) {
        Map<String, Object> hasilKalkulasi = new HashMap<>();
        
        // Ambil 2 data haid terakhir untuk menghitung panjang siklus asli user
        String query = "SELECT tanggal_mulai, tanggal_akhir FROM siklus_haid WHERE username = ? ORDER BY tanggal_mulai DESC LIMIT 2";
        
        LocalDate hphTerakhir = null;
        LocalDate hphBulanLalu = null;
        LocalDate tglAkhirTerakhir = null;

        try (Connection conn = DatabaseHalper.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    hphTerakhir = rs.getDate("tanggal_mulai").toLocalDate();
                    Date tglAkhirSql = rs.getDate("tanggal_akhir");
                    if (tglAkhirSql != null) {
                        tglAkhirTerakhir = tglAkhirSql.toLocalDate();
                    }
                }
                if (rs.next()) {
                    hphBulanLalu = rs.getDate("tanggal_mulai").toLocalDate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // --- MULAI LOGIKA KALKULASI BERDASARKAN SIKLUS KALKULATOR ---
        
        // 1. Hitung Panjang Siklus Asli (lblpanjang)
        long panjangSiklus = SiklusKalkulator.hitungPanjangSiklus(hphBulanLalu, hphTerakhir);
        
        // 2. Hitung Durasi Haid Nyata Bulan Ini (lbldurasi)
        long durasiHaidNyata = SiklusKalkulator.hitungDurasiHaidNyata(hphTerakhir, tglAkhirTerakhir);
        
        // 3. Hitung Prediksi Haid Berikutnya (lbltanggal)
        LocalDate prediksiBerikutnya = SiklusKalkulator.hitungPrediksiHaidBerikutnya(hphTerakhir, panjangSiklus);
        
        // 4. Hitung Berapa Hari Lagi Menuju Haid (lblharike)
        long hariMenujuHaid = SiklusKalkulator.hitungHariMenujuHaid(LocalDate.now(), prediksiBerikutnya);
        
        // 5. Hitung Hari Ovulasi (lblovulasi)
        LocalDate tglOvulasi = SiklusKalkulator.hitungHariOvulasi(prediksiBerikutnya);

        // --- BUNGKUS SEMUA HASIL UNTUK DILEMPAR KE UI / HOMEPAGE ---
        hasilKalkulasi.put("panjangSiklus", panjangSiklus);
        hasilKalkulasi.put("durasiHaidNyata", durasiHaidNyata);
        hasilKalkulasi.put("prediksiBerikutnya", prediksiBerikutnya);
        hasilKalkulasi.put("hariMenujuHaid", hariMenujuHaid);
        hasilKalkulasi.put("tglOvulasi", tglOvulasi);
        
        // Ambil info durasi bawaan dari session untuk cadangan penentuan fase hari ini
        int durasiStr = UserSession.getInstance().getDurasiStr();
        long durasiPakai = (durasiHaidNyata > 0) ? durasiHaidNyata : durasiStr;
        
        // Tentukan fase hari ini untuk Beranda
        String faseHariIni = SiklusKalkulator.cekFaseKalender(LocalDate.now(), hphTerakhir, tglOvulasi, prediksiBerikutnya);
        hasilKalkulasi.put("faseHariIni", faseHariIni);

        return hasilKalkulasi;
    }
}