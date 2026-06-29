package org.datanglagi;

import java.time.LocalDate;
import java.util.List;

public class CatatanHarian {
    private String username;
    private LocalDate tanggalCatatan;
    private String tingkatAliran; 
    private List<String> gejalaSelected; 
    private String catatanTambahan;
    private boolean statusKB; 

    // Constructor Utama
    public CatatanHarian(String username, LocalDate tanggalCatatan, String tingkatAliran, 
                         List<String> gejalaSelected, String catatanTambahan, boolean statusKB) {
        this.username = username;
        this.tanggalCatatan = tanggalCatatan;
        this.tingkatAliran = tingkatAliran;
        this.gejalaSelected = gejalaSelected;
        this.catatanTambahan = catatanTambahan;
        this.statusKB = statusKB;
    }

    // Logika Konversi List Gejala menjadi Text Panjang untuk Database
    public String getGejalaAsText() {
        if (gejalaSelected == null || gejalaSelected.isEmpty()) return "";
        return String.join(", ", gejalaSelected); 
    }

// getter dan setter
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDate getTanggalCatatan() { return tanggalCatatan; }
    public void setTanggalCatatan(LocalDate tanggalCatatan) { this.tanggalCatatan = tanggalCatatan; }

    public String getTingkatAliran() { return tingkatAliran; }
    public void setTingkatAliran(String tingkatAliran) { this.tingkatAliran = tingkatAliran; }

    public List<String> getGejalaSelected() { return gejalaSelected; }
    public void setGejalaSelected(List<String> gejalaSelected) { this.gejalaSelected = gejalaSelected; }

    public String getCatatanTambahan() { return catatanTambahan; }
    public void setCatatanTambahan(String catatanTambahan) { this.catatanTambahan = catatanTambahan; }

    public boolean isStatusKB() { return statusKB; } 
    public void setStatusKB(boolean statusKB) { this.statusKB = statusKB; }
}