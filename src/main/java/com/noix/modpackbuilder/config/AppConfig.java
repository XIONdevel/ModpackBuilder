package com.noix.modpackbuilder.config;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {

    //TODO: add more config options
    public static List<String> DOWNLOAD_MOD_DIRS = List.of("G:\\downloads");
    public static String MINECRAFT_MOD_DIR;


    public void loadConfigs() {
        //todo: implement
        //get path to user` appdata
        //if config not exists: createDefaultConfig();
        //init variables
    }

    protected void createDefaultConfig() {
        //todo: implement, prob with json
    }

}
