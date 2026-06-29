package org.datanglagi;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//buat class app yg mewarisi sifat javaFX
public class App extends Application {
    private static Scene scene;

//method pertama yang dijalankan untuk memanggil loading page
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loading"), 360, 640);
        stage.setScene(scene);
        stage.show();
    }

//method pindah page    
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
//method mencari dan memuat fxml
    private static Parent loadFXML(String fxml) throws IOException {
    String path = "/org/datanglagi/fxml/" + fxml + ".fxml"; 
    
    System.out.println("Mencoba memuat FXML dari: " + path);
    
    URL resourceUrl = App.class.getResource(path);
    
    // Pengecekan jika file tidak ditemukan
    if (resourceUrl == null) {
        throw new IOException("File FXML tidak ditemukan di: " + path);
    }
    
    FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
    return fxmlLoader.load();
}

//driver utama untuk mengeksekusi class ini pertama kali
    public static void main(String[] args) {
        launch(args);
    }
}