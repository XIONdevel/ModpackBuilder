package com.noix.modpackbuilder.mod;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;


public class Mod {

    private SimpleStringProperty name;
    private SimpleStringProperty path;
    private SimpleStringProperty version;
    private SimpleStringProperty minecraftVersion;
    private SimpleStringProperty modLoader;
    private BooleanProperty enabled;


    public void setDefaultValues() {
        this.name = new SimpleStringProperty();
        this.path = new SimpleStringProperty();
        this.version = new SimpleStringProperty();
        this.minecraftVersion = new SimpleStringProperty();
        this.modLoader = new SimpleStringProperty();
        this.enabled = new SimpleBooleanProperty(false);
    }

    public Mod() {
        this.name = new SimpleStringProperty();
        this.path = new SimpleStringProperty();
        this.version = new SimpleStringProperty();
        this.minecraftVersion = new SimpleStringProperty();
        this.modLoader = new SimpleStringProperty();
        this.enabled = new SimpleBooleanProperty(false);
    }

    public Mod(SimpleStringProperty name,
               SimpleStringProperty path,
               SimpleStringProperty version,
               SimpleStringProperty minecraftVersion,
               SimpleStringProperty modLoader,
               BooleanProperty enabled) {
        this.name = name;
        this.path = path;
        this.version = version;
        this.minecraftVersion = minecraftVersion;
        this.modLoader = modLoader;
        this.enabled = enabled;
    }


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getPath() {
        return path.get();
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path = new SimpleStringProperty(path);
    }

    public String getVersion() {
        return version.get();
    }

    public SimpleStringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version = new SimpleStringProperty(version);
    }

    public String getMinecraftVersion() {
        return minecraftVersion.get();
    }

    public SimpleStringProperty minecraftVersionProperty() {
        return minecraftVersion;
    }

    public void setMinecraftVersion(String minecraftVersion) {
        this.minecraftVersion = new SimpleStringProperty(minecraftVersion);
    }

    public String getModLoader() {
        return modLoader.get();
    }

    public SimpleStringProperty modLoaderProperty() {
        return modLoader;
    }

    public void setModLoader(String modLoader) {
        this.modLoader = new SimpleStringProperty(modLoader);
    }

    public BooleanProperty isEnabledProperty() {
        return enabled;
    }

    public void setIsEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = new SimpleBooleanProperty(enabled);
    }

    public boolean isEnabled() {
        return enabled.get();
    }
}
