module io.marcinrg {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens io.marcinrg to javafx.fxml;
    exports io.marcinrg;
}