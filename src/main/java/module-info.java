module com.noix.modpackbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;


    exports com.noix.modpackbuilder;
    exports com.noix.modpackbuilder.controller;
    exports com.noix.modpackbuilder.mod;
    exports com.noix.modpackbuilder.config;
    opens com.noix.modpackbuilder to javafx.fxml;
    opens com.noix.modpackbuilder.controller to javafx.fxml;
    opens com.noix.modpackbuilder.mod to javafx.fxml;
    opens com.noix.modpackbuilder.config to javafx.fxml, com.fasterxml.jackson.databind;
}