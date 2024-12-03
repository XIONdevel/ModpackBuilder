package com.noix.modpackbuilder.mod;

import com.noix.modpackbuilder.config.AppConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ModUtils {

    public static final String[] MOD_LOADERS = new String[]{"forge", "fabric", "neoforge", "rift"};

    public static List<Mod> getModList() throws IOException {
        List<Mod> result = new ArrayList<>();

        if (!AppConfig.DOWNLOAD_MOD_DIRS.isEmpty()) {
            for (Path dir : AppConfig.DOWNLOAD_MOD_DIRS) {
                Files.walk(dir).forEach(path -> {
                    if (!Files.isDirectory(path)) {
                        String fileName = path.getFileName().toString();
                        if (fileName.endsWith(".jar")) {
                            result.add(extractMod(fileName.substring(0, fileName.length() - 4), path));
                        }
                    }
                });
            }
        }
        return result;
    }

    private static Mod extractMod(String fileName, Path path) {
        final boolean hasModLoader = hasModLoader(fileName);
        Mod mod = new Mod();
        mod.setPath(path.toString());
        String[] separatedName = fileName.split("[-_+\\s\\[\\]]");
        StringBuilder name = new StringBuilder();

        for (String word : separatedName) {
            if (hasModLoader && isModLoader(word)) {
                mod.setModLoader(formatWord(word));
            } else if (hasNumbers(word)) {
                String version = word;
                if (hasLetters(word)) {
                    Map<String, String> map = removeLetters(word, hasModLoader);
                    version = map.get("version");
                    if (map.containsKey("loader")) {
                        mod.setModLoader(map.get("loader"));
                    }
                }
                if (isMinecraftVersion(version) && mod.getMinecraftVersion() == null) {
                    mod.setMinecraftVersion(version);
                } else {
                    mod.setVersion(version);
                }
            } else {
                word = formatWord(word);
                name.append(word).append(" ");
            }
        }
        if (name.toString().isEmpty()) {
            mod.setName(formatWord(separatedName[0]));
        } else {
            mod.setName(name.toString().trim());
        }
        return mod;
    }

    private static boolean isMinecraftVersion(String word) {
        final Pattern shortVersionPattern = Pattern.compile("^\\d\\.\\d{2}$");
        final Pattern longVersionPattern = Pattern.compile("^\\d\\.\\d{2}\\.\\d$");
        return (longVersionPattern.matcher(word).find() && isMinecraftVersionValid(word))
                || (shortVersionPattern.matcher(word).find() && isMinecraftVersionValid(word));
    }

    private static boolean isMinecraftVersionValid(String version) {
        String[] separated = version.split("\\.");
        if (separated.length == 2) {
            int connectedNum = Integer.parseInt(version.replaceAll("\\.", ""));
            return connectedNum >= 110 && connectedNum <= 121; //from 1.10 to 1.21 version
        } else if (separated.length == 3) {
            int connectedNum = Integer.parseInt(version.replaceAll("\\.", ""));
            return connectedNum >= 1101 && connectedNum <= 1214; //from 1.10.1 to 1.21.4 version
        }
        return false;
    }

    private static boolean hasNumbers(String word) {
        final Pattern pattern = Pattern.compile(".*\\d.*");
        return pattern.matcher(word).find();
    }

    private static boolean hasLetters(String word) {
        final Pattern pattern = Pattern.compile("[a-zA-Z]");
        return pattern.matcher(word).find();
    }

    private static Map<String, String> removeLetters(String word, boolean hasModLoader) {
        char[] chars = word.toCharArray();

        Map<String, String> result = new HashMap<>();
        StringBuilder version = new StringBuilder();
        StringBuilder removed = new StringBuilder();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                version.append(c);
            } else {
                removed.append(c);
            }
        }

        if (hasModLoader) {
            String str = removed.toString().toLowerCase();
            for (String loader : MOD_LOADERS) {
                if (str.contains(loader)) {
                    result.put("loader", formatWord(extractWord(str, loader)));
                    break;
                }
            }
        }

        result.put("version", version.toString());
        return result;
    }

    private static String formatWord(String word) {
        if (word.length() > 1) {
            word = word.toLowerCase();
            StringBuilder builder = new StringBuilder();
            builder.append(Character.toUpperCase(word.charAt(0)));
            builder.append(word.substring(1, word.length()));
            return builder.toString();
        } else {
            return word.toUpperCase();
        }
    }

    private static String extractWord(String source, String example) {
        return source.substring(
                source.indexOf(example.charAt(0)),
                source.indexOf(example.charAt(example.length() - 1)) + 1 //if it works, dont touch it
        );
    }

    private static boolean isModLoader(String word) {
        for (String loader : MOD_LOADERS) {
            if (loader.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasModLoader(String fileName) {
        String lower = fileName.toLowerCase();
        for (String loader : MOD_LOADERS) {
            if (loader.contains(loader)) {
                return true;
            }
        }
        return false;
    }

}
