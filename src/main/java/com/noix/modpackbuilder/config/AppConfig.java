package com.noix.modpackbuilder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
    private static AppConfig appConfig;

    public final Path PATH_TO_APPDATA;
    public final Path PATH_TO_CONFIG;

    public static List<Path> DOWNLOAD_MOD_DIRS;
    public static Path MINECRAFT_MOD_DIR;

    public static AppConfig getInstance() {
        if (appConfig == null) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    private AppConfig() {
        final String username = System.getProperty("user.name");
        PATH_TO_APPDATA = Path.of(String.format("C:\\Users\\%s\\AppData", username));
        PATH_TO_CONFIG = Path.of(String.format("C:\\Users\\%s\\AppData\\Local\\ModpackBuilder\\config.json", username));
        if (Files.exists(PATH_TO_CONFIG)) {
                readConfig();
        } else {
            createDefaultConfig();
        }
        System.out.println(DOWNLOAD_MOD_DIRS);
    }

    private void readConfig() {
        LOGGER.info("Started readConfig");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ConfigDTO config = objectMapper.readValue(PATH_TO_CONFIG.toFile(), ConfigDTO.class);
            readConfigDTO(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultConfig() {
        LOGGER.info("Started createDefaultConfig");
        try {
            final String username = System.getProperty("user.name");
            Path configDir = Path.of(String.format("C:\\Users\\%s\\AppData\\Local\\ModpackBuilder", username));
            if (Files.notExists(configDir)) {
                Files.createDirectory(configDir);
            }
            if (Files.exists(PATH_TO_CONFIG)) {
                Files.delete(PATH_TO_CONFIG);
                Files.createFile(PATH_TO_CONFIG);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ConfigDTO config = new ConfigDTO(
                    List.of(String.format("C:\\Users\\%s\\Downloads", username)),
                    String.format("C:\\Users\\%s\\AppData\\Roaming\\.minecraft\\mods", username)
            );

            objectMapper.writeValue(PATH_TO_CONFIG.toFile(), config);
            readConfigDTO(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readConfigDTO(ConfigDTO config) {
        DOWNLOAD_MOD_DIRS = new ArrayList<>();
        MINECRAFT_MOD_DIR = Path.of(config.minecraftModDir);
        config.downloadModDirs.forEach(p -> DOWNLOAD_MOD_DIRS.add(Path.of(p)));
        if (DOWNLOAD_MOD_DIRS == null || MINECRAFT_MOD_DIR == null) {
            createDefaultConfig();
        }
    }

}
