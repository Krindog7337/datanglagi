package org.datanglagi;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SiklusKalkulator {

    /**
     * Hitung panjang siklus (jarak dari HPH bulan lalu ke HPH bulan ini)
     */
    public static long hitungPanjangSiklus(LocalDate hphBulanLalu, LocalDate hphBulanIni) {
        if (hphBulanLalu == null || hphBulanIni == null) return 28; // Default jika data < 2
        return ChronoUnit.DAYS.between(hphBulanLalu, hphBulanIni);
    }

    /**
     * Hitung durasi haid nyata (Jarak dari klik btnMulai sampai btnAkhir)
     */
    public static long hitungDurasiHaidNyata(LocalDate tglMulai, LocalDate tglAkhir) {
        if (tglMulai == null || tglAkhir == null) return 0;
        return ChronoUnit.DAYS.between(tglMulai, tglAkhir) + 1; // +1 agar hari mulai terhitung
    }

    /**
     * Prediksi Tanggal Haid Berikutnya (lbltanggal)
     */
    public static LocalDate hitungPrediksiHaidBerikutnya(LocalDate hphTerakhir, long panjangSiklus) {
        if (hphTerakhir == null) return LocalDate.now().plusDays(28); // Estimasi kasarnya
        return hphTerakhir.plusDays(panjangSiklus);
    }

    /**
     * Hitung berapa hari lagi menuju haid berikutnya (lblharike)
     */
    public static long hitungHariMenujuHaid(LocalDate tglSekarang, LocalDate tglPrediksiHaid) {
        return ChronoUnit.DAYS.between(tglSekarang, tglPrediksiHaid);
    }

    /**
     * Hitung Hari Ovulasi (Mundur 14 hari dari prediksi haid berikutnya)
     */
    public static LocalDate hitungHariOvulasi(LocalDate tglPrediksiHaid) {
        if (tglPrediksiHaid == null) return null;
        return tglPrediksiHaid.minusDays(14);
    }
    
    /**
     * Cek Fase berdasarkan tanggal yang di-klik di kalender
     */
    public static String cekFaseKalender(LocalDate tglDilik, LocalDate hphTerakhir, LocalDate tglOvulasi, LocalDate tglPrediksiBerikutnya) {
        if (tglDilik.equals(tglOvulasi)) {
            return "Fase Ovulasi";
        }
        if (tglDilik.isAfter(hphTerakhir) && tglDilik.isBefore(tglOvulasi)) {
            return "Fase Folikular";
        }
        if (tglDilik.isAfter(tglOvulasi) && tglDilik.isBefore(tglPrediksiBerikutnya)) {
            return "Fase Luteal";
        }
        return "Fase Menstruasi / Umum";
    }
}
