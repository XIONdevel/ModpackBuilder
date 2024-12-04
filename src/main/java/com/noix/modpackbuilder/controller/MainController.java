package com.noix.modpackbuilder.controller;

import com.noix.modpackbuilder.gui.GUIBuilder;
import com.noix.modpackbuilder.mod.Mod;
import com.noix.modpackbuilder.mod.ModUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    public TableView modView;
    public Button refreshButton;
    public Button settingsButton;

    //TODO: add exception handling
    @FXML
    public void refreshModList(ActionEvent event) throws IOException {
        ObservableList<Mod> mods = FXCollections.observableArrayList(ModUtils.getModList());
        modView.setItems(mods);
    }

    @FXML
    public void openSettings(ActionEvent event) {
        GUIBuilder.getInstance().initSettingsWindow();
    }

    public void initModViewTables() {
        TableColumn<Mod, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setPrefWidth(200.0);

        TableColumn<Mod, String> pathColumn = new TableColumn<>("Path");
        pathColumn.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
        pathColumn.setPrefWidth(500.0);

        TableColumn<Mod, String> versionColumn = new TableColumn<>("Version");
        versionColumn.setCellValueFactory(cellData -> cellData.getValue().versionProperty());
        versionColumn.setPrefWidth(100.0);

        TableColumn<Mod, String> minecraftVersionColumn = new TableColumn<>("Minecraft Version");
        minecraftVersionColumn.setCellValueFactory(cellData -> cellData.getValue().minecraftVersionProperty());
        minecraftVersionColumn.setPrefWidth(150.0);

        TableColumn<Mod, String> modLoaderColumn = new TableColumn<>("Loader");
        modLoaderColumn.setCellValueFactory(cellData -> cellData.getValue().modLoaderProperty());
        modLoaderColumn.setPrefWidth(100.0);

        TableColumn<Mod, Boolean> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("enabled"));
        statusColumn.setPrefWidth(200.0);

        statusColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean enabled, boolean empty) {
                super.updateItem(enabled, empty);

                if (empty || enabled == null) {
                    setText(null);
                    setStyle("");
                } else {
                    // Change background color based on the enabled state
                    setText(enabled ? "Enabled" : "Disabled");
                    setStyle("-fx-background-color: " + (enabled ? "lightgreen" : "lightcoral"));
                }
            }
        });

        modView.getColumns().addAll(
                nameColumn, pathColumn,
                versionColumn, minecraftVersionColumn,
                modLoaderColumn, statusColumn
        );

        modView.setRowFactory(tv -> {
            TableRow<Mod> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Mod mod = row.getItem();
                    mod.setEnabled(!mod.isEnabled());
                    modView.refresh();
                }
            });
            return row;
        });
    }
}
