package ca.dklink750.invjamdetector;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Toggle implements CommandExecutor {

    private final ConfigManager config;

    public Toggle(ConfigManager config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("invjamdetector.toggle")) {
            if(config.isEnabled()) {
                sender.sendMessage(ChatColor.GREEN + "Disabled inventory jam detection");
                config.setEnabled(false);
            } else {
                sender.sendMessage(ChatColor.GREEN + "Enabled inventory jam detection");
                config.setEnabled(true);
            }
        }
        return true;
    }
}
