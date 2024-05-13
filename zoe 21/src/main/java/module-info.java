module com.example.zoe21 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.zoe21 to javafx.fxml;
    exports com.example.zoe21;
}