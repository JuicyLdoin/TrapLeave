package ua.ldoin.trapleave;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {

    private final FileConfiguration config = TrapLeavePlugin.plugin.getConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {

        if (command.equals("leaver")) {

            if (!sender.hasPermission("trapleave.leaver")) {

                TrapLeavePlugin.plugin.sendMessage(sender, config.getString("message.messages.no_permissions"));
                return false;

            }

            if (args.length == 1 && args[0].equals("reload")) {

                TrapLeavePlugin.plugin.reloadPlugin();
                sender.sendMessage("§aTrapLeave is reloaded!");

                return true;

            }

            if (args.length == 3 && args[0].equals("give")) {

                Player player = Bukkit.getPlayerExact(args[1]);

                if (player == null) {

                    TrapLeavePlugin.plugin.sendMessage(sender, config.getString("message.messages.not_found").replace("%player%", args[1]));
                    return false;

                }

                if (!args[2].matches("^-?\\d+$")) {

                    TrapLeavePlugin.plugin.sendMessage(sender, config.getString("message.messages.not_an_integer"));
                    return false;

                }

                if (player.getInventory().firstEmpty() == -1) {

                    TrapLeavePlugin.plugin.sendMessage(sender, config.getString("message.messages.inventory_is_full").replace("%player%", player.getName()));
                    return false;

                }

                ItemStack toGive = TrapLeavePlugin.plugin.getLeaver();
                toGive.setAmount(Integer.parseInt(args[2]));

                player.getInventory().addItem(toGive);

                TrapLeavePlugin.plugin.sendMessage(sender, config.getString("message.messages.give")
                        .replace("%amount%", args[2])
                        .replace("%player%", player.getName()));

                return true;

            }

            TrapLeavePlugin.plugin.sendMessage(sender, "&a/leaver reload - reload plugin");
            TrapLeavePlugin.plugin.sendMessage(sender, "&a/leaver give [player] [amount] - give [amount] leavers to [player]");

        }

        return false;

    }
}