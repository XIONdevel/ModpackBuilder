module com.noix.modpackbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens com.noix.modpackbuilder to javafx.fxml;
    exports com.noix.modpackbuilder;
    exports com.noix.modpackbuilder.controller;
    exports com.noix.modpackbuilder.mod;
    opens com.noix.modpackbuilder.controller to javafx.fxml;
    opens com.noix.modpackbuilder.mod to javafx.fxml;
}