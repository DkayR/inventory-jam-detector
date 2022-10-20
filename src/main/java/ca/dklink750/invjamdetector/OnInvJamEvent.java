package ca.dklink750.invjamdetector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.stream.Collectors;

public class OnInvJamEvent implements Listener {

    private final ConfigManager config;

    public OnInvJamEvent(ConfigManager config) {
        this.config = config;
    }

    private final DecimalFormat df = new DecimalFormat("#.#");

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInvJam(InvJamEvent event) {
        Player invJammer = event.getPlayer();
        Location location =  invJammer.getLocation();
        String name = invJammer.getDisplayName();
        String world = location.getWorld().getName();
        String x = df.format(location.getX());
        String y = df.format(location.getY());
        String z = df.format(location.getZ());

        String message = config.getFormat();
        message = message.replace("{{player-name}}", name);
        message = message.replace("{{x}}", x);
        message = message.replace("{{y}}", y);
        message = message.replace("{{z}}", z);
        message = message.replace("{{world}}", world);
        message = ChatColor.translateAlternateColorCodes('&', message);

        if(config.isLog()) {
            Bukkit.getLogger().info(message);
        }

        for(Player player : getViewers()) {
            player.sendMessage(message);
        }
    }

    private Collection<Player> getViewers() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("invjamdetector.see"))
                .collect(Collectors.toList());
    }
}
