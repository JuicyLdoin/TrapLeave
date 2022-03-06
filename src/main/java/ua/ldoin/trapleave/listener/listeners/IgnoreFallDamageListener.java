package ua.ldoin.trapleave.listener.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import ua.ldoin.trapleave.TrapLeavePlugin;
import ua.ldoin.trapleave.utils.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class IgnoreFallDamageListener implements Listener {

    private final FileConfiguration config = TrapLeavePlugin.plugin.getConfig();

    public static List<Player> fall = new ArrayList<>();

    @EventHandler
    public void fall(EntityDamageEvent event) {

        if (config.getBoolean("options.fall_damage"))
            return;

        if (event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();
            PlayerManager manager = PlayerManager.getPlayerManager(player);

            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
                if (fall.contains(player) && manager.hasLeaverRecharge()) {

                    event.setCancelled(true);
                    fall.remove(player);

                }
        }
    }
}