package org.datanglagi.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.datanglagi.App;

public class PerawatanController {

    @FXML private void keBeranda(MouseEvent e) throws IOException { App.setRoot("homepage"); }
    @FXML private void keKalender(MouseEvent e) throws IOException { App.setRoot("calender"); }
    @FXML private void kePerawatan(MouseEvent e) throws IOException { App.setRoot("perawatan"); }
    @FXML private void keAkun(MouseEvent e) throws IOException { App.setRoot("user"); }
}
