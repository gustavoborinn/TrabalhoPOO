module com.example.demo {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;

        opens com.example.demo.model to javafx.base;
        opens com.example.demo.controller to javafx.fxml;

        exports com.example.demo;
}
