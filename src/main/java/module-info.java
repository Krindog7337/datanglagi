module org.datanglagi {
    requires javafx.controls;
    requires javafx.fxml;
    opens org.datanglagi to javafx.fxml;
    opens org.datanglagi.controller to javafx.fxml;
    exports org.datanglagi;
}