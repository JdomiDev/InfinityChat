package me.jdomi.chat.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class onleave implements Listener
{
    main plugin;

    @EventHandler
    public void onquit(PlayerQuitEvent e)
    {
        if (ConfigManager.settings.getString("settings.leaveFormat").equalsIgnoreCase("true"))
        {
            if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            {

                e.setQuitMessage("");

                Bukkit.broadcastMessage(IridiumColorAPI.process(PlaceholderAPI.setPlaceholders(e.getPlayer(), ConfigManager.msg.getString("messages.leaveFormat"))));

            }
            else
            {

                e.setQuitMessage("");
                Bukkit.broadcastMessage(IridiumColorAPI.process(ConfigManager.msg.getString("messages.leaveFormat").replace("%player_name%", e.getPlayer().getDisplayName())));
            }
        }
    }
}