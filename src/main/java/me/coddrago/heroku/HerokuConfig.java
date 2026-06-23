package me.coddrago.heroku;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HerokuConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path HEROKU_DIR = Paths.get("heroku");
    private static final Path CONFIG_FILE = HEROKU_DIR.resolve("config.json");

    public int api_id = 0;
    public String api_hash = "";
    public String app_name = "Heroku Minecraft Mod";
    public String command_prefix = ".";

    public static HerokuConfig load() {
        if (!HEROKU_DIR.toFile().exists()) {
            HEROKU_DIR.toFile().mkdirs();
        }
        if (CONFIG_FILE.toFile().exists()) {
            try (Reader reader = new FileReader(CONFIG_FILE.toFile())) {
                return GSON.fromJson(reader, HerokuConfig.class);
            } catch (IOException e) {
                HerokuMod.LOGGER.error("Failed to load config", e);
            }
        }
        return new HerokuConfig();
    }

    public void save() {
        if (!HEROKU_DIR.toFile().exists()) {
            HEROKU_DIR.toFile().mkdirs();
        }
        try (Writer writer = new FileWriter(CONFIG_FILE.toFile())) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            HerokuMod.LOGGER.error("Failed to save config", e);
        }
    }
}
