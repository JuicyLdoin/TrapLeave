package ua.ldoin.trapleave.listener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import ua.ldoin.trapleave.TrapLeavePlugin;
import ua.ldoin.trapleave.listener.listeners.*;

public class ListenerManager {

    public ListenerManager(TrapLeavePlugin plugin) {

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new UseListener(), plugin);
        pluginManager.registerEvents(new JoinListener(), plugin);
        pluginManager.registerEvents(new IgnoreFallDamageListener(), plugin);

    }
}