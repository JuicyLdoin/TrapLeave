package ua.ldoin.trapleave.listener.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ua.ldoin.trapleave.utils.PlayerManager;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (PlayerManager.getPlayerManager(player) != null)
            new PlayerManager(player);

    }
}