package ua.ldoin.trapleave.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ua.ldoin.trapleave.TrapLeavePlugin;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private final FileConfiguration config = TrapLeavePlugin.plugin.getConfig();

    private static final Map<Player, PlayerManager> managers = new HashMap<>();

    public static PlayerManager getPlayerManager(Player player) {

        return managers.get(player);

    }

    private final Player player;

    private int leaverRecharge;

    public PlayerManager(Player player) {

        this.player = player;

        leaverRecharge = -1;

        managers.put(player, this);

    }

    public boolean hasLeaver() {

        ItemStack leaver = TrapLeavePlugin.plugin.getLeaver().clone();
        leaver.setAmount(1);

        for (ItemStack itemStack : player.getInventory().getContents())
            if (itemStack != null) {

                ItemStack itemStack1 = itemStack.clone();
                itemStack1.setAmount(1);

                if (leaver.equals(itemStack1))
                    return true;

            }

        return false;

    }

    public boolean hasLeaverRecharge() {

        return leaverRecharge != -1;

    }

    public int getLeaverRecharge() {

        return leaverRecharge;

    }

    public void startLeaverRecharge() {

        int leaverRechargeTime = config.getInt("options.recharge");

        if (leaverRechargeTime > 0) {

            leaverRecharge = leaverRechargeTime;

            new BukkitRunnable() {

                public void run() {

                    leaverRecharge--;

                    if (leaverRecharge == -1) {

                        if (hasLeaver())
                            TrapLeavePlugin.plugin.sendMessage(player, config.getString("message.messages.recharged"));

                        cancel();

                    }
                }
            }.runTaskTimer(TrapLeavePlugin.plugin, 0L, 20L);
        }
    }
}