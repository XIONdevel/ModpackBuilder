module com.noix.modpackbuilder {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.noix.modpackbuilder to javafx.fxml;
    exports com.noix.modpackbuilder;
    exports com.noix.modpackbuilder.controller;
    opens com.noix.modpackbuilder.controller to javafx.fxml;
}