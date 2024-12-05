package com.noix.modpackbuilder.controller;

import com.noix.modpackbuilder.config.AppConfig;
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
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MainController {

    @FXML
    public TableView<Mod> modView;

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

    @FXML
    public void enableAll(ActionEvent event) {
        modView.getItems().forEach(mod -> {
            mod.setEnabled(true);
        });
    }

    @FXML
    public void disableAll(ActionEvent event) {
        modView.getItems().forEach(mod -> {
            mod.setEnabled(false);
        });
    }

    @FXML
    public void copyMods(ActionEvent event) {
        modView.getItems().forEach(mod -> {
            Path path = Path.of(mod.getPath());
            Path resolvedTarget = AppConfig.MINECRAFT_MODS_DIR.resolve(path.getFileName());
            if (mod.isEnabled() && Files.exists(path) && Files.notExists(resolvedTarget)) {
                try {
                    Files.copy(path, resolvedTarget);
                } catch (IOException e) {
                    //TODO: add logging
                    throw new RuntimeException(e);
                }
            }
        });
        System.out.println("Finished");
    }

    @FXML
    public void backupMods(ActionEvent event) throws IOException {
        final String username = System.getProperty("user.name");
        final Path sourceDir = AppConfig.MINECRAFT_MODS_DIR;
        final Path archivePath = Path.of( //TODO: improve naming
                String.format("C:\\Users\\%s\\Downloads\\mods.zip", username) //TODO: add to config
        );

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(archivePath))) {
            // Walk through the directory tree
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Add a file to the zip archive
                    Path relativePath = sourceDir.relativize(file);
                    zipOutputStream.putNextEntry(new ZipEntry(relativePath.toString().replace("\\", "/")));
                    Files.copy(file, zipOutputStream);
                    zipOutputStream.closeEntry();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // Add a directory to the zip archive
                    Path relativePath = sourceDir.relativize(dir);
                    if (!relativePath.toString().isEmpty()) {
                        zipOutputStream.putNextEntry(new ZipEntry(relativePath.toString().replace("\\", "/") + "/"));
                        zipOutputStream.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }

        System.out.println("Directory successfully archived to: " + archivePath);
    }

    @FXML void cleanModFolder(ActionEvent event) throws IOException {
        Files.walk(AppConfig.MINECRAFT_MODS_DIR).forEach(path -> {
            if (Files.isRegularFile(path) || path.getFileName().toString().endsWith(".jar")) {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    //TODO: add logging
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
