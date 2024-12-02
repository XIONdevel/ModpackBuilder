package com.noix.modpackbuilder.gui;

import com.noix.modpackbuilder.BuilderApplication;
import com.noix.modpackbuilder.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIBuilder {


    public void initWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BuilderApplication.class.getResource("main-view.fxml"));
        Scene scene = scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("ModpackBuilder");
        stage.setScene(scene);
        stage.show();

        MainController controller = fxmlLoader.getController();
        controller.initModViewTables();
        controller.refreshModList(null);
    }



}
