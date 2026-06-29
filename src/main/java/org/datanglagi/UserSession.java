package org.datanglagi;
public class UserSession {
    private static UserSession instance;
    private String username;
    private String email;
    private int durasiStr; // Diambil saat sign up

    // Private constructor agar tidak bisa di-instantiate langsung
    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Mengisi data sesi saat login berhasil
    public void startSession(String username, String email, int durasiHaid) {
        this.username = username;
        this.email = email;
        this.durasiStr = durasiHaid;
    }

    // Menghapus sesi saat logout
    public void clearSession() {
        this.username = null;
        this.email = null;
        this.durasiStr = 0;
    }

    public boolean isUserLoggedIn() {
        return this.username != null;
    }

    // Getter dan Setter
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public int getDurasiStr() { return durasiStr; }
}