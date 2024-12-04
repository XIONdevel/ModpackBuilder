package com.noix.modpackbuilder.config;

import java.util.List;

public class ConfigDTO {

    public String downloadsDir;
    public String modsDir;



    public ConfigDTO() {
    }

    public ConfigDTO(String downloadsDir, String modsDir) {
        this.downloadsDir = downloadsDir;
        this.modsDir = modsDir;
    }

    public String getDownloadsDir() {
        return downloadsDir;
    }

    public void setDownloadsDir(String downloadsDir) {
        this.downloadsDir = downloadsDir;
    }

    public String getModsDir() {
        return modsDir;
    }

    public void setModsDir(String modsDir) {
        this.modsDir = modsDir;
    }
}
