package com.noix.modpackbuilder;

import com.noix.modpackbuilder.gui.GUIBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class BuilderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GUIBuilder builder = new GUIBuilder();
        builder.initWindow(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}