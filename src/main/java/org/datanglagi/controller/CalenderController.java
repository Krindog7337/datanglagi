package org.datanglagi.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.datanglagi.App;

public class CalenderController implements Initializable {

    // Deklarasi Komponen (Pastikan fx:id di FXML sama persis)
    @FXML private Button btnTidakAda, btnRingan, btnSedang, btnBerat;
    @FXML private Button btnNyeri, btnSakitKepala, btnKembung, btnMual, btnPayudara, btnKram, btnLemas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi jika diperlukan
    }   

    // --- LOGIKA TOMBOL ALIRAN (Pilih Satu) ---
    @FXML
    private void pilihAliran(ActionEvent event) {
        Button[] semua = {btnTidakAda, btnRingan, btnSedang, btnBerat};
        // Reset semua ke warna default
        for (Button b : semua) {
            b.setStyle("-fx-background-radius:14; -fx-background-color:#F2EEEE; -fx-text-fill:black;");
        }
        // Beri warna terpilih
        Button terpilih = (Button) event.getSource();
        terpilih.setStyle("-fx-background-radius:14; -fx-background-color:#9B1C1C; -fx-text-fill:white;");
    }

    // --- LOGIKA TOMBOL GEJALA (Pilih Banyak) ---
    @FXML
    private void pilihGejala(ActionEvent event) {
        Button b = (Button) event.getSource();
        if (b.getStyle().contains("#9B1C1C")) {
            b.setStyle("-fx-background-radius:18; -fx-background-color:#F2EEEE; -fx-text-fill:black;");
        } else {
            b.setStyle("-fx-background-radius:18; -fx-background-color:#9B1C1C; -fx-text-fill:white;");
        }
    }

    @FXML
    private void simpanData() {
        System.out.println("Data berhasil disimpan ke sistem!");
    }

    // --- LOGIKA NAVIGASI ---
    @FXML private void keBeranda(MouseEvent e) throws IOException { navigasi(e, "homepage"); }
    @FXML private void keKalender(MouseEvent e) throws IOException { navigasi(e, "calender"); }
    @FXML private void kePerawatan(MouseEvent e) throws IOException { navigasi(e, "perawatan"); }
    @FXML private void keAkun(MouseEvent e) throws IOException { navigasi(e, "user"); }

    private void navigasi(MouseEvent event, String fxml) throws IOException {
        Node source = (Node) event.getSource();
        FadeTransition ft = new FadeTransition(Duration.millis(200), source);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
        ft.play();
        ft.setOnFinished(e -> {
            try { App.setRoot(fxml); } catch (IOException ex) { ex.printStackTrace(); }
        });
    }
}