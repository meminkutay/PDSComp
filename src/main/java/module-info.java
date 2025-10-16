module com.pdsdataextractor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;

    opens com.pdsdataextractor to javafx.fxml;
    exports com.pdsdataextractor;


}