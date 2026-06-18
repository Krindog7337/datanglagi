package org.datanglagi.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi jika diperlukan
    }

    @FXML
    private void keBeranda(MouseEvent event) throws IOException {
        pindahHalaman(event, "/org/datanglagi/fxml/homepage.fxml");
    }

    @FXML
    private void keKalender(MouseEvent event) throws IOException {
        pindahHalaman(event, "/org/datanglagi/fxml/calender.fxml");
    }

    @FXML
    private void kePerawatan(MouseEvent event) throws IOException {
        pindahHalaman(event, "/org/datanglagi/fxml/perawatan.fxml");
    }

    @FXML
    private void keAkun(MouseEvent event) throws IOException {
        // Sudah di halaman Akun, tidak perlu pindah
    }

    // Method pembantu agar kode lebih rapi
    private void pindahHalaman(MouseEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}