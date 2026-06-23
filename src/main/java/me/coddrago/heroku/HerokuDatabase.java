package me.coddrago.heroku;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HerokuDatabase {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path HEROKU_DIR = Paths.get("heroku");
    private final Path dbFile;
    private JsonObject data = new JsonObject();

    public HerokuDatabase(long tgId) {
        this.dbFile = HEROKU_DIR.resolve("db-" + tgId + ".json");
        read();
    }

    public void read() {
        if (dbFile.toFile().exists()) {
            try (Reader reader = new FileReader(dbFile.toFile())) {
                data = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (IOException e) {
                HerokuMod.LOGGER.error("Failed to read database", e);
            }
        }
    }

    public void save() {
        try (Writer writer = new FileWriter(dbFile.toFile())) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            HerokuMod.LOGGER.error("Failed to save database", e);
        }
    }

    public void set(String owner, String key, Object value) {
        if (!data.has(owner)) {
            data.add(owner, new JsonObject());
        }
        data.getAsJsonObject(owner).add(key, GSON.toJsonTree(value));
        save();
    }

    public <T> T get(String owner, String key, T defaultValue, Class<T> type) {
        if (data.has(owner)) {
            JsonObject ownerData = data.getAsJsonObject(owner);
            if (ownerData.has(key)) {
                return GSON.fromJson(ownerData.get(key), type);
            }
        }
        return defaultValue;
    }
}
