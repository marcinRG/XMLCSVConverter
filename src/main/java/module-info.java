module io.marcinrg {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires javafx.base;

    opens io.marcinrg to javafx.fxml;
    exports io.marcinrg;
    exports io.marcinrg.model;
    opens io.marcinrg.model to javafx.base;
}