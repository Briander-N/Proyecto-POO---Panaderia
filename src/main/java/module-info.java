module com.example.panaderia_el_panshito {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.panaderia_el_panshito to javafx.fxml;
    exports com.example.panaderia_el_panshito;
}