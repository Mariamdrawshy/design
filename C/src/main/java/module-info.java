module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.example.library to javafx.fxml;
    opens com.example.library.Library to javafx.base;

    exports com.example.library;
}