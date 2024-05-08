module com.example.zoe21 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zoe21 to javafx.fxml;
    exports com.example.zoe21;
}