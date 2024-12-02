package com.noix.modpackbuilder;

import com.noix.modpackbuilder.config.AppConfig;
import com.noix.modpackbuilder.controller.MainController;
import com.noix.modpackbuilder.gui.GUIBuilder;
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class BuilderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AppConfig config = new AppConfig();
        config.loadConfigs();
        GUIBuilder builder = new GUIBuilder();
        builder.initWindow(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}