package ca.dklink750.invjamdetector;

import org.bukkit.configuration.file.FileConfiguration;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private final FileConfiguration config;
    private final List<String> inventories = new ArrayList<>();
    private final boolean log;
    private boolean enabled;
    private final String format;

    private final InvJamDetector plugin;

    public ConfigManager(FileConfiguration config, InvJamDetector plugin) {
        this.config = config;
        this.plugin = plugin;
        this.log = config.getBoolean("log-to-console");
        this.enabled = config.getBoolean("enabled");
        this.format = config.getString("message-format");
        loadInventories();
    }

    private void loadInventories() {
        List<String> parsed = config.getStringList("inventories");
        if(parsed != null) {
            this.inventories.addAll(parsed);
        }
    }

    public List<String> getInventories() {
        return inventories;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isLog() {
        return log;
    }

    public String getFormat() {
        return format;
    }

    public void setEnabled(boolean enabled) {
        config.set("enabled", enabled);
        plugin.saveConfig();
        this.enabled = enabled;
    }
}
