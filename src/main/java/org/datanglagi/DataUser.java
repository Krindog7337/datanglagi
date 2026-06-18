package org.datanglagi;

import java.io.*;

public class DataUser {
    private static final String FILE_PATH = "user_data.txt";

    public static void simpanData(String email, String pass) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            out.println(email);
            out.println(pass);
        } catch (IOException e) {
            System.err.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    public static String[] ambilData() {
        File file = new File(FILE_PATH);
        
        // CEK APAKAH FILE ADA
        if (!file.exists()) {
            return null; // Jika file belum ada, kembalikan null agar LoginController tahu belum ada akun
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String email = br.readLine();
            String pass = br.readLine();
            
            if (email != null && pass != null) {
                return new String[]{email, pass};
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca data: " + e.getMessage());
        }
        return null;
    }
}