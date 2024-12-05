package com.noix.modpackbuilder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
    private static AppConfig appConfig;

    public final Path PATH_TO_APPDATA;
    public final Path PATH_TO_CONFIG;

    public static Path DOWNLOAD_DIR;
    public static Path MINECRAFT_MODS_DIR;

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
            dtoToVariables(readFromFile());
        } else {
            System.out.println("Config not exists");
            LOGGER.warn("Config does not exist, creating default");
            dtoToVariables(createDefaultConfig());
        }
    }

    private ConfigDTO readFromFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(PATH_TO_CONFIG.toFile(), ConfigDTO.class);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
            LOGGER.debug(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            return createDefaultConfig();
        }
    }

    public void writeConfig(ConfigDTO config) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(PATH_TO_CONFIG.toFile(), config);
            dtoToVariables(config);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
            LOGGER.debug(Arrays.toString(e.getStackTrace()));
        }
    }

    private ConfigDTO createDefaultConfig() {
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

            ConfigDTO config = new ConfigDTO(
                    String.format("C:\\Users\\%s\\Downloads", username),
                    String.format("C:\\Users\\%s\\AppData\\Roaming\\.minecraft\\mods", username)
            );
            writeConfig(config);
            return config;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            LOGGER.debug(Arrays.toString(e.getStackTrace()));
            return null; //TODO: show error to user
        }
    }

    public void dtoToVariables(ConfigDTO config) {
        MINECRAFT_MODS_DIR = Path.of(config.modsDir);
        DOWNLOAD_DIR = Path.of(config.downloadsDir);
        if (DOWNLOAD_DIR == null || MINECRAFT_MODS_DIR == null) {
            System.out.println("Something null");
            System.out.println(DOWNLOAD_DIR + " " + MINECRAFT_MODS_DIR);
            createDefaultConfig();
        }
    }


}
