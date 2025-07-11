package me.spigot.Omnicore.API.Managers.Warn;

import com.google.gson.reflect.TypeToken;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Modules.Warn.Helpers.WarnObject;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WarnManager {
    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<UUID, List<WarnObject>> warns = new HashMap<>();

    public WarnManager(JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "warns.json");
        load();
    }

    public void addWarn(UUID uuid, WarnObject warn) {
        warns.computeIfAbsent(uuid, k -> new ArrayList<>()).add(warn);
        save();
    }

    public void clearWarns(UUID uuid) {
        warns.remove(uuid);
        save();
    }

    public List<WarnObject> getWarns(UUID uuid) {
        return warns.getOrDefault(uuid, new ArrayList<>());
    }

    private void save() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(warns, writer);
        } catch (IOException e) {
            OmniLog.Exception("Omnicore ha riscontrato un errore e non è riuscito ad avviarsi!", e);
        }
    }

    private void load() {
        if (!file.exists()) return;
        try (Reader reader = Files.newBufferedReader(file.toPath())) {
            Type type = new TypeToken<Map<UUID, List<WarnObject>>>() {}.getType();
            Map<UUID, List<WarnObject>> data = gson.fromJson(reader, type);
            if (data != null) {
                warns.putAll(data);
            }
        } catch (IOException e) {
            OmniLog.Exception("Omnicore ha riscontrato un errore e non è riuscito ad avviarsi!", e);
        }
    }
}
