package com.noix.modpackbuilder.config;

import java.util.List;

public class ConfigDTO {

    public List<String> downloadModDirs;
    public String minecraftModDir;

    public ConfigDTO(List<String> downloadModDirs, String minecraftModDir) {
        this.downloadModDirs = downloadModDirs;
        this.minecraftModDir = minecraftModDir;
    }

    public ConfigDTO() {
    }

    public List<String> getDownloadModDirs() {
        return downloadModDirs;
    }

    public void setDownloadModDirs(List<String> downloadModDirs) {
        this.downloadModDirs = downloadModDirs;
    }

    public String getMinecraftModDir() {
        return minecraftModDir;
    }

    public void setMinecraftModDir(String minecraftModDir) {
        this.minecraftModDir = minecraftModDir;
    }
}
