package ca.dklink750.invjamdetector;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class OnInventoryCloseEvent implements Listener {

    private final InvJamDetector plugin;

    private final ConfigManager config;

    public OnInventoryCloseEvent(InvJamDetector plugin, ConfigManager config) {
        this.plugin = plugin;
        this.config = config;
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        if(config.isEnabled()) {
            if (config.getInventories().contains(event.getInventory().getType().name())) {
                Player player = Bukkit.getPlayer(event.getPlayer().getName());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (isJam(player)) {
                            InvJamEvent invJamEvent = new InvJamEvent(player);
                            Bukkit.getPluginManager().callEvent(invJamEvent);
                        }
                    }
                }.runTaskLater(plugin, 1);
            }
        }
    }

    private boolean isJam(Player player) {
        double jumpVelocity = 0.42F;
        if(player.hasPotionEffect(PotionEffectType.JUMP)) {
            for(PotionEffect effect : player.getActivePotionEffects()) {
                if(effect.getType().equals(PotionEffectType.JUMP)) {
                    jumpVelocity += (effect.getAmplifier() + 1) * 0.1F;
                }
            }
        }

        double playerVelocity = player.getVelocity().getY();
        return Double.compare(playerVelocity, (double)jumpVelocity) == 0;
    }
}
