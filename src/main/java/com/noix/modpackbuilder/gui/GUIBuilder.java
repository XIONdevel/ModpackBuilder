package com.noix.modpackbuilder.gui;

import com.noix.modpackbuilder.BuilderApplication;
import com.noix.modpackbuilder.config.AppConfig;
import com.noix.modpackbuilder.config.ConfigDTO;
import com.noix.modpackbuilder.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIBuilder {

    private static GUIBuilder instance;

    public static GUIBuilder getInstance() {
        if (instance == null) {
            instance = new GUIBuilder();
        }
        return instance;
    }

    private GUIBuilder() {}

    public void initMainWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BuilderApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 900);
        stage.setTitle("ModpackBuilder");
        stage.setScene(scene);
        stage.show();

        MainController controller = fxmlLoader.getController();
        controller.initModViewTables();
        controller.refreshModList(null);
    }

    public void initSettingsWindow() {
        Stage settingsWindow = new Stage();
        settingsWindow.setTitle("Settings");


        Label modLabel = new Label("Mod directory");
        modLabel.getStyleClass().add("label");

        TextField modDir = new TextField(AppConfig.MINECRAFT_MODS_DIR.toString());
        modDir.setPromptText("Path to .minecraft/mods");
        modDir.getStyleClass().add("modDirField");
        VBox modBox = new VBox();

        modBox.getChildren().addAll(modLabel, modDir);
        modBox.getStyleClass().add("modVBox");


        Label downloadLabel = new Label("Downloads directory");
        downloadLabel.getStyleClass().add("label");

        TextField downloadDir = new TextField(AppConfig.DOWNLOAD_DIR.toString());
        downloadDir.setPromptText("Path to downloaded mods");
        downloadDir.getStyleClass().add("downloadDirField");
        VBox downloadBox = new VBox();

        downloadBox.getChildren().addAll(downloadLabel, downloadDir);
        downloadBox.getStyleClass().add("downloadVBox");


        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            ConfigDTO dto = new ConfigDTO(
                    downloadDir.getText().trim(),
                    modDir.getText().trim()
            );
            AppConfig.getInstance().writeConfig(dto);
            settingsWindow.close();
        });
        saveButton.getStyleClass().add("saveButton");


        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            settingsWindow.close();
        });
        cancelButton.getStyleClass().add("cancelButton");


        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(saveButton, cancelButton);
        buttonBox.getStyleClass().add("buttonHBox");

        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(modBox, downloadBox, buttonBox);
        mainBox.getStyleClass().add("mainVBox");

        Scene scene = new Scene(mainBox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/com/noix/modpackbuilder/css/settings.css").toExternalForm());
        settingsWindow.setScene(scene);
        settingsWindow.show();
    }


}
