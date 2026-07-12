module com.example.panaderia_el_panshito {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.panaderia_el_panshito to javafx.fxml;
    opens com.example.panaderia_el_panshito.controller to javafx.fxml;
    opens com.example.panaderia_el_panshito.model to javafx.fxml;

    exports com.example.panaderia_el_panshito;
}