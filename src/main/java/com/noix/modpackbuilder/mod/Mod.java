package com.noix.modpackbuilder.mod;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.Date;

public class Mod {

    private SimpleStringProperty name;
    private SimpleStringProperty path;
    private SimpleStringProperty version;
    private SimpleStringProperty modified;
    private BooleanProperty isEnabled;

    public Mod(String name, String path, String version, String modified) {
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty(path);
        this.version = new SimpleStringProperty(version);
        this.modified = new SimpleStringProperty(modified);
        isEnabled = new SimpleBooleanProperty(false);
    }

    public boolean isEnabled() {
        return isEnabled.get();
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled.set(enabled);
    }

    public BooleanProperty enabledProperty() {
        return isEnabled;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPath() {
        return path.get();
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public String getVersion() {
        return version.get();
    }

    public SimpleStringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public String getModified() {
        return modified.get();
    }

    public SimpleStringProperty modifiedProperty() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified.set(modified);
    }
}
