package ca.dklink750.invjamdetector;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class InvJamDetector extends JavaPlugin {

    private final FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        ConfigManager configManager = new ConfigManager(config, this);
        this.getCommand("toggleinvjam").setExecutor(new Toggle(configManager));
        getServer().getPluginManager().registerEvents(new OnInventoryCloseEvent(this, configManager), this);
        getServer().getPluginManager().registerEvents(new OnInvJamEvent(configManager), this);
    }
}
