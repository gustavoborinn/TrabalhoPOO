module com.bluevelvetmusicstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bluevelvetmusicstore.model to javafx.base;
    opens com.bluevelvetmusicstore.controller to javafx.fxml;

    exports com.bluevelvetmusicstore;
}
