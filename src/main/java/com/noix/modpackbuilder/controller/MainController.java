package com.noix.modpackbuilder.controller;

import com.noix.modpackbuilder.mod.Mod;
import com.noix.modpackbuilder.mod.ModUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MainController {

    @FXML
    public TableView modView;
    @FXML
    public Button refreshButton;

    @FXML
    public void refreshModList(ActionEvent event) {
        ObservableList<Mod> mods = FXCollections.observableArrayList(
                new Mod("name", "path/path/path/path", "1.1.0", "2021-01-02"),
                new Mod("name", "path/path/path/path", "1.1.0", "2021-01-02")
        );
        modView.setItems(mods);
    }

    public void initModViewTables() {
        System.out.println("REFRESHING");
//        List<Mod> modList = ModUtils.getModList();

        TableColumn<Mod, String> nameColumn = new TableColumn<>();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Mod, String> pathColumn = new TableColumn<>();
        pathColumn.setCellValueFactory(cellData -> cellData.getValue().pathProperty());

        TableColumn<Mod, String> versionColumn = new TableColumn<>();
        versionColumn.setCellValueFactory(cellData -> cellData.getValue().versionProperty());

        TableColumn<Mod, String> modifiedColumn = new TableColumn<>();
        modifiedColumn.setCellValueFactory(cellData -> cellData.getValue().modifiedProperty());


        // Column for status (enabled/disabled)
        TableColumn<Mod, Boolean> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("enabled"));

        // Custom cell factory for status column
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

        modView.getColumns().addAll(nameColumn, pathColumn, versionColumn, modifiedColumn, statusColumn);

        modView.setRowFactory(tv -> {
            TableRow<Mod> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Mod mod = row.getItem();
                    mod.setEnabled(!mod.isEnabled()); // Toggle the enabled state
                    modView.refresh(); // Refresh to update the cell appearance
                }
            });
            return row;
        });

    }
}

        // Create a TableView
//        TableView<Person> tableView = new TableView<>();
//
//        // Create Columns
//        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
//        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
//
//        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
//        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject()); // Convert IntegerProperty to Object
//
//        // Add columns to the TableView
//        tableView.getColumns().addAll(nameColumn, ageColumn);
//
//        // Populate TableView with data
//        ObservableList<Person> people = FXCollections.observableArrayList(
//                new Person("Alice", 30),
//                new Person("Bob", 25),
//                new Person("Charlie", 35)
//        );
//        tableView.setItems(people);
//
//        // Handle Row Selection
//        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                System.out.println("Selected: " + newValue.getName() + ", Age: " + newValue.getAge());
//            }
//        });
//
//        // Layout
//        VBox layout = new VBox(10, tableView);
//        Scene scene = new Scene(layout, 400, 300);
//
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("JavaFX TableView Example");
//        primaryStage.show();
//    }
//
//    // Inner class for Person
//    public static class Person {
//        private final javafx.beans.property.SimpleStringProperty name;
//        private final javafx.beans.property.SimpleIntegerProperty age;
//
//        public Person(String name, int age) {
//            this.name = new javafx.beans.property.SimpleStringProperty(name);
//            this.age = new javafx.beans.property.SimpleIntegerProperty(age);
//        }
//
//        public String getName() {
//            return name.get();
//        }
//
//        public void setName(String name) {
//            this.name.set(name);
//        }
//
//        public javafx.beans.property.StringProperty nameProperty() {
//            return name;
//        }
//
//        public int getAge() {
//            return age.get();
//        }
//
//        public void setAge(int age) {
//            this.age.set(age);
//        }
//
//        public javafx.beans.property.IntegerProperty ageProperty() {
//            return age;
//        }
//    }
