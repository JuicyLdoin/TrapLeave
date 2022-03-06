package ua.ldoin.trapleave;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import ua.ldoin.trapleave.listener.ListenerManager;
import ua.ldoin.trapleave.utils.EffectManager;
import ua.ldoin.trapleave.utils.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class TrapLeavePlugin extends JavaPlugin {

    public static TrapLeavePlugin plugin;

    private EffectManager effectManager;

    public void onEnable() {

        plugin = this;

        saveDefaultConfig();

        new ListenerManager(this);
        effectManager = new EffectManager();

        getCommand("leaver").setExecutor(new Commands());

        for (Player player : Bukkit.getOnlinePlayers())
            new PlayerManager(player);

    }

    public EffectManager getEffectManager() {

        return effectManager;

    }

    public ItemStack getLeaver() {

        ItemStack leaver = new ItemStack(Material.matchMaterial(getConfig().getString("options.leaver.material").toUpperCase()), 1,
                Short.parseShort(getConfig().getString("options.leaver.data")));

        ItemMeta meta = leaver.getItemMeta();

        meta.setDisplayName(getConfig().getString("options.leaver.name").replace("&", "§"));

        List<String> lore = new ArrayList<>();

        for (String lorePiece : getConfig().getStringList("options.leaver.lore"))
            lore.add(lorePiece.replace("&", "§"));

        if (!lore.isEmpty())
            meta.setLore(lore);

        leaver.setItemMeta(meta);

        return leaver;

    }

    public boolean isLeaver(ItemStack item) {

        if (!item.hasItemMeta())
            return false;

        if (!item.getItemMeta().hasDisplayName())
            return false;

        return item.getItemMeta().equals(getLeaver().getItemMeta());

    }

    public void reloadPlugin() {

        plugin.reloadConfig();

    }

    public void sendMessage(CommandSender sender, String message) {

        if (getConfig().getBoolean("message.enabled"))
            if (!message.equals(""))
                sender.sendMessage(message.replace("&", "§")
                        .replace("%leaver%", getLeaver().getItemMeta().getDisplayName()));

    }
}