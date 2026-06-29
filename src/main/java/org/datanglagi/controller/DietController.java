package org.datanglagi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.datanglagi.App;

public class DietController {


    @FXML
    private void keTipsDiet(MouseEvent event) {
        tampilkanPesan("Kamu sudah berada di halaman Tips Diet, wak!");
    }

    @FXML
    private void keTipsNyeri(MouseEvent event) {
        try {
            App.setRoot("perawatan"); 
        } catch (Exception e) {
            e.printStackTrace();
            tampilkanPesan("Gagal memuat halaman Tips Nyeri.");
        }
    }

    @FXML
    private void keTipsOlahraga(MouseEvent event) {
        try {
            App.setRoot("olga"); 
        } catch (Exception e) {
            e.printStackTrace();
            tampilkanPesan("Gagal memuat halaman Tips Olahraga.");
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
