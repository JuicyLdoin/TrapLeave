package ua.ldoin.trapleave.listener.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ua.ldoin.trapleave.TrapLeavePlugin;
import ua.ldoin.trapleave.utils.PlayerManager;

public class UseListener implements Listener {

    private final FileConfiguration config = TrapLeavePlugin.plugin.getConfig();

    @EventHandler
    public void use(PlayerInteractEvent event) {

        if (event.getHand() != null && event.getHand().equals(EquipmentSlot.valueOf(config.getString("options.work_on_hand"))))
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                if (event.getItem() != null) {

                    Player player = event.getPlayer();
                    PlayerManager manager = PlayerManager.getPlayerManager(player);

                    ItemStack item = event.getItem();

                    if (item != null)
                        if (TrapLeavePlugin.plugin.isLeaver(item)) {

                            event.setCancelled(true);

                            if (manager.hasLeaverRecharge())
                                TrapLeavePlugin.plugin.sendMessage(player, config.getString("message.messages.recharge").replace("%time%", String.valueOf(manager.getLeaverRecharge())));
                            else {

                                String type = config.getString("options.type");

                                if (type == null)
                                    type = "jump";

                                int height = config.getInt("options.height");

                                if (type.equalsIgnoreCase("jump"))
                                    player.setVelocity(new Vector(0, height / 10, 0));
                                else if (type.equalsIgnoreCase("teleport"))
                                    player.teleport(player.getLocation().clone().add(0, height, 0), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);

                                if (config.getBoolean("options.remove"))
                                    item.setAmount(item.getAmount() - 1);

                                if (config.contains("options.effects"))
                                    TrapLeavePlugin.plugin.getEffectManager().addEffectsFromStringList(player, config.getStringList("options.effects"));

                                TrapLeavePlugin.plugin.sendMessage(player, config.getString("message.messages.use"));

                                IgnoreFallDamageListener.fall.add(player);
                                manager.startLeaverRecharge();

                            }
                        }
                }
    }
}